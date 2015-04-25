package ee461l.groupstudy;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeetingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeetingsFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String GROUP_NAME = "groupName";

    private String groupName;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MeetingsFragment.
     */
    public static Fragment newInstance(String groupName) {
        Fragment fragment = new MeetingsFragment();
        Bundle args = new Bundle();
        args.putString(GROUP_NAME, groupName);
        fragment.setArguments(args);
        return fragment;
    }

    public MeetingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupName = getArguments().getString(GROUP_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meetings, container, false);
    }


}
