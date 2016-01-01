package ee461l.groupstudy.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;

import ee461l.groupstudy.R;
import ee461l.groupstudy.models.Group;


//store calendar items to parse
//load items from parse and display on calendar
public class CalendarFragment extends BaseFragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String GROUP_NAME = "groupName";
    private static final String TAG = "CalendarFragment";

    private TextView events;
    private TextView eventDate;
    private TextView eventTime;
    private ArrayList<String> dateList = new ArrayList<>();
    private ArrayList<String> timeList = new ArrayList<>();
    private ArrayList<String> descriptionList = new ArrayList<>();
    private Group group;
    private String groupName;
    private AlertDialog descriptionDialog = null;
    private AlertDialog locationDialog = null;
    private TimePickerDialog timeDialog = null;
    private DatePickerDialog dateDialog = null;
    boolean dateCancel = false;
    boolean timeAdded = false;

    private LayoutInflater mInflater;
    private ViewGroup mContainer;


    // if we want the default location to be PCL:            ADDED BY DANIEL
    private String locationString = "PCL - Perry-Castañeda Library";

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

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
        query.whereEqualTo("name", groupName);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    group = (Group) parseObject;
                    //updateView();
                } else {
                    Log.d(TAG, e.getMessage());
                }
            }
        });
    }

    /*private void updateView() {
        mInflater.inflate(R.layout.fragment_calendar, mContainer, false);

        Log.d(TAG, "updating view");

        List<String> tasks = group.getTasks();

        // tasks have been created so they can be loaded
        // otherwise no tasks will show initially
        if (tasks != null) {
            Collections.sort(tasks);
            Collections.sort(tasks, new Comparator<String>(){
                public int compare(String a, String b){
                    String[] as = a.split("/");
                    String[] bs = b.split("/");
                    int result = Integer.valueOf(as[0]).compareTo(Integer.valueOf(bs[0]));
                    if(result==0)
                        result = Integer.valueOf(as[1]).compareTo(Integer.valueOf(bs[1]));
                    return result;
                }
            });

            for (int i = 0; i < tasks.size(); i++) {
                events.append(tasks.get(i) + "\n");
            }

        }
        Log.d(TAG, "view updated!");
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mInflater = inflater;
        mContainer = container;

        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        Button newEvent = (Button)rootView.findViewById(R.id.newEvent);
        newEvent.setOnClickListener(this);
        events = (TextView) rootView.findViewById(R.id.tasks);
        eventDate = (TextView) rootView.findViewById(R.id.eventDate);
        eventDate.setVisibility(View.GONE);
        eventTime = (TextView) rootView.findViewById(R.id.eventTime);
        eventTime.setVisibility(View.GONE);

        setupToolbar((Toolbar) rootView.findViewById(R.id.toolbar), "Calendar");

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


        dateDialog = new DatePickerDialog(getContext(),
                new mDateSetListener(), mYear, mMonth, mDay);
        dateDialog.setOnCancelListener(new mDateCancelListener());

        timeDialog = new TimePickerDialog(getContext(),
                new mTimeSetListener(), hour, minute, false);
        timeDialog.setOnDismissListener(new mTimeDismissListener());

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder locationBuilder = new AlertDialog.Builder(getContext());       // ADDED BY
        // DANIEL
        locationBuilder.setTitle("Pick a location:");

        String[] types = {"ECJ - Ernest Cockrell Jr. Hall",
                "ETC - Engineering Teaching Center II",
                "FAC - Peter T. Flawn Academic Center",
                "PCL - Perry-Castañeda Library",
                "UNB - Union Building"};
        setLocationItems(locationBuilder, types);

        AlertDialog.Builder descriptionDialogBuilder = new AlertDialog.Builder(
                getContext());

        // set prompts.xml to alertdialog builder
        descriptionDialogBuilder.setView(promptsView);


        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        setDialogMessage(descriptionDialogBuilder, userInput);

        // create alert dialog
        descriptionDialog = descriptionDialogBuilder.create();
        locationDialog = locationBuilder.create();

        //initialize each time
        dateCancel = false;
        timeAdded = false;


        // show all of them
        descriptionDialog.show(); //description
        locationDialog.show(); //location  // ADDED BY DANIEL
        timeDialog.show(); //time
        dateDialog.show();  //date

        ParseObject calendarItem = new ParseObject("Task");
        calendarItem.put("description", "stuff");
        calendarItem.put("date", "stuff");
        calendarItem.put("location", "stuff");
        calendarItem.saveInBackground();

        group.add("tasks", calendarItem);
        group.saveInBackground();

    }

    private void setDialogMessage(AlertDialog.Builder descriptionDialogBuilder, final EditText userInput) {
        descriptionDialogBuilder
            .setCancelable(true)
            .setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        // get user input and set it to result
                        descriptionList.add(userInput.getText().toString());
                        int index = descriptionList.indexOf(userInput.getText().toString());
                        int size = descriptionList.size();

                        events.append("\n" + dateList.get(index));
                        events.append(" " + timeList.get(index) + ": ");
                        events.append(locationString + "\n" + descriptionList.get(index));

                        String localEvent = dateList.get(index) + " " + timeList.get(index) + ": "
                                + locationString + "\n" + descriptionList.get(index) + "\n";

                        //TODO: SAVE TASK TO PARSE


                        Toast.makeText(getContext(), "Event added!", Toast.LENGTH_SHORT).show();
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
    }

    private void setLocationItems(AlertDialog.Builder locationBuilder, String[] types) {
        locationBuilder.setItems(types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0:
                        //System.out.println("ECJ - Ernest Cockrell Jr. Hall");
                        locationString = "ECJ - Ernest Cockrell Jr. Hall"; break;
                    case 1:
                        //System.out.println("ETC - Engineering Teaching Center II");
                        locationString = "ETC - Engineering Teaching Center II";break;
                    case 2:
                        //System.out.println("FAC - Peter T. Flawn Academic Center");
                        locationString = "FAC - Peter T. Flawn Academic Center";break;
                    case 3:
                        //System.out.println("PCL - Perry-Castañeda Library");
                        locationString = "PCL - Perry-Castañeda Library";break;
                    case 4:
                        //System.out.println("UNB - Union Building");
                        locationString = "UNB - Union Building";break;
                }
            }
        });
    }


    //select the date of the event
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

    //if the user cancels the date dialog, this is called
    class mDateCancelListener implements DatePickerDialog.OnCancelListener{
        @Override
        public void onCancel(DialogInterface dialog) {
            dateCancel = true;
            timeDialog.dismiss();

        }
    }

    //if the user cancels the time dialog, this is called
    //will cancel time dialog and all dialogs after
    //removes newest date from list since it was already added by a previous dialog
    class mTimeDismissListener implements DialogInterface.OnDismissListener {
        @Override
        public void onDismiss(DialogInterface dialog) {

            if(dateCancel){
                timeAdded = false;
                descriptionDialog.cancel();
                locationDialog.cancel();
            }
            if(dateList.size() > timeList.size()){ //TIME CANCELED, DATE NOT CANCELED
                timeAdded = false;
                dateList.remove(dateList.size() - 1);
                descriptionDialog.cancel();
                locationDialog.cancel();
            }
        }
    }

    //dialog for user to set time of event
    class mTimeSetListener implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            int mHour = hourOfDay;
            int aMinute = minute;
            String mMinute = null;
            boolean AM = false;

            if ((mHour < 12)) {
                AM = true;
                if(mHour == 0){
                    mHour = 12;
                }
            }
            else if (mHour == 12) {
                //do nothing, 12PM
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
