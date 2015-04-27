package ee461l.groupstudy;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessagingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagingFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String GROUP_NAME = "groupName";
    private static final String USERNAME = "username";

    private EditText textMessage;
    private TextView displayMessage;
    private Button sendButton;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_messaging, container, false);
        getActivity().setTitle("Messaging");

        sendButton = (Button) rootView.findViewById(R.id.sendButton);
        textMessage = (EditText) rootView.findViewById(R.id.edit_message);
        displayMessage = (TextView) rootView.findViewById(R.id.display_message);

        sendButton.setOnClickListener(this);

        textMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                displayMessage.setText(textMessage.getText().toString());
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
        displayMessage.setText(textMessage.getText().toString());
    }
}
