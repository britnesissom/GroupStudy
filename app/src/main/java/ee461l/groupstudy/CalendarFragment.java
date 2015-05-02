package ee461l.groupstudy;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ee461l.groupstudyendpoints.groupstudyEndpoint.model.Groups;


public class CalendarFragment extends Fragment implements View.OnClickListener, OnRetrieveSingleGroupTaskCompleted {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String GROUP_NAME = "groupName";
    private static final String TAG = "CalendarFragment";

    //static TextView users; //**maybe shouldnt be static
    private TextView events;
    private TextView eventDate;
    private TextView eventTime;
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<String> timeList = new ArrayList<>();
    private ArrayList<String> descriptionList = new ArrayList<>();
    FragmentManager manager;
    private boolean dateCancel = false;
    private Groups group;

    private String groupName;
    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalendarFragment.
     */
    public static Fragment newInstance(String groupName) {
        Fragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(GROUP_NAME, groupName);
        fragment.setArguments(args);
        return fragment;
    }

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupName = getArguments().getString(GROUP_NAME);
        }

        LoadSingleGroupAsyncTask lsgat = new LoadSingleGroupAsyncTask(getActivity(),
                new OnRetrieveSingleGroupTaskCompleted() {
            @Override
            public void onRetrieveSingleGroupCompleted(Groups g) {
                group = g;
            }
        });

        //defeats purpose of async task
        try {
            group = lsgat.execute(groupName).get();
        }
        catch(InterruptedException e) {
            Log.e(TAG, "InterruptedException: " + e.getMessage());
        }
        catch(ExecutionException e) {
            Log.e(TAG, "ExecutionException: " + e.getMessage());
        }
    }

    public void onRetrieveSingleGroupCompleted(Groups g) {
        group = g;
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
        events = (TextView) rootView.findViewById(R.id.tasks);
        eventDate = (TextView) rootView.findViewById(R.id.eventDate);
        eventDate.setVisibility(View.GONE);
        eventTime = (TextView) rootView.findViewById(R.id.eventTime);
        eventTime.setVisibility(View.GONE);
        //users.setText("Hello everyone");

        getActivity().setTitle("Calendar");

        List<String> tasks = group.getTasks();

        // tasks have been created so they can be loaded
        //otherwise no tasks will show initially
        if (tasks != null) {
            Collections.sort(tasks);
            for (int i = 0; i < tasks.size(); i++) {
                events.append(tasks.get(i) + "\n");
            }
        }

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
                        if (dateCancel) {
                            // already removed date and time
                        } else if (dateList.size() > timeList.size()) {
                            //date not canceled but time was
                            dateList.remove(dateList.size() - 1);
                        } else {
                            // get user input and set it to result
                            descriptionList.add(userInput.getText().toString());
                            int index = descriptionList.indexOf(userInput.getText().toString());
                            if (index == 0) {
                                events.setText(dateList.get(index) + " "
                                        + timeList.get(index) + ": " +
                                        descriptionList.get(index));
                            } else {
                                events.append("\n" + dateList.get(index));
                                events.append(" " + timeList.get(index) + ": ");
                                events.append(descriptionList.get(index));
                            }

                            String localEvent = dateList.get(index) + " " + timeList.get(index) + ": "
                                    + descriptionList.get(index);
                            CreateTaskAsyncTask ctat = new CreateTaskAsyncTask(getActivity(), groupName);
                            ctat.execute(localEvent);
                        }
                    }
                })
            .setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dateCancel) {
                            // already removed date and time
                        } else if (dateList.size() > timeList.size()) {
                            //date not canceled but time was
                            dateList.remove(dateList.size() - 1);
                        } else if (dateList.size() == timeList.size()) {
                            //date not canceled, time not canceled
                            dateList.remove(dateList.size() - 1);
                            timeList.remove(timeList.size() - 1);
                        }
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        //initialize each time
        dateCancel = false;

        // show all of them
        alertDialog.show(); //description
                            //location
        timeDialog.show(); //time
        dialog.show();  //date

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

            if(dateList.size() == timeList.size()){
                // a date wasn't added, it was canceled
                dateCancel = true;
            } else {
                if (mHour < 12) {
                    AM = true;
                }
                if (mHour == 12) {
                    //don't change it
                } else {
                    mHour -= 12;
                }
                if (AM) {
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


}
