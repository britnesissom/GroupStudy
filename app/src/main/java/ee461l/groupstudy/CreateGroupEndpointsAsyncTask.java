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

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.GroupWrapperEntity;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;
import ee461l.groupstudyendpoints.groupstudyEndpoint.GroupstudyEndpoint;


/**
 * Created by britne on 4/11/15.
 */
class CreateGroupEndpointsAsyncTask extends AsyncTask<Void, Void, Void> {
    private static GroupstudyEndpoint groupEndpointApi = null;
    private Context context;
    private GroupWrapperEntity groupWrapper;
    private String groupName;
    private String adminUser;
    private ArrayList<User> teammates;

    CreateGroupEndpointsAsyncTask(Context context, String groupName, String adminUser, ArrayList<User> teammates) {
        this.context = context;
        this.groupName = groupName;
        this.adminUser = adminUser;
        this.teammates = teammates;

        groupWrapper = new GroupWrapperEntity();
        Groups group = new Groups();
        group.setGroupName(groupName);
        group.setAdminUser(adminUser);
        group.setUsers(teammates);
        groupWrapper.setGroup(group);
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(groupEndpointApi == null) {  // Only do this once
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