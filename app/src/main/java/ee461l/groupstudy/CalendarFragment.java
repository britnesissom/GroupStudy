package ee461l.groupstudy;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import java.util.ArrayList;
import java.util.Calendar;


public class CalendarFragment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_MENU_CHOICE_NUM = "menu_number";
    UserList userList;
    static TextView users; //**maybe shouldnt be static
    TextView eventDate;
    TextView eventTime;
    ArrayList dateList = new ArrayList<>();
    ArrayList timeList = new ArrayList();
    ArrayList descriptionList = new ArrayList();
    FragmentManager manager;


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

        /*dateList.add("04/20/2015 5pm: Meeting 1 at ENS");
        dateList.add("04/21/2015 3pm: Meeting 2 at PCL");
        dateList.add("04/23/2015 12:30pm: Meeting 3 at ECJ");*/

        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        Button newEvent = (Button)rootView.findViewById(R.id.newEvent);
        newEvent.setOnClickListener(this);
        users = (TextView) rootView.findViewById(R.id.users);
        eventDate = (TextView) rootView.findViewById(R.id.eventDate);
        eventDate.setVisibility(View.GONE);
        eventTime = (TextView) rootView.findViewById(R.id.eventTime);
        eventTime.setVisibility(View.GONE);
        //users.setText("Hello everyone");

        getActivity().setTitle("Calendar");

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        //users.append("the selected " + mDay);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                new mDateSetListener(), mYear, mMonth, mDay);

        //DialogFragment newFragment = new TimePickerFragment();
        //newFragment.show(this.manager, "timePicker");
        TimePickerDialog timeDialog = new TimePickerDialog(getActivity(),
                new mTimeSetListener(), hour, minute, false);
        //Dialog description = new Dialog(getActivity());
        //description.show();

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                descriptionList.add(userInput.getText().toString());
                                int index = descriptionList.indexOf(userInput.getText().toString());
                                if (index == 0) {
                                    users.setText(dateList.get(index).toString() + " "
                                            + timeList.get(index).toString() + ": " +
                                            descriptionList.get(index).toString());
                                } else {
                                    users.append("\n" + dateList.get(index).toString());
                                    users.append(" " + timeList.get(index).toString() + ": ");
                                    users.append(descriptionList.get(index).toString());
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dateList.remove(dateList.size() - 1);
                                timeList.remove(timeList.size() - 1);
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show all of them
        alertDialog.show();
        timeDialog.show();
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

            eventDate.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" "));
            dateList.add(eventDate.getText().toString());


        }
    }

    class mTimeSetListener implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            int mHour = hourOfDay;
            int mMinute = minute;
            boolean AM = false;

            if (mHour < 12){
                AM = true;
            } else {
                mHour -= 12;
            }
            if(AM) {
                eventTime.setText(new StringBuilder()
                        .append(mHour).append(":").append(mMinute).append("am"));
            } else {
                eventTime.setText(new StringBuilder()
                        .append(mHour).append(":").append(mMinute).append("pm"));
            }
            timeList.add(eventTime.getText().toString());

        }


    }


}
