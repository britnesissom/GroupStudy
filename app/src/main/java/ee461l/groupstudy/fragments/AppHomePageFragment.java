package ee461l.groupstudy.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ee461l.groupstudy.adapter.GroupsListViewAdapter;
import ee461l.groupstudy.OnRetrieveSingleUserTaskCompleted;
import ee461l.groupstudy.R;
import ee461l.groupstudy.models.Group;
import ee461l.groupstudyendpoints.groupstudyEndpoint.GroupstudyEndpoint;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.User;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * OnFragmentInteractionListener interface
 * to handle interaction events.
 * Use the {@link AppHomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppHomePageFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String USERNAME = "username";
    private static final String TAG = "AppHomePage";

    // TODO: Rename and change types of parameters
    private String menuChoice;
    private List<String> groups;
    private ListView groupsListView;
    private String username;
    private GroupsListViewAdapter adapter;
    private ParseUser user;

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
            username = getArguments().getString(USERNAME);
            Log.d(TAG, "username passed successfully: " + username);
        }

        user = ParseUser.getCurrentUser();

        groups = new ArrayList<>();
        List<Object> list = user.getList("Group");
        for (Object item : list) {
            groups.add((String) item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;   //maybe change this line?
        //actionBar.setTitle("Home");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_36dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //initialize listview and adapter to list groups on home page
        adapter = new GroupsListViewAdapter(getActivity(), R.layout.home_page_groups_list_item,
                groups, username);
        groupsListView = (ListView) rootView.findViewById(R.id.groups_list);
        Log.d(TAG, "Setting adapter");
        groupsListView.setAdapter(adapter);

        //open home page for specified group when it is clicked
        groupsListView.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Home");
        }
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long arg3) {
        Log.d(TAG, "item clicked, position: " + position);
        Toast.makeText(getActivity().getApplicationContext(), "You selected: " + position, Toast.LENGTH_LONG).show();
        // get the list adapter
        GroupsListViewAdapter groupsAdapter = (GroupsListViewAdapter) parent.getAdapter();

        //open home page for selected group
        groupsAdapter.getView(position, view, parent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_page_add_group, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.create_group:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_fragment,
                        AppHomePageFragment.newInstance(username)).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
