package ee461l.groupstudy.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ee461l.groupstudy.utils.MemberUtils;
import ee461l.groupstudy.R;
import ee461l.groupstudy.models.Group;
import ee461l.groupstudy.models.Message;
import ee461l.groupstudy.models.Task;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupHomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupHomePageFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "GroupHomePage";
    private static final String USERNAME = "username";
    private static final String GROUP_NAME = "groupName";

    // TODO: Rename and change types of parameters
    private String username;
    private String groupName;
    private Group group;

    private TextView upcomingTasks;
    private TextView newMessages;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment GroupHomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupHomePageFragment newInstance(String groupName, String username) {
        GroupHomePageFragment fragment = new GroupHomePageFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        args.putString(GROUP_NAME, groupName);
        fragment.setArguments(args);

        //Log.d(TAG, "Group home fragment instantiated");
        return fragment;
    }

    public GroupHomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            username = getArguments().getString(USERNAME);
            groupName = getArguments().getString(GROUP_NAME);

            //Log.d(TAG, "group name: " + groupName);
        }

        ParseQuery<Group> query = ParseQuery.getQuery("Group");
        query.include("messages");
        query.include("tasks");
        query.whereEqualTo("name", groupName);
        query.getFirstInBackground(new GetCallback<Group>() {
            public void done(Group g, ParseException e) {
                if (e == null) {
                    //Log.d(TAG, "group name: " + g.getGroupName());
                    group = g;

                    //updateTasks();
                    updateMessages();

                    if (isAdded()) {
                        getActivity().invalidateOptionsMenu();  //updates options menu because group
                        // has been retrieved
                    }
                } else {
                    // something went wrong
                    Log.d(TAG, "error: " + e.getMessage());
                }
            }
        });
    }

    private void updateTasks() {
        //Log.d(TAG, "refreshData: " + group.get("name"));

        List<Task> tasks = group.getTasks();

        //view first three (if there are that many) upcoming events on group home screen
        if (tasks != null) {

            //sorts by nearest date first
            Collections.sort(tasks, new Comparator<Task>() {
                public int compare(Task a, Task b){

                    SimpleDateFormat from = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);

                    try {
                        Log.d(TAG, "before date parsing");
                        Date date1 = from.parse(a.getDate() + " " + a.getTime());
                        Date date2 = from.parse(b.getDate() + " " + b.getTime());

                        if (date1.getTime() < date2.getTime()) {
                            Log.d(TAG, "return -1");
                            return -1;
                        } else if (date1.getTime() == date2.getTime()) {
                            Log.d(TAG, "return 0");
                            return 0;
                        } else if (date1.getTime() > date2.getTime()) {
                            Log.d(TAG, "return 1");
                            return 1;
                        }
                    }
                    catch (java.text.ParseException e) {
                        Log.d(TAG, e.getMessage());
                    }

                    Log.d(TAG, "unreachable code");
                    return 0;   //shouldn't reach here unless something is wrong
                }
            });

            if (tasks.size() > 3) {
                for (int i = 0; i < 3; i++) {
                    upcomingTasks.append(tasks.get(i).getDate() + " - " + tasks.get(i).getDescription() + "\n");
                }
            } else {
                for (int i = 0; i < tasks.size(); i++) {
                    upcomingTasks.append(tasks.get(i).getDate() + " - " + tasks.get(i).getDescription() + "\n");
                }
            }
        }
    }

    private void updateMessages() {

        List<Message> messages = group.getList("messages");

        //view two most recent (if there are that many) messages on group home screen
        //might show your own messages woops
        if (messages != null) {

            if (messages.size() > 2) {
                setMessages(messages, 2);

            } else {
                setMessages(messages, messages.size());
            }
        }
    }

    private void setMessages(List<Message> messages, int size) {
        for (int i = 0; i < size; i++) {
            //if message wasn't written by you, show it
            if (!messages.get(i).getAuthor().equals(username)) {
                newMessages.append(messages.get(i).getAuthor() + ": " + messages.get(i).getMessageText() +
                        "\n");
            } else {
                //I feel like there's an error here, come back later
                if (size > 2 && size < messages.size()) {
                    size += 1;
                }
            }

        }
    }

    //will display most recent messages and next three upcoming tasks
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group_home_page, container, false);

        upcomingTasks = (TextView) rootView.findViewById(R.id.upcoming_tasks);
        newMessages = (TextView) rootView.findViewById(R.id.new_messages);

        setupToolbar((Toolbar) rootView.findViewById(R.id.toolbar), "Home");

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        if (group == null) {
            super.onCreateOptionsMenu(menu, inflater);
        } else {
            //this menu has the add/delete member capability
            //generic menu otherwise
            ParseUser user = ParseUser.getCurrentUser();

            //current user is the admin user so they can add and delete members from the group
            if (group.getAdminUser().equals(user.getObjectId())) {
                inflater.inflate(R.menu.menu_admin_user, menu);
            }
        }
    }

    //use an alert dialog to type member to add/delete
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MemberUtils memberUtils = new MemberUtils(getContext(), group);
        // handle item selection
        switch (item.getItemId()) {
            case R.id.add_member:
                memberUtils.addMember();
                return true;
            case R.id.delete_member:
                memberUtils.deleteMember();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
