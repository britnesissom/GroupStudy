package ee461l.groupstudy;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ee461l.groupstudyendpoints.groupstudyEndpoint.GroupstudyEndpoint;
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
    private static final String TAG = "AppHomePage";

    // TODO: Rename and change types of parameters
    private String menuChoice;
    private List<Groups> groups;
    private ListView groupsListView;
    private String username;
    private GroupsListViewAdapter adapter;

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
            Log.i(TAG, "username passed successfully: " + username);
        }

        groups = new ArrayList<>();

        LoadGroupsEndpointsAsyncTask lgeat = new LoadGroupsEndpointsAsyncTask(getActivity(),
                new OnRetrieveGroupsTaskCompleted() {
            @Override
            public void onRetrieveGroupsCompleted(List<Groups> groupsList) {
                groups = groupsList;
            }
        });
        lgeat.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home_page, container, false);

        View rootView = inflater.inflate(R.layout.fragment_home_page, container, false);
        getActivity().setTitle("Home");

        //initialize listview and adapter to list groups on home page
        adapter = new GroupsListViewAdapter(getActivity(), R.layout.home_page_groups_list_item, groups);
        groupsListView = (ListView) rootView.findViewById(R.id.groups_list);
        Log.i(TAG, "Setting adapter");
        groupsListView.setAdapter(adapter);

        //open home page for specified group when it is clicked
        groupsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long arg3) {
                Log.i(TAG, "item clicked");
                // get the list adapter
                GroupsListViewAdapter groupsAdapter = (GroupsListViewAdapter) parent.getAdapter();

                //get view/open website for selected recipe
                groupsAdapter.getView(position, view, parent);
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

    private class LoadGroupsEndpointsAsyncTask extends AsyncTask<Void, Void, List<Groups>> {
        private GroupstudyEndpoint groupsEndpointApi = null;
        private Context context;
        private OnRetrieveGroupsTaskCompleted listener;

        LoadGroupsEndpointsAsyncTask(Context context, OnRetrieveGroupsTaskCompleted listener) {
            this.context = context;
            this.listener = listener;
        }

        @Override
        protected List<Groups> doInBackground(Void... params) {
            if(groupsEndpointApi == null) {  // Only do this once
                GroupstudyEndpoint.Builder builder = new GroupstudyEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("https://groupstudy-ee-461l.appspot.com/_ah/api")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                groupsEndpointApi = builder.build();
            }

            try {
                List<Groups> groups = groupsEndpointApi.loadGroups().execute().getItems();
                Log.i("LoadGroupsAsync", "groups retrieved");

                //no groups have been added yet so objectify returns null
                //not allowed when setting a list adapter so an empty arraylist needs to be created
                if (groups == null)
                    groups = new ArrayList<>();

                return groups;
            } catch (IOException e) {
                Log.i("LoadGroupsAsync", "" + e.getMessage());
                return Collections.EMPTY_LIST;
            }
        }

        @Override
        protected void onPostExecute(List<Groups> result) {
            groups.addAll(result);
            adapter.notifyDataSetChanged();
            listener.onRetrieveGroupsCompleted(result);
        }
    }
}