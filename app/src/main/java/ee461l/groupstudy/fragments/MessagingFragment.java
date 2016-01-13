package ee461l.groupstudy.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import ee461l.groupstudy.R;
import ee461l.groupstudy.adapter.MessagingRVAdapter;
import ee461l.groupstudy.models.Group;
import ee461l.groupstudy.models.Message;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessagingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

//TODO: SET UP GOOGLE CLOUD MESSAGING
public class MessagingFragment extends BaseFragment implements View.OnClickListener,
        TextView.OnEditorActionListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String GROUP_NAME = "groupName";
    private static final String USERNAME = "username";
    private static final String TAG = "MessagingLoadGroup";

    private EditText messageEditor;
    private Group group;
    private MessagingRVAdapter adapter;
    private List<String> messagesForAdapter;
    private List<Message> messages;
    private CircularProgressView progressView;

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

            /*MainActivity main = new MainActivity();
            main.sendGroupName(groupName);*/
        }


        GetMessagesAsyncTask gmat = new GetMessagesAsyncTask();
        gmat.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_messaging, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.message_rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new MessagingRVAdapter(getContext(), username, messages);
        recyclerView.setAdapter(adapter);

        //getContext().setTitle("Messaging");
        progressView = (CircularProgressView) rootView.findViewById(R.id.progress_view);

        Button sendButton = (Button) rootView.findViewById(R.id.sendButton);
        messageEditor = (EditText) rootView.findViewById(R.id.edit_message);

        sendButton.setOnClickListener(this);

        //this is if the enter button is pressed to send the message
        messageEditor.setOnEditorActionListener(this);

        setupToolbar((Toolbar) rootView.findViewById(R.id.toolbar), "Messages");

        return rootView;
    }

    //this is for when the send button is clicked
    @Override
    public void onClick(View v) {
        String text = username + ": " + messageEditor.getText().toString();
        //save message to parse here
        Message message = new Message();
        message.put("author", ParseUser.getCurrentUser().getUsername());
        message.put("messageText", messageEditor.getText().toString());
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.d(TAG, "message saved");
            }
        });

        group.add("messages", message);
        group.saveInBackground();

        messagesForAdapter.add(text);
        adapter.notifyDataSetChanged();

        clearText();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (event.getAction() == KeyEvent.KEYCODE_ENTER) {
            String text = username + ": " + messageEditor.getText().toString();

            Message message = new Message();
            message.put("author", ParseUser.getCurrentUser().getUsername());
            message.put("messageText", messageEditor.getText().toString());
            message.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Log.d(TAG, "message saved");
                }
            });

            group.add("messages", message);
            group.saveInBackground();

            messagesForAdapter.add(text);
            adapter.notifyDataSetChanged();

            clearText();
        }
        return false;
    }

    private void clearText() {
        messageEditor.setText("");
    }

    public class GetMessagesAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            progressView.startAnimation();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParseQuery<Group> query = ParseQuery.getQuery("Group");
            query.whereEqualTo("name", groupName);
            query.include("messages");
            try {
                group = query.getFirst();
                //Log.d(TAG, "group: " + group.getGroupName() + " " + group.getAdminUser());
                messages = group.getMessages();
                //Log.d(TAG, "message: " + messages.get(0).getMessageText());
            }
            catch (ParseException e) {
                Log.d(TAG, "error: " + e.getMessage());
            }
            /*query.getFirstInBackground(new GetCallback<Group>() {
                @Override
                public void done(Group g, ParseException e) {
                    if (e == null) {
                        group = g;
                        Log.d(TAG, "group: " + group.getGroupName() + " " + group.getAdminUser());
                        //updateView();
                        messages = group.getMessages();
                    } else {
                        Log.d(TAG, "error: " + e.getMessage());
                    }
                }
            });*/
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(TAG, "after query");
            messagesForAdapter = new ArrayList<>();

            if (messages != null && messages.size() > 0) {
                for (Message message : messages) {
                    //Log.d(TAG, "message: " + message.getMessageText());
                    messagesForAdapter.add(message.getAuthor() + ": " + message.getMessageText());
                }
            }

            adapter.notifyDataSetChanged();
        }
    }
}
