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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        String admin = getIntent().getStringExtra("username");

        //retrieves user to set as admin
        LoadSingleUserAsyncTask lsuat = new LoadSingleUserAsyncTask(this, new OnRetrieveSingleUserTaskCompleted() {
            @Override
            public void onRetrieveUserCompleted(User user) {
                adminUser = user;
            }
        });
        lsuat.execute(admin);

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

    /*
    adds listener to spinner to retrieve the selection for each department
    spinner and use it to pick the correct class
     */
    //this is for if we have time to create dynamic spinners that retrieve a list of people in that class

    /*public void addListenerOnSpinnerSelection() {
        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //if spinner hasn't initialized yet then don't try to get selected item
                //because it will be null
                if (initializedView == false) {
                    initializedView = true;
                } else {
                    if (departmentSpinner.getSelectedItem().toString().equals("Electrical Engineering")) {
                        populateClassSpinner("ee");
                    } else if (departmentSpinner.getSelectedItem().toString().equals("Mathematics")) {
                        populateClassSpinner("math");
                    }
                }
            }

            //option if user has just opened app or never actually chooses a type
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/

    public void createGroup(View view) {
        String delimiters = ",[ ]*";
        String nameOfGroup = groupName.getText().toString();
        String usernames = teammates.getText().toString();
        String[] usernamesArray = teammates.getText().toString().split(delimiters);
        ArrayList<User> listOfUsers = getUsersFromArray(usernamesArray);
        Log.i(TAG,"" + usernames);
        CreateGroupEndpointsAsyncTask cgeat = new CreateGroupEndpointsAsyncTask(this, nameOfGroup,
                adminUser, listOfUsers);
        cgeat.execute();

        //pass name of group and username to next activity so if user creates another group
        //the app knows who the admin should be
        Intent intent = new Intent(this, NavDrawerGroups.class);
        intent.putExtra("group name", nameOfGroup);
        intent.putExtra("username", getIntent().getStringExtra("username"));
        startActivity(intent);
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
