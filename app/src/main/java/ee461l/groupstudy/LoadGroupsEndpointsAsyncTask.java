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
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;


/**
 * Created by britne on 4/11/15.
 */
class LoadGroupsEndpointsAsyncTask extends AsyncTask<Void, Void, List<Groups>> {
    private static final String TAG = "LoadGroupsAsync";
    private static GroupstudyEndpoint groupsEndpointApi = null;
    private Context context;
    private OnRetrieveGroupsTaskCompleted listener;
    private GroupsListViewAdapter adapter;

    LoadGroupsEndpointsAsyncTask(GroupsListViewAdapter adapter, Context context, OnRetrieveGroupsTaskCompleted listener) {
        this.context = context;
        this.listener = listener;
        this.adapter = adapter;
    }

    @Override
    protected List<Groups> doInBackground(Void... params) {
        if(groupsEndpointApi == null) {  // Only do this once
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

            groupsEndpointApi = builder.build();
        }

        try {
            List<Groups> groups = groupsEndpointApi.loadGroups().execute().getItems();
            Log.i(TAG, "groups retrieved");

            //no groups have been added yet so objectify returns null
            //not allowed when setting a list adapter so an empty arraylist needs to be created
            if (groups == null)
                groups = new ArrayList<>();

            return groups;
        } catch (IOException e) {
            Log.i(TAG, "" + e.getMessage());
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void onPostExecute(List<Groups> result) {
        adapter.notifyDataSetChanged();
        listener.onRetrieveGroupsCompleted(result);
    }
}