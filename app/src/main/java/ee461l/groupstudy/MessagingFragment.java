package ee461l.groupstudy;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ee461l.groupstudyendpoints.groupstudyEndpoint.GroupstudyEndpoint;
import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessagingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagingFragment extends Fragment implements View.OnClickListener,
        TextView.OnEditorActionListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String GROUP_NAME = "groupName";
    private static final String USERNAME = "username";
    private static GroupstudyEndpoint groupEndpointApi = null;
    private static final String TAG = "MessagingLoadGroup";

    private EditText messageEditor;
    private ListView messages;
    private List<String> listOfMessages;
    private Button sendButton;
    private Groups group;
    private MessagingListViewAdapter adapter;
    private List<String> messagesForAdapter;

    private String groupName;
    private String username;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment MessagingFragment.
     */
    public static Fragment newInstance(String groupName, String username) {
        Fragment fragment = new MessagingFragment();
        Bundle args = new Bundle();
        args.putString(GROUP_NAME, groupName);
        args.putString(USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    public MessagingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupName = getArguments().getString(GROUP_NAME);
            username = getArguments().getString(USERNAME);
        }

        LoadSingleGroupAsyncTask lsgat = new LoadSingleGroupAsyncTask(getActivity(),
                new OnRetrieveSingleGroupTaskCompleted() {
            @Override
            public void onRetrieveSingleGroupCompleted(Groups g) {
                group = g;
            }
        });

        Toast.makeText(getActivity(), "Loading messages...", Toast.LENGTH_SHORT).show();
        //defeats purpose of async task
        //but the group is loading too slowly so messages are null in onCreateView and it crashes
        try {
            group = lsgat.execute(groupName).get();
        }
        catch(InterruptedException e) {
            Log.e(TAG, "InterruptedException: " + e.getMessage());
        }
        catch(ExecutionException e) {
            Log.e(TAG, "ExecutionException: " + e.getMessage());
        }

        listOfMessages = group.getMessages();

        //no messages yet so nothing to show
        if (listOfMessages == null)
            messagesForAdapter = new ArrayList<>();
        else
            messagesForAdapter = new ArrayList<>(listOfMessages);

        adapter = new MessagingListViewAdapter(getActivity(), username, messagesForAdapter,
                R.layout.fragment_messaging);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_messaging, container, false);
        getActivity().setTitle("Messaging");

        sendButton = (Button) rootView.findViewById(R.id.sendButton);
        messageEditor = (EditText) rootView.findViewById(R.id.edit_message);
        messages = (ListView) rootView.findViewById(R.id.message_list_view);

        messages.setAdapter(adapter);
        sendButton.setOnClickListener(this);

        //this is if the enter button is pressed to send the message
        messageEditor.setOnEditorActionListener(this);



        // messages have been created so they can be loaded
        //otherwise no messages will show initially
/*        if (messages != null) {
            for (int i = 0; i < messages.size(); i++) {
                displayMessage.append(messages.get(i) + "\n");
            }
        }*/

        return rootView;
    }

    //this is for when the send button is clicked
    @Override
    public void onClick(View v) {
        String text = username + ": " + messageEditor.getText().toString();
        CreateMessageAsyncTask cmat = new CreateMessageAsyncTask(getActivity(), groupName);
        cmat.execute(text);

        messagesForAdapter.add(text);
        adapter.notifyDataSetChanged();
        //displayMessage.setText(username + ": " + messageEditor.getText().toString());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        String text = username + ": " + messageEditor.getText().toString();
        CreateMessageAsyncTask cmat = new CreateMessageAsyncTask(getActivity(), groupName);
        cmat.execute(text);

        messagesForAdapter.add(text);
        adapter.notifyDataSetChanged();

        //displayMessage.setText(messageEditor.getText().toString());
        return false;
    }
}
