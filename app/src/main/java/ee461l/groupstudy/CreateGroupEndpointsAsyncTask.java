package ee461l.groupstudy;

import android.content.Context;
import android.content.Intent;
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
class CreateGroupEndpointsAsyncTask extends AsyncTask<Void, Void, Groups> {

    private static final String TAG = "CreateGroupAsync";
    private static GroupstudyEndpoint groupEndpointApi = null;
    private Context context;
    private GroupWrapperEntity groupWrapper;

    CreateGroupEndpointsAsyncTask(Context context, String groupName, User adminUser, ArrayList<User> teammates) {
        this.context = context;

        groupWrapper = new GroupWrapperEntity();
        Groups group = new Groups();
        group.setGroupName(groupName);
        group.setAdminUser(adminUser);
        group.setUsers(teammates);
        groupWrapper.setGroup(group);
    }

    @Override
    protected Groups doInBackground(Void... params) {
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
            //not creating group, likely due to the User class
            Groups group = groupEndpointApi.createGroup(groupWrapper).execute();
            Log.i(TAG, "admin name: " + group.getAdminUser().getUsername());
            return group;
        } catch (IOException e) {
            Log.i(TAG, "" + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Groups result) {

        //pass name of group and username to next activity so if user creates another group
        //the app knows who the admin should be
        Intent intent = new Intent(context, NavDrawerGroups.class);
        intent.putExtra("group name", result.getGroupName());
        intent.putExtra("username", result.getAdminUser().getUsername());
        context.startActivity(intent);
    }
}