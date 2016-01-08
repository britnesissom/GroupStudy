package ee461l.groupstudy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import ee461l.groupstudy.R;


public class LoginScreenActivity extends AppCompatActivity {

    private static final String TAG = "LoginScreen";
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
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

    public void login(View v) {
        String passwordText = password.getText().toString();

        ParseUser.logInInBackground(username.getText().toString(), passwordText, new LogInCallback
                () {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("username", user.getUsername());
                    startActivity(intent);
                } else {
                    // Login failed. Look at the ParseException to see what happened.
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast
                            .LENGTH_SHORT).show();
                    password.setText("");
                }
            }
        });
    }

    //go to a new create account activity
    public void createAccount(View v) {
        Intent intent = new Intent(this,CreateNewAccountActivity.class);
        startActivity(intent);
    }
}
