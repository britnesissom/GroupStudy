package ee461l.groupstudy;


import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessagingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessagingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView users;
    private EditText textMessage;
    private TextView displayMessage;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment MessagingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance() {
        Fragment fragment = new MessagingFragment();
     /*   Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    public MessagingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_messaging, container, false);
        users = (TextView) rootView.findViewById(R.id.users);
        textMessage = (EditText) rootView.findViewById(R.id.edit_message);
        displayMessage = (TextView) rootView.findViewById(R.id.display_message);
        getActivity().setTitle("Messaging");

        textMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                displayMessage.setText(textMessage.getText().toString());
                return false;
            }
        });
        return rootView;
    }


}
