package ee461l.groupstudy;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;


public class CalendarFragment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_MENU_CHOICE_NUM = "menu_number";
    TextView users;
    TextView event;

    // TODO: Rename and change types of parameters
    private String menuChoice;

    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance() {
        Fragment fragment = new CalendarFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_MENU_CHOICE_NUM, menuChoiceNum);
        fragment.setArguments(args);*/
        return fragment;
    }

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            menuChoice = getArguments().getString(ARG_MENU_CHOICE_NUM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home_page, container, false);
        ArrayList lists = new ArrayList<>();
        lists.add("04/20/15 5pm: Meeting 1 at ENS");
        lists.add("04/21/15 3pm: Meeting 2 at PCL");
        lists.add("04/23/15 12:30pm: Meeting 3 at ECJ");

        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        Button newEvent = (Button)rootView.findViewById(R.id.newEvent);
        newEvent.setOnClickListener(this);
        users = (TextView) rootView.findViewById(R.id.users);
        event = (TextView) rootView.findViewById(R.id.event);
        event.setVisibility(View.GONE);
        //users.setText("Hello everyone");
        users.setText(lists.get(0).toString());
        users.append("\n" + lists.get(1).toString() + "\n" + lists.get(2).toString());
        getActivity().setTitle("Calendar");

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        //users.append("the selected " + mDay);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                new mDateSetListener(), mYear, mMonth, mDay);
        dialog.show();
    }

    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;

            event.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" "));
            users.append("\n" + event.getText().toString());


        }
    }
}
