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
    private Groups group;
    private String groupName;
    private String lastDate = null;
    private String lastTime = null;
    private String lastDescription = null;
    private AlertDialog descriptionDialog = null;
    private TimePickerDialog timeDialog = null;
    private DatePickerDialog dateDialog = null;
    boolean dateCancel = false;
    boolean timeAdded = false;


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


        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        Button newEvent = (Button)rootView.findViewById(R.id.newEvent);
        newEvent.setOnClickListener(this);
        events = (TextView) rootView.findViewById(R.id.tasks);
        eventDate = (TextView) rootView.findViewById(R.id.eventDate);
        eventDate.setVisibility(View.GONE);
        eventTime = (TextView) rootView.findViewById(R.id.eventTime);
        eventTime.setVisibility(View.GONE);


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


        dateDialog = new DatePickerDialog(getActivity(),
                new mDateSetListener(), mYear, mMonth, mDay);
        dateDialog.setOnCancelListener(new mDateCancelListener());

        timeDialog = new TimePickerDialog(getActivity(),
                new mTimeSetListener(), hour, minute, false);
        timeDialog.setOnDismissListener(new mTimeDismissListener());

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
            .setCancelable(true)
            .setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


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

                    })
            .setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                               if (timeList.size() > descriptionList.size()) { //THEY WERE BOTH NOT CANCELED
                                   dateList.remove(dateList.size() - 1);
                                   timeList.remove(timeList.size() - 1);
                               }
                                dialog.cancel();
                            }
                    });

        // create alert dialog
        descriptionDialog = alertDialogBuilder.create();

        //initialize each time
        dateCancel = false;
        timeAdded = false;

        System.out.println("date, time, description" + dateList.size() + timeList.size() + descriptionList.size());
        // show all of them
        descriptionDialog.show(); //description
                            //location
        timeDialog.show(); //time
        dateDialog.show();  //date

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
            dateCancel = false;

        }
    }
    class mDateCancelListener implements DatePickerDialog.OnCancelListener{
        @Override
        public void onCancel(DialogInterface dialog) {
            dateCancel = true;
            timeDialog.dismiss();
            //descriptionDialog.cancel();
            System.out.println("date canceled");
        }
    }

    class mTimeDismissListener implements DialogInterface.OnDismissListener{
        @Override
        public void onDismiss(DialogInterface dialog) {

            if(dateCancel){
                timeAdded = false;
                descriptionDialog.cancel();
            }
            if(dateList.size() > timeList.size()){ //TIME CANCELED, DATE NOT CANCELED
                timeAdded = false;
                dateList.remove(dateList.size() - 1);
                descriptionDialog.cancel();
            }
            System.out.println("time dismissed");

        }
    }

    class mTimeSetListener implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            int mHour = hourOfDay;
            int aMinute = minute;
            String mMinute = null;
            boolean AM = false;

            if ((mHour < 12)) {
                AM = true;
            }
            else if ((mHour == 12) || (mHour == 0)) {
                mHour = 12;
            } else {
                mHour -= 12;
            }

            if(aMinute < 10){
               mMinute = "0" + aMinute;
            } else {
               mMinute = new Integer(aMinute).toString();
            }
            if (AM) {
                eventTime.setText(new StringBuilder()
                        .append(mHour).append(":").append(mMinute).append("am"));
            } else {
                eventTime.setText(new StringBuilder()
                        .append(mHour).append(":").append(mMinute).append("pm"));
            }
            timeList.add(eventTime.getText().toString());
            timeAdded = true;
        }


    }





}
