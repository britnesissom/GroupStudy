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

    CreateGroupEndpointsAsyncTask(Context context, String groupName, String adminUser, ArrayList<String> teammates) {
        this.context = context;

        groupWrapper = new GroupWrapperEntity();
        Groups group = new Groups();
        group.setGroupName(groupName);
        group.setAdminUser(adminUser);
        group.setTeammates(teammates);
        groupWrapper.setGroup(group);
    }

    @Override
    protected Groups doInBackground(Void... params) {
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
            Groups group = groupEndpointApi.createGroup(groupWrapper).execute();
            //Log.d(TAG, "admin name: " + group.getAdminUser().getUsername());
            return group;
        } catch (IOException e) {
            Log.d(TAG, "" + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Groups result) {

        //pass name of group and username to next activity so if user creates another group
        //the app knows who the admin should be
        Intent intent = new Intent(context, NavDrawerGroups.class);
        intent.putExtra("group name", result.getGroupName());
        intent.putExtra("username", result.getAdminUser());
        context.startActivity(intent);
    }
}