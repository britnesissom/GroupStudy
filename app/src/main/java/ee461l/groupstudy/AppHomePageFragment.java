package ee461l.groupstudy;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * OnFragmentInteractionListener interface
 * to handle interaction events.
 * Use the {@link AppHomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppHomePageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String USERNAME = "username";

    // TODO: Rename and change types of parameters
    private String menuChoice;
    private List<Groups> groups;
    private ListView groupsListView;
    private String username;

    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(String username) {
        Fragment fragment = new AppHomePageFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    public AppHomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            Log.i("HomePageFragment", "username passed successfully");
            username = getArguments().getString(USERNAME);
        }

        LoadGroupsEndpointsAsyncTask lgeat = new LoadGroupsEndpointsAsyncTask(
                getActivity(), new OnRetrieveGroupsTaskCompleted() {
            @Override
            public void onRetrieveGroupsCompleted(List<Groups> groupsList) {
                groups = groupsList;
            }
        });

        //fragment tries to set list adapter before the groups are actually retrieved
        //obviously this is a problem so the groups need to be retrieved before
        //the adapter can be set

        //defeats the purpose of asynctask though ugh
        try {
            groups = lgeat.execute().get();
        }
        catch(InterruptedException e) {
            Log.e("HomePageFragment", e.getMessage());
        }
        catch(ExecutionException e) {
            Log.e("HomePageFragment", e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home_page, container, false);

        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        getActivity().setTitle("Home");

        //initialize listview and adapter to list groups on home page
        groupsListView = (ListView) rootView.findViewById(R.id.groups_list);
        GroupsListViewAdapter adapter = new GroupsListViewAdapter(getActivity(), groups);
        Log.i("HomePageFragment", "Setting adapter");
        groupsListView.setAdapter(adapter);

        //send group name to new activity so app knows which messages to load
        groupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int pos, long id) {
                Intent intent = new Intent(getActivity(), NavDrawerGroups.class);
                intent.putExtra("groupName", groups.get(pos).getGroupName());
                startActivity(intent);
            }
        });
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
