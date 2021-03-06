package ee461l.groupstudy;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupHomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupHomePageFragment extends Fragment implements OnRetrieveSingleGroupTaskCompleted {
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
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    private View rootView;


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

        LoadSingleGroupAsyncTask lsgat = new LoadSingleGroupAsyncTask(getActivity(), this);

        Log.d(TAG, "group name: " + groupName);
        lsgat.execute(groupName);
    }

    @Override
    public void onRetrieveSingleGroupCompleted(Groups g) {
        group = g;
        Log.d(TAG, "group name: " + group.getGroupName());
        refreshData();

        if (isAdded()) {
            getActivity().invalidateOptionsMenu();  //updates options menu because group has been retrieved
        }
    }

    private View refreshData() {

        if (rootView == null || mContainer == null || mInflater == null) {
            Log.d(TAG, "rootView or container or inflater is null");
            return null;
        }
        //need to reinflate view to set the title and update textviews
        rootView = mInflater.inflate(R.layout.fragment_group_home_page, mContainer, false);

        Log.d(TAG, "refreshData: " + group.getGroupName());

        if (isAdded()) {
            getActivity().setTitle(group.getGroupName() + " Home");
        }

        List<String> tasks = group.getTasks();
        List<String> messages = group.getMessages();

        //view first three (if there are that many) upcoming events on group home screen
        if (tasks != null) {
            Collections.sort(tasks);
            Collections.sort(tasks, new Comparator<String>(){
                public int compare(String a, String b){
                    String[] as = a.split("/");
                    String[] bs = b.split("/");
                    int result = Integer.valueOf(as[0]).compareTo(Integer.valueOf(bs[0]));
                    if(result==0)
                        result = Integer.valueOf(as[1]).compareTo(Integer.valueOf(bs[1]));
                    return result;
                }
            });

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
                for (int i = 0; i < 2; i++) {
                    newMessages.append(messages.get(i) + "\n");
                }
            } else {
                for (int i = 0; i < messages.size(); i++) {
                    newMessages.append(messages.get(i) + "\n");
                }
            }
        }

        return rootView;
    }

    //will display most recent messages and next three upcoming tasks
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        mContainer = container;
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_group_home_page, container, false);

        upcomingTasks = (TextView) rootView.findViewById(R.id.upcoming_tasks);
        newMessages = (TextView) rootView.findViewById(R.id.new_messages);

        Log.d(TAG, "fragment view created");

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
            if (group.getAdminUser().equals(username))
                inflater.inflate(R.menu.menu_admin_user, menu);
        }

    }

    //use an alert dialog to type member to add/delete
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.add_member:
                addMember();
                return true;
            case R.id.delete_member:
                deleteMember();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addMember() {

        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.add_member_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.addMemberInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //add member here
                                String memberToAdd = userInput.getText().toString();

                                AddMemberAsyncTask amat = new AddMemberAsyncTask(getActivity(), groupName);
                                amat.execute(memberToAdd);

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteMember() {
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.delete_member_layout, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.removeMemberInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Remove",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String memberToRemove = userInput.getText().toString();

                                //delete member here
                                DeleteMemberAsyncTask dmat = new DeleteMemberAsyncTask(getActivity(), groupName);
                                dmat.execute(memberToRemove);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
