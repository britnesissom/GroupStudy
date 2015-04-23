package ee461l.groupstudy;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by britne on 4/21/15.
 */
public class RetrieveFilesFromServer {

    private List<File> files;
    private static final String TAG = "RetrieveFilesFromServer";
    private String url = "http://groupstudy-ee-461l.appspot.com/";
    private String groupName;

    public RetrieveFilesFromServer(String groupName) {
        this.groupName = groupName;
        files = new ArrayList<>();

        RetrieveFilesFromServerHelper helper = new RetrieveFilesFromServerHelper();

        //will need to change b/c multiple files in a group = different urls
        helper.execute(this.url+this.groupName);
    }

    public List<File> getFiles() {
        return this.files;
    }

    private class RetrieveFilesFromServerHelper extends AsyncTask<String,Void,Void> {
        //how do I get url of file from server??
        @Override
        protected Void doInBackground(String... serverUrls) {

            try {
                URL url = new URL(serverUrls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);

                //connect
                urlConnection.connect();

                //create a new file, to save the downloaded file
                File file = new File("downloaded_file");

                FileOutputStream fileOutput = new FileOutputStream(file);

                //Stream used for reading the data from the internet
                InputStream inputStream = urlConnection.getInputStream();

                //create a buffer...
                byte[] buffer = new byte[1024];
                int bufferLength = 0;

                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, bufferLength);
                }

                //save data to file
                fileOutput.flush();

                //close the output stream when complete //
                fileOutput.close();
                inputStream.close();

                files.add(file);

            } catch (final MalformedURLException e) {
                Log.i(TAG, "Error : MalformedURLException " + e);
                e.printStackTrace();
            } catch (final IOException e) {
                Log.i(TAG, "Error : IOException " + e);
                e.printStackTrace();
            } catch (final Exception e) {
                Log.i(TAG, "Error : Please check your internet connection " + e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //view file in app!!
        }
    }
}
