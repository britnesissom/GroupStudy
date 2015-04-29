package ee461l.groupstudy;

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

import java.util.List;
import java.util.concurrent.ExecutionException;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;


public class LoginScreenActivity extends AppCompatActivity {

    private static final String TAG = "LoginScreen";
    private EditText username;
    private EditText password;
    private User user;

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

    //make sure the user actually exists before going to new homepage activity
    public void verifyUser(View v) {
        String passwordText = password.getText().toString();

        LoadSingleUserAsyncTask lsuat = new LoadSingleUserAsyncTask(this, new OnRetrieveSingleUserTaskCompleted() {
            @Override
            public void onRetrieveUserCompleted(User userLogin) {
                user = userLogin;
            }
        }, TAG);

        //defeats purpose of async task
        try {
            user = lsuat.execute(username.getText().toString()).get();
        }
        catch(InterruptedException e) {
            Log.e(TAG, "InterruptedException: " + e.getMessage());
        }
        catch(ExecutionException e) {
            Log.e(TAG, "ExecutionException: " + e.getMessage());
        }

        //username, password combo is correct so log in the user
        if (user != null && user.getPassword().equals(passwordText)) {
            Intent intent = new Intent(this, NavDrawerHomePage.class);
            intent.putExtra("username", user.getUsername());
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid username or password",
                    Toast.LENGTH_LONG).show();
        }
    }

    //go to a new create account activity
    public void createAccount(View v) {
        Intent intent = new Intent(this,CreateNewAccountActivity.class);
        startActivity(intent);
    }
}
