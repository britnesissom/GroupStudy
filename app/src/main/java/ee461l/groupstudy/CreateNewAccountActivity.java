package ee461l.groupstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Brian on 4/1/2015.
 */
public class CreateNewAccountActivity extends ActionBarActivity {

    EditText username;
    EditText createPassword;
    EditText confirmPassword;
    TextView nonmatchingPasswords;
    Button createAccountButton;
    UserList userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_account);

        userList = getIntent().getParcelableExtra("user list");

        username = (EditText) findViewById(R.id.create_username);
        createPassword = (EditText) findViewById(R.id.create_password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        nonmatchingPasswords = (TextView) findViewById(R.id.nonmatching_passwords);
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

        //makes sure the inputted passwords are the same
        //makes sure user doesn't already exist
        if (createPassword.getText().toString().equals(confirmPassword.getText().toString())) {

            //if user doesn't exist, add new user to list of users
            //else, tell them to choose a new name
            if (checkIfUserExists(usernameText) == false) {
                User user = new User(usernameText, createPassword.getText().toString());
                userList.add(user);

                //be sure to change to NavDrawerHomePage!
                Intent intent = new Intent(this, NavDrawerHomePage.class);
                intent.putExtra("user list", userList);
                startActivity(intent);
            } else {
                nonmatchingPasswords.setText("Username already taken");
                nonmatchingPasswords.setVisibility(View.VISIBLE);
            }
        } else {
            nonmatchingPasswords.setText("Passwords do not match");
            nonmatchingPasswords.setVisibility(View.VISIBLE);
            clearTextFields();
        }
    }
    
    private void clearTextFields() {
        createPassword.setText("");
        confirmPassword.setText("");
    }

    private boolean checkIfUserExists(String user) {
        return userList.checkIfUserExists(user);
    }

}
