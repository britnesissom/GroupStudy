package ee461l.groupstudy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ee461l.groupstudyendpoints.groupstudyEndpoint.GroupstudyEndpoint;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.GroupWrapperEntity;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;


public class AddGroupActivity extends AppCompatActivity {

    private static final String TAG = "AddGroupActivity";

    private EditText groupName;
    private EditText teammates;
    private List<User> users;
    private User adminUser;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        Log.i(TAG, "AddGroup created");

        user = getIntent().getStringExtra("username");

        //retrieves user to set as admin
        LoadSingleUserAsyncTask lsuat = new LoadSingleUserAsyncTask(this, new OnRetrieveSingleUserTaskCompleted() {
            @Override
            public void onRetrieveUserCompleted(User user) {
                adminUser = user;
            }
        });
        lsuat.execute(user);

        groupName = (EditText) findViewById(R.id.group_name);
        teammates = (EditText) findViewById(R.id.teammates);

        //retrieve list of users to find them using what the user typed in
        LoadUsersEndpointsAsyncTask lueat = new LoadUsersEndpointsAsyncTask(this, new OnRetrieveUsersTaskCompleted() {
            @Override
            public void onTaskCompleted(List<User> list) {
                users = list;
            }
        });
        lueat.execute();
    }

    public void createGroup(View view) {
        //needs to be called here again b/c if 2 groups are created in a row
        //there is no username to pass to the async task

        String delimiters = ",[ ]*";
        String nameOfGroup = groupName.getText().toString();
        String usernames = teammates.getText().toString();
        String[] usernamesArray = teammates.getText().toString().split(delimiters);
        ArrayList<User> listOfUsers = getUsersFromArray(usernamesArray);

        Log.i(TAG, "CreateGroup async about to be called");
        CreateGroupEndpointsAsyncTask cgeat = new CreateGroupEndpointsAsyncTask(this, nameOfGroup,
                adminUser, listOfUsers);
        cgeat.execute();
    }

    //group needs arraylist of users not just their usernames
    //find each user and add to arraylist

    //FIX THIS CODE LATER BECAUSE IT IS VERY SLOW - O(n^2)
    private ArrayList<User> getUsersFromArray(String[] usernames) {
        ArrayList<User> listOfUsers = new ArrayList<>();
        for (int i = 0; i < usernames.length; i++) {
            for (int j = i; j < users.size(); j++) {
                if (usernames[i].equals(users.get(j).getUsername())) {
                    listOfUsers.add(users.get(j));
                }
            }
        }
        return listOfUsers;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CreateGroupEndpointsAsyncTask extends AsyncTask<Void, Void, Groups> {

        private static final String TAG = "CreateGroupAsync";
        private GroupstudyEndpoint groupEndpointApi = null;
        private Context context;
        private GroupWrapperEntity groupWrapper;

        CreateGroupEndpointsAsyncTask(Context context, String groupName, User adminUser, ArrayList<User> teammates) {
            this.context = context;

            groupWrapper = new GroupWrapperEntity();
            Groups group = new Groups();
            group.setGroupName(groupName);
            group.setAdminUser(adminUser);
            group.setUsers(teammates);
            group.setFiles(new ArrayList<String>());
            group.setMessages(new ArrayList<String>());
            group.setTasks(new ArrayList<String>());
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
                        .setRootUrl("https://groupstudy-461l.appspot.com/_ah/api")
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
            Log.i(TAG, "group name createGroup: " + result.getGroupName());
            intent.putExtra("groupName", result.getGroupName());
            intent.putExtra("username", result.getAdminUser().getUsername());
            context.startActivity(intent);
        }
    }
}
