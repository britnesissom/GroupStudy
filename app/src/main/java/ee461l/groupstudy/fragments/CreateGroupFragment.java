package ee461l.groupstudy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ee461l.groupstudy.R;


public class CreateGroupFragment extends Fragment {

    private static final String TAG = "CreateGroupActivity";
    public static final String USERNAME = "username";

    private EditText groupName;
    private EditText teammates;
    private ParseUser user;

    public static Fragment newInstance(String username) {
        Fragment fragment = new CreateGroupFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    public CreateGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Log.d(TAG, "AddGroup created");

        user = ParseUser.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_group, container, false);
        groupName = (EditText) rootView.findViewById(R.id.group_name);
        teammates = (EditText) rootView.findViewById(R.id.teammates);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        //toolbar.setTitle("Home");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;   //maybe change this line?
        actionBar.setTitle("Home");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_36dp);
        actionBar.setDisplayHomeAsUpEnabled(true);


        return rootView;
    }

    public void createGroup(View view) {
        //needs to be called here again b/c if 2 groups are created in a row
        //there is no username to pass to the async task

        String delimiters = ",[ ]*";
        String nameOfGroup = groupName.getText().toString();
        String[] usernamesArray = teammates.getText().toString().split(delimiters);
        final List<ParseUser> teammates = new ArrayList<>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContainedIn("username", Arrays.asList(usernamesArray));
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for (ParseUser user : objects) {
                        teammates.add(user);
                    }
                } else {
                    // Something went wrong.
                    Log.d(TAG, e.getMessage());
                }
            }
        });

        ParseObject group = new ParseObject("Group");
        group.put("name", nameOfGroup);
        group.put("members", teammates);
        group.put("adminUser", user);
        group.put("messages", null);
        group.put("tasks", null);
        group.put("files", null);

        group.saveInBackground();

        user.add("groups", nameOfGroup);
        user.saveInBackground();


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_fragment,
                GroupHomePageFragment.newInstance(user.getUsername(), group.getObjectId())).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_group, menu);
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
