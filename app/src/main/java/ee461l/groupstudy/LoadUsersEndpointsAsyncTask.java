package ee461l.groupstudy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ee461l.groupstudyendpoints.groupstudyEndpoint.GroupstudyEndpoint;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;


/**
 * Created by britne on 4/11/15.
 */
class LoadUsersEndpointsAsyncTask extends AsyncTask<Void, Void, List<User>> {
    private static GroupstudyEndpoint usersEndpointApi = null;
    private Context context;
    private OnRetrieveUsersTaskCompleted listener;

    LoadUsersEndpointsAsyncTask(Context context, OnRetrieveUsersTaskCompleted listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<User> doInBackground(Void... params) {
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
            List<User> users = usersEndpointApi.loadUsers().execute().getItems();

            //no users have been added yet so objectify returns null
            //not allowed when setting a list adapter so an empty arraylist needs to be created
            if (users == null)
                users = new ArrayList<>();

            return users;
        } catch (IOException e) {
            Log.d("LoadUsersAsync", "" + e.getMessage());
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<User> result) {
        listener.onTaskCompleted(result);
    }
}