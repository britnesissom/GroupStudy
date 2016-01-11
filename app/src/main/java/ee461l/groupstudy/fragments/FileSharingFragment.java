package ee461l.groupstudy.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ee461l.groupstudy.FileUtils;
import ee461l.groupstudy.R;
import ee461l.groupstudy.adapter.FileRVAdapter;
import ee461l.groupstudy.models.Group;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FileSharingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FileSharingFragment extends BaseFragment implements FileRVAdapter.FileCallback {

    private static final String GROUP_ID = "groupId";
    private static final int PERMISSION_CODE = 6;

    private List<ParseFile> filesToView;
    private String groupId;
    private Group group;
    private FileRVAdapter adapter;
    private ActionMode mActionMode;
    private byte[] fileBytes;
    private ParseFile selectedFile;
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

        filesToView = new ArrayList<>();
        adapter = new FileRVAdapter(getContext(), filesToView, this);

        ParseQuery<Group> query = ParseQuery.getQuery("Group");
        query.whereEqualTo("name", groupId);
        query.getFirstInBackground(new GetCallback<Group>() {
            public void done(Group g, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    Log.d(TAG, "group retrieved");
                    group = g;

                    //get files here? idk
                    loadFiles();
                } else {
                    // Something went wrong.
                    Log.d(TAG, e.getMessage());
                }
            }
        });
    }

    private void loadFiles() {
        filesToView.clear();
        filesToView.addAll(group.getFiles());
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_file_sharing, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.file_rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mRecyclerView.setAdapter(adapter);

        setupToolbar((Toolbar) rootView.findViewById(R.id.toolbar), "Files");

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.file_sharing_upload_file, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.upload_file:

                if (askForPermission()) {
                    startFileChooser();
                }
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

    private boolean askForPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Access to external storage is necessary to view and save " +
                        "files on your device.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(new String[]{Manifest.permission
                                .WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            } else {

                // No explanation needed, we can request the permission.

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // permission already granted
            return true;
        }

        return false;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    //permission denied, do something
                    Log.d(TAG, "ext. storage permission denied");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("You cannot share files without this permission.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //convert file to byte array then save it as entity in group
    private class SendFileToGroupEndpoint extends AsyncTask<Uri, Void, Void> {
        private Context context;

        private SendFileToGroupEndpoint(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getContext(), "Uploading file...", Toast.LENGTH_LONG).show();
        }

        //need to open endpoint for saving
        //convert file to byte array to save
        //save file to arraylist for files in specific group
        @Override
        protected Void doInBackground(Uri... uri) {

            //File file = new File(uri[0].toString());
            try {
                String path = FileUtils.getPath(context, uri[0]);

                if (path == null) {
                    Toast.makeText(getContext(), "File not shared", Toast.LENGTH_SHORT).show();
                    return null;
                }

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

                final ParseFile parseFile = new ParseFile(file.getName(), bFile);
                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        Log.d(TAG, "file saved to parse");
                        group.add("files", parseFile);
                        group.saveInBackground();
                    }
                });

                Log.d(TAG, "file added to group");
                return null;
            }
            catch(IOException e) {
                Log.e(TAG, e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Toast.makeText(getActivity().getApplicationContext(), "File uploaded!",
                    Toast.LENGTH_LONG).show();

            Log.d(TAG, "first file: " + group.getFiles().get(0).getName());

            filesToView.clear();
            filesToView.addAll(group.getFiles());
            adapter.notifyDataSetChanged();
        }
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.file_context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.save_file:
                    if (askForPermission()) {
                        saveFile();
                    }
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    public void onItemClicked(int index, boolean longClick) {
        selectedFile = adapter.getItem(index);
        selectedFile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                if (e == null) {
                    Log.d(TAG, "file bytes retrieved");
                    fileBytes = bytes;
                } else {
                    Log.d(TAG, e.getMessage());
                }
            }
        });

        if (longClick) {
            Log.d(TAG, "long click save started");
            if (mActionMode != null) {
                return;
            }

            // Start the CAB using the ActionMode.Callback defined above
            mActionMode = getActivity().startActionMode(mActionModeCallback);
        } else {
            //view file in-app (or in another app)
        }
    }

    private void saveFile() {
        if (isExternalStorageWritable()) {
            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment
                    .DIRECTORY_PICTURES), "GroupStudy");

            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }

            try {
                if (success) {
                    Log.d(TAG, "folder path: " + folder.getAbsolutePath());
                    File file = new File(folder.getAbsolutePath(), "hello.jpg");
                    MediaScannerConnection.scanFile(getContext(), new String[] { file.getPath() }, new String[] { "image/jpeg" }, null);
                    BufferedOutputStream buf = new BufferedOutputStream(new FileOutputStream(file));
                    buf.write(fileBytes, 0, fileBytes.length);
                    buf.flush();
                    buf.close();
                }
            }
            catch (FileNotFoundException e) {
                Log.d(TAG, e.getMessage());
                Toast.makeText(getContext(), "File not found", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
