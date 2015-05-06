package ee461l.groupstudy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import ee461l.groupstudyendpoints.groupstudyEndpoint.GroupstudyEndpoint;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;


/**
 * Created by britne on 4/11/15.
 * Loads a single user and returns null if the user does not exist
 */
class LoadSingleUserAsyncTask extends AsyncTask<String, Void, User> {
    private static final String TAG = "LoadSingleUserAsync";
    private static GroupstudyEndpoint usersEndpointApi = null;
    private Context context;
    private String activityName;
    private OnRetrieveSingleUserTaskCompleted listener;

    LoadSingleUserAsyncTask(Context context, OnRetrieveSingleUserTaskCompleted listener,
                            String activityName) {
        this.context = context;
        this.listener = listener;
        this.activityName = activityName;
    }

    @Override
    protected User doInBackground(String... username) {
        if(usersEndpointApi == null) {  // Only do this once
            GroupstudyEndpoint.Builder builder = new GroupstudyEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://groupstudy-461l.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            usersEndpointApi = builder.build();
        }

        try {
            User user = usersEndpointApi.retrieveSingleUser(username[0], activityName).execute();
            Log.d(TAG, "user retrieved");
            return user;
        } catch (IOException e) {
            Log.d(TAG, "" + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(User result) {
        listener.onRetrieveUserCompleted(result);
    }
}