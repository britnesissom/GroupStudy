package ee461l.groupstudy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FileSharingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FileSharingFragment extends Fragment {

    private static final String GROUP_NAME = "groupName";

    private TextView users;
    private ListView files;
    private List<File> filesFromServer;
    private String groupName;
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
        args.putString(GROUP_NAME, groupName);
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
            groupName = getArguments().getString(GROUP_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_file_sharing, container, false);
        users = (TextView) rootView.findViewById(R.id.users);
        getActivity().setTitle("Files");

        files = (ListView) rootView.findViewById(R.id.filesInGroup);

        RetrieveFilesFromServer retrieval = new RetrieveFilesFromServer("groupName");
        filesFromServer = retrieval.getFiles();

        FileListViewAdapter adapter = new FileListViewAdapter(getActivity(), filesFromServer);
        files.setAdapter(adapter);

        // implement eventDate when an item on list view is selected
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
        Log.i(TAG, "Received an \"Activity Result\"");
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

                try {
                    InputStream fileInputStream = getActivity().getContentResolver().openInputStream(androidUri);
                    String uriString = fileInputStream.toString();
                    Log.i(TAG, "Android Uri: " + androidUri.toString());
                    Log.i(TAG, "String uri: " + uriString);

                    //begin sending file to server
                    SendFileToServer sendFile = new SendFileToServer();
                    sendFile.execute(uriString);
                }
                catch(FileNotFoundException e) {
                    Log.i(TAG, "file not found");
                    e.printStackTrace();
                }
            }
            // END_INCLUDE (parse_open_document_response)
        }
    }

    private class SendFileToServer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... uri) {

            String responseString;

            //need to add group name after ".com"
            //so we know where the file is saved
            String serverUrl = "http://groupstudy-ee-461l.appspot.com";

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(serverUrl);

            try {
                MultipartEntityBuilder entity = MultipartEntityBuilder.create();

                File file = new File(uri[0]);

                // Adding file data to http body
                entity.addPart("file", new FileBody(file));
                httppost.setEntity(entity.build());

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(TAG, "Response from server: " + result);

            new AlertDialog.Builder(getActivity())
                    .setTitle("Success!")
                    .setMessage("File uploaded!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //go back to ingredients screen
                        }
                    })
                    .show();

            super.onPostExecute(result);
        }
    }
}
