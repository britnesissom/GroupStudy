package ee461l.groupstudy;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupHomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupHomePageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "GroupHomePage";
    private static final String USERNAME = "username";
    private static final String GROUP_NAME = "groupName";

    // TODO: Rename and change types of parameters
    private String username;
    private String groupName;
    private Groups group;

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

        Log.d(TAG, "Group home fragment instantiated");
        return fragment;
    }

    public GroupHomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Log.d(TAG, "Group home fragment created");

        if (getArguments() != null) {
            username = getArguments().getString(USERNAME);
            groupName = getArguments().getString(GROUP_NAME);
        }

        LoadSingleGroupAsyncTask lsgat = new LoadSingleGroupAsyncTask(getActivity(), new OnRetrieveSingleGroupTaskCompleted() {
            @Override
            public void onRetrieveSingleGroupCompleted(Groups g) {
                group = g;
            }
        });

        try {
            Log.d(TAG, "group name: " + groupName);
            group = lsgat.execute(groupName).get();
        }
        catch (InterruptedException e) {
            Log.e(TAG, e.getMessage());
        }
        catch (ExecutionException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    //will display most recent messages and next three upcoming tasks
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group_home_page, container, false);
        getActivity().setTitle(group.getGroupName() + " Home");
        Log.d(TAG, "Group home fragment view created");
        //Log.d(TAG, "admin username: " + group.getAdminUser().getUsername());

        upcomingTasks = (TextView) rootView.findViewById(R.id.upcoming_tasks);
        newMessages = (TextView) rootView.findViewById(R.id.new_messages);

        List<String> tasks = group.getTasks();
        List<String> messages = group.getMessages();

        //view first three (if there are that many) upcoming events on group home screen
        if (tasks != null) {
            Collections.sort(tasks);    //sorts tasks by date - most recent first

            if (tasks.size() > 3) {
                for (int i = 0; i < 3; i++) {
                    upcomingTasks.append(tasks.get(i) + "\n");
                }
            } else {
                for (int i = 0; i < tasks.size(); i++) {
                    upcomingTasks.append(tasks.get(i) + "\n");
                }
            }
        }

        //view two most recent (if there are that many) messages on group home screen
        //might show your own messages woops
        if (messages != null) {

            if (messages.size() > 2) {
                //the newest messages are at the end of the array list
                for (int i = messages.size()-1; i > messages.size()-3; i--) {
                    newMessages.append(messages.get(i) + "\n");
                }
            } else {
                for (int i = messages.size()-1; i > 0; i--) {
                    newMessages.append(messages.get(i) + "\n");
                }
            }
        }

        return rootView;
    }
}
