package ee461l.groupstudy;


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
import android.widget.AdapterView;
import android.widget.ListView;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupHomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupHomePageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USERNAME = "username";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String username;
    private Groups group;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment GroupHomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupHomePageFragment newInstance(String username) {
        GroupHomePageFragment fragment = new GroupHomePageFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        fragment.setArguments(args);
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
        }

        LoadSingleGroupAsyncTask lsgat = new LoadSingleGroupAsyncTask(getActivity(), new OnRetrieveSingleGroupTaskCompleted() {
            @Override
            public void onRetrieveSingleGroupCompleted(Groups g) {
                group = g;
            }
        });
    }

    //will display most recent messages and next three upcoming tasks
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_group_home_page, container, false);
        getActivity().setTitle(group.getGroupName() + " Home");

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_page_add_group, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.create_group:
                Intent intent = new Intent(getActivity(), AddGroupActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
