package ee461l.groupstudy.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.FilesEntity;

/**
 * Created by britne on 4/27/15.
 * Opens file with device's document viewer
 */
public class OpenFileAsyncTask extends AsyncTask<Void, String, Void> {

    private static final String TAG = "OpenFileAsyncTask";

    private FilesEntity file;
    private Context context;

    public OpenFileAsyncTask(Context context, FilesEntity file) {
        this.file = file;
        this.context = context;
    }

    @Override
    protected void onProgressUpdate(String... message) {
        Toast.makeText(context, message[0], Toast.LENGTH_LONG).show();
    }

    //convert each string of file content into actual file that is downloaded
    @Override
    protected Void doInBackground(Void... params) {

        try {
            Toast.makeText(context, "File downloading...", Toast.LENGTH_LONG).show();
            //publishProgress("File downloading...");
            //pick place to store file
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir = new File(root.getAbsolutePath() + "/downloads/");

            //get file content and convert to bytes to be turned into actual file
            String fileContent = file.getFileContents();
            byte[] fileBytes = fileContent.getBytes("UTF-16");
            FileOutputStream fos = new FileOutputStream(dir.getAbsolutePath());
            fos.write(fileBytes);
            fos.flush();
            fos.close();

        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "incorrect encoding for file");
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }

}

