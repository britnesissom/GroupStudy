package ee461l.groupstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;


public class LoginScreenActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        LoadUserEndpointsAsyncTask ueat = new LoadUserEndpointsAsyncTask(this, new OnRetrieveUsersTaskCompleted() {
            @Override
            public void onTaskCompleted(List<User> list) {
                users = list;
            }
        });
        ueat.execute();
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

    //make sure the user actually exists before going to new homepage activity
    public void verifyUser(View v) {
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();

        //goes through each user to see if person who is trying to log in exists
        for (int i = 0; i < users.size(); i++) {

            //username, password combo is correct so log in the user
            if (users.get(i).getUsername().equals(usernameText) &&
                    users.get(i).getPassword().equals(passwordText)) {
                Intent intent = new Intent(this, NavDrawerHomePage.class);
                intent.putExtra("username", users.get(i).getUsername());
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Invalid username or password",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    //go to a new create account activity
    public void createAccount(View v) {
        Intent intent = new Intent(this,CreateNewAccountActivity.class);
        startActivity(intent);
    }
}
