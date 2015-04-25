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
 */
class LoadUserEndpointsAsyncTask extends AsyncTask<Void, Void, List<User>> {
    private static GroupstudyEndpoint usersEndpointApi = null;
    private Context context;
    private OnRetrieveUsersTaskCompleted listener;

    LoadUserEndpointsAsyncTask(Context context, OnRetrieveUsersTaskCompleted listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<User> doInBackground(Void... params) {
        if(usersEndpointApi == null) {  // Only do this once
            GroupstudyEndpoint.Builder builder = new GroupstudyEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://groupstudy-ee-461l.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            usersEndpointApi = builder.build();
        }

        try {
            return usersEndpointApi.loadUsers().execute().getItems();
        } catch (IOException e) {
            Log.i("LoadUserAsync", "" + e.getMessage());
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<User> result) {
        listener.onTaskCompleted(result);
    }
}