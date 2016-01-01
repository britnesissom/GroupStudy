package ee461l.groupstudy.fragments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ee461l.groupstudy.R;
import ee461l.groupstudy.models.Group;
import ee461l.groupstudy.models.Message;
import ee461l.groupstudy.models.Task;

public class CreateGroupFragment extends BaseFragment {

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

        setupToolbar((Toolbar) rootView.findViewById(R.id.toolbar), "Create a Group");

        Button button = (Button) rootView.findViewById(R.id.create_group);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
            }
        });


        return rootView;
    }

    private void createGroup() {
        //needs to be called here again b/c if 2 groups are created in a row
        //there is no username to pass to the async task

        String delimiters = ",[ ]*";
        final String nameOfGroup = groupName.getText().toString();
        String[] usernamesArray = teammates.getText().toString().split(delimiters);
        final List<String> teammates = new ArrayList<>();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContainedIn("username", Arrays.asList(usernamesArray));
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for (ParseUser user : objects) {
                        Log.d(TAG, "teammate's username: " + user.getUsername());

                        //saves objectId of user rather than entire user object
                        teammates.add(user.getObjectId());

                        //add group to teammate
                        user.add("Groups", nameOfGroup);
                    }
                    ParseObject.saveAllInBackground(objects);
                } else {
                    // Something went wrong.
                    Log.d(TAG, e.getMessage());
                }
            }
        });

        Group group = new Group();
        group.put("name", nameOfGroup);
        group.put("members", teammates);
        group.put("admin", user.getObjectId());
        group.put("messages", new ArrayList<Message>());
        group.put("tasks", new ArrayList<Task>());
        group.put("files", new ArrayList<ParseFile>());

        group.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.d(TAG, "group save completed");
            }
        });

        user.add("Groups", nameOfGroup);
        user.saveInBackground();

        NavigationView nv = (NavigationView) getActivity().findViewById(R.id.nav_view);
        nv.getMenu().clear();
        nv.inflateMenu(R.menu.menu_group_nav);
        nv.getMenu().getItem(0).setChecked(true);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_fragment,
                GroupHomePageFragment.newInstance(groupName.getText().toString(), user.getUsername())).commit();
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
