package ee461l.groupstudy.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import ee461l.groupstudy.R;
import ee461l.groupstudy.adapter.TaskRVAdapter;
import ee461l.groupstudy.models.Group;
import ee461l.groupstudy.models.Task;

//TODO: use full-screen dialog instead of multiple dialogs
//store calendar items to parse
//load items from parse and display on calendar
public class TaskFragment extends BaseFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String GROUP_NAME = "groupName";
    public static final String DESCRIPTION = "description";
    public static final String LOCATION = "location";
    public static final String DATE = "date";
    public static final String TIME = "time";
    private static final String TAG = "TaskFragment";

    private RecyclerView.Adapter adapter;
    private List<Task> tasks;
    private Group group;
    private String groupName;

    private String description;
    private String location;
    private String date;
    private String time;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalendarFragment.
     */
    public static TaskFragment newInstance(String groupName, String desc, String loc, String
            date, String time) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString(GROUP_NAME, groupName);

        if (desc != null && loc != null && date != null && time != null) {
            args.putString(DESCRIPTION, desc);
            args.putString(LOCATION, loc);
            args.putString(DATE, date);
            args.putString(TIME, time);

        }

        fragment.setArguments(args);
        return fragment;
    }

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupName = getArguments().getString(GROUP_NAME);
            description = getArguments().getString(DESCRIPTION);
            location = getArguments().getString(LOCATION);
            date = getArguments().getString(DATE);
            time = getArguments().getString(TIME);
        }

        setHasOptionsMenu(true);

        tasks = new ArrayList<>();

        ParseQuery<Group> query = ParseQuery.getQuery("Group");
        query.whereEqualTo("name", groupName);
        query.include("tasks");
        query.getFirstInBackground(new GetCallback<Group>() {
            @Override
            public void done(Group g, ParseException e) {
                if (e == null) {
                    group = g;
                    tasks.clear();
                    tasks.addAll(group.getTasks());

                    if (tasks != null && tasks.size() > 0) {
                        Log.d(TAG, "first task: " + tasks.get(0).getDescription());
                    }
                    adapter.notifyDataSetChanged();

                    // this means that a task was sent from TaskDialogFragment and must be saved
                    // to Parse
                    if (description != null && location != null && date != null && time != null) {
                        saveTaskFromDialog();
                    }
                } else {
                    Log.d(TAG, "error: " + e.getMessage());
                }
            }
        });
    }

    private void saveTaskFromDialog() {
        final Task task = new Task();
        task.put("description", description);
        task.put("date", date);
        task.put("time", time);
        task.put("location", location);
        task.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.d(TAG, "task saved");
                List<Task> t = new ArrayList<>();
                t.addAll(tasks);
                t.add(task);

                tasks.clear();
                tasks.addAll(t);
                adapter.notifyDataSetChanged();
            }
        });

        group.add("tasks", task);
        group.saveInBackground();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.task_rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new TaskRVAdapter(getContext(), tasks);
        recyclerView.setAdapter(adapter);

        setupToolbar((Toolbar) rootView.findViewById(R.id.toolbar), "Tasks");

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_task:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.content_fragment, TaskDialogFragment.newInstance
                        (groupName)).commit();
        }
        return super.onOptionsItemSelected(item);
    }
}
