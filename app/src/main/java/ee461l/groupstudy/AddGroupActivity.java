package ee461l.groupstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

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
}
