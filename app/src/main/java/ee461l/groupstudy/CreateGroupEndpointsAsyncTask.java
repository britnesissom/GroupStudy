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

import ee461l.groupstudyendpoints.groupsEndpoint.GroupsEndpoint;
import ee461l.groupstudyendpoints.groupsEndpoint.model.GroupWrapperEntity;


/**
 * Created by britne on 4/11/15.
 */
class CreateGroupEndpointsAsyncTask extends AsyncTask<Void, Void, Void> {
    private static GroupsEndpoint groupEndpointApi = null;
    private Context context;
    private GroupWrapperEntity groupWrapper;

    CreateGroupEndpointsAsyncTask(Context context, String groupName, String adminUser, ArrayList<String> teammates) {
        this.context = context;

        groupWrapper = new GroupWrapperEntity();
        groupWrapper.setGroupName(groupName);
        groupWrapper.setAdminUser(adminUser);
        groupWrapper.setUsers(teammates);
        groupWrapper.setMessages(new ArrayList<String>());
        groupWrapper.setTasks(new ArrayList<String>());
        groupWrapper.setFileUris(new ArrayList<String>());
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(groupEndpointApi == null) {  // Only do this once
            GroupsEndpoint.Builder builder = new GroupsEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
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

            groupEndpointApi = builder.build();
        }

        //createUser(name, password)
        try {
            groupEndpointApi.createGroup(groupWrapper).execute();
        } catch (IOException e) {
            Log.i("CreateGroupAsync", "" + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result){
    }
}