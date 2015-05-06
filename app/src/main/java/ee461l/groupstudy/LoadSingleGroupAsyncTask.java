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
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;


/**
 * Created by britne on 4/11/15.
 */
class LoadSingleGroupAsyncTask extends AsyncTask<String, Void, Groups> {
    private static final String TAG = "LoadSingleGroupAsync";
    private static GroupstudyEndpoint groupEndpointApi = null;
    private Context context;
    private OnRetrieveSingleGroupTaskCompleted listener;

    LoadSingleGroupAsyncTask(Context context, OnRetrieveSingleGroupTaskCompleted listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Groups doInBackground(String... groupName) {
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

        try {
            Groups group = groupEndpointApi.retrieveSingleGroup(groupName[0]).execute();
            Log.d(TAG, "group retrieved: " + group.getGroupName());
            return group;
        } catch (IOException e) {
            Log.d(TAG, "Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Groups result) {
        Log.d(TAG, "group retrieved onpostexecute: " + result.getGroupName());
        listener.onRetrieveSingleGroupCompleted(result);
    }
}