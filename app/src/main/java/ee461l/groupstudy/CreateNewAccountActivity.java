package ee461l.groupstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;


/**
 * Created by Brian on 4/1/2015.
 */
public class CreateNewAccountActivity extends AppCompatActivity {

    private static final String TAG = "CreateNewAccount";

    EditText username;
    EditText createPassword;
    EditText confirmPassword;
    Button createAccountButton;
    List<User> users;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_account);

        username = (EditText) findViewById(R.id.create_username);
        createPassword = (EditText) findViewById(R.id.create_password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        createAccountButton = (Button) findViewById(R.id.create_account_button);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_create_account, menu);
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

    public void createNewAccount(View v) {
        String usernameText = username.getText().toString();

        //make sure user does not already exist
        LoadSingleUserAsyncTask lsuat = new LoadSingleUserAsyncTask(this, new OnRetrieveSingleUserTaskCompleted() {
            @Override
            public void onRetrieveUserCompleted(User userLogin) {
                user = userLogin;
            }
        });

        //fix this because it defeats purpose of async task
        try {
            user = lsuat.execute(username.getText().toString()).get();
        }
        catch(InterruptedException e) {
            Log.e(TAG, "InterruptedException: " + e.getMessage());
        }
        catch(ExecutionException e) {
            Log.e(TAG, "ExecutionException: " + e.getMessage());
        }

        //if user doesn't exist, add new user to list of users
        //else, tell them to choose a new name
        if (user != null) {
            Log.i(TAG, "user exists");
            //be sure to change to NavDrawerHomePage!
            //makes sure the inputted passwords are the same
            //makes sure user doesn't already exist
            if (createPassword.getText().toString().equals(confirmPassword.getText().toString())) {

                //add user to server
                CreateUserEndpointsAsyncTask cueat = new CreateUserEndpointsAsyncTask(this);
                cueat.execute(usernameText, createPassword.getText().toString());

                Intent intent = new Intent(this, NavDrawerHomePage.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Passwords do not match",
                        Toast.LENGTH_LONG).show();
                clearTextFields();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Username already taken",
                    Toast.LENGTH_LONG).show();
            clearTextFields();
        }
    }
    
    private void clearTextFields() {
        createPassword.setText("");
        confirmPassword.setText("");
    }
/*

    private boolean checkIfUserExists(String user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user))
                return true;
        }
        return false;
    }
*/

}
