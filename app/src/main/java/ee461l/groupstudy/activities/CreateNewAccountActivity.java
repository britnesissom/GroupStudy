package ee461l.groupstudy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

import ee461l.groupstudy.R;
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


    public void create(View v) {
        final String usernameText = username.getText().toString();

        if (createPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            ParseUser user = new ParseUser();
            user.setUsername(usernameText);
            user.setPassword(createPassword.getText().toString());
            user.put("groups", null);

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // Hooray! Let them use the app now.
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("username", usernameText);
                        startActivity(intent);
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                        Log.d("NewAccount", e.getMessage());

                        if (e.getCode() == ParseException.USERNAME_TAKEN) {
                            Toast.makeText(getApplicationContext(), "Username already taken",
                                    Toast.LENGTH_SHORT).show();
                            clearTextFields();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Passwords do not match",
                    Toast.LENGTH_LONG).show();
            clearTextFields();
        }

    }
    
    private void clearTextFields() {
        username.setText("");
        createPassword.setText("");
        confirmPassword.setText("");
    }

}
