package ee461l.groupstudy.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ee461l.groupstudy.adapter.FileListViewAdapter;
import ee461l.groupstudy.FileUtils;
import ee461l.groupstudy.R;
import ee461l.groupstudyendpoints.groupstudyEndpoint.GroupstudyEndpoint;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.FilesEntity;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FileSharingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FileSharingFragment extends Fragment {

    private static final String GROUP_ID = "groupId";

    private TextView users;
    private ListView files;
    private List<FilesEntity> filesFromServer;
    //private List<File> filesToView;
    private String groupId;
    private Groups group;
    private FileListViewAdapter adapter;
    private static final String TAG = "FileSharingFragment";

    // A request code's purpose is to match the result of a "startActivityForResult" with
    // the type of the original request.  Choose any value.
    private static final int READ_REQUEST_CODE = 1337;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment FileSharingFragment.
     */
    public static Fragment newInstance(String groupName) {
        Fragment fragment = new FileSharingFragment();
        Bundle args = new Bundle();
        args.putString(GROUP_ID, groupName);
        fragment.setArguments(args);
        return fragment;
    }

    public FileSharingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            groupId = getArguments().getString(GROUP_ID);
        }

        filesFromServer = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
        query.getInBackground(groupId, new GetCallback<ParseObject>() {
            public void done(ParseObject group, ParseException e) {
                if (e == null) {
                    // The query was successful.

                    //get files here? idk
                } else {
                    // Something went wrong.
                    Log.d(TAG, e.getMessage());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_file_sharing, container, false);
        getActivity().setTitle("Files");

        files = (ListView) rootView.findViewById(R.id.filesInGroup);
        adapter = new FileListViewAdapter(getActivity(), R.layout.file_list_item, filesFromServer);

        files.setAdapter(adapter);

        files.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int pos, long id) {
                // get the list adapter
                FileListViewAdapter fileAdapter = (FileListViewAdapter) parent.getAdapter();

                //get view for file
                fileAdapter.getView(pos, view, parent);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.file_sharing_upload_file, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.upload_file:
                startFileChooser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //starts dialog to allow user to choose a file to upload to server
    private void startFileChooser() {
        // BEGIN_INCLUDE (use_open_document_intent)
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a file (as opposed to a list
        // of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers, it would be
        // "*/*".
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
        // END_INCLUDE (use_open_document_intent)
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        Log.d(TAG, "Received an \"Activity Result\"");
        // BEGIN_INCLUDE (parse_open_document_response)
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code READ_REQUEST_CODE.
        // If the request code seen here doesn't match, it's the response to some other intent,
        // and the below code shouldn't run at all.

        //save to server in here (in an async task)!!
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.  Pull that uri using "resultData.getData()"
            Uri androidUri;
            if (resultData != null) {
                androidUri = resultData.getData();

                Log.d(TAG, "Android Uri: " + androidUri.toString());

                //begin sending file to server
                SendFileToGroupEndpoint sendFile = new SendFileToGroupEndpoint(getActivity());
                sendFile.execute(androidUri);

            }
            // END_INCLUDE (parse_open_document_response)
        }
    }

    //convert file to byte array then save it as entity in group
    private class SendFileToGroupEndpoint extends AsyncTask<Uri, Void, Groups> {
        private GroupstudyEndpoint groupEndpointApi = null;
        private Context context;

        private SendFileToGroupEndpoint(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getActivity(), "Uploading file...", Toast.LENGTH_LONG).show();
        }

        //need to open endpoint for saving
        //convert file to byte array to save
        //save file to arraylist for files in specific group
        @Override
        protected Groups doInBackground(Uri... uri) {
            if(groupEndpointApi == null) {  // Only do this once
                GroupstudyEndpoint.Builder builder = new GroupstudyEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("https://groupstudy-461l.appspot.com/_ah/api")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });

                groupEndpointApi = builder.build();
            }

            //File file = new File(uri[0].toString());
            try {
                String path = FileUtils.getPath(context, uri[0]);
                Log.d(TAG, "file path: " + path);
                File file = new File(path);

                Log.d(TAG, "file name: " + file.getName());
                Log.d(TAG, "file length: " + file.length());

                InputStream fileInputStream = null;
                byte[] bFile = new byte[(int) file.length()];

                //convert file into array of bytes
                fileInputStream = new BufferedInputStream(new FileInputStream(file));
                int i = fileInputStream.read(bFile);
                fileInputStream.close();

                Log.d(TAG, "inputStream length: " + i);

                String fileBytes = new String(bFile, "UTF-16");
                FilesEntity fe = new FilesEntity();
                fe.setId(file.getName());
                fe.setFileName(file.getName());
                fe.setFileContents(fileBytes);

                Groups groupReturned = groupEndpointApi.addFile(groupId, fe).execute();

                Log.d(TAG, "file added to group");
                return groupReturned;
            }
            catch(IOException e) {
                Log.e(TAG, e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Groups result) {
            Toast.makeText(getActivity().getApplicationContext(), "File uploaded!",
                    Toast.LENGTH_LONG).show();

            super.onPostExecute(result);

            //group did not load correctly so it can't be returned
            if (result != null)
                filesFromServer.addAll(result.getFiles());
            else
                filesFromServer.addAll(new ArrayList<FilesEntity>());
            adapter.notifyDataSetChanged();
        }
    }
}
