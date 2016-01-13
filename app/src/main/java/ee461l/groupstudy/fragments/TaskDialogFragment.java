package ee461l.groupstudy.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ee461l.groupstudy.R;

public class TaskDialogFragment extends BaseFragment {

    private EditText descriptionEditText;
    private EditText locationEditText;
    private String date;
    private String time;
    private TextView timeView;
    private TextView dateView;


    private OnSendTaskListener mListener;

    public TaskDialogFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TaskDialogFragment newInstance(String groupName) {
        TaskDialogFragment fragment = new TaskDialogFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.task_dialog, container, false);
        setupToolbar((Toolbar) view.findViewById(R.id.toolbar), "New Task");

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        assert actionBar != null;   //maybe change this line?
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_cancel);

        Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);

        String date = setDate();

        dateView = (TextView) view.findViewById(R.id.task_date);
        dateView.setText(date);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDialog = new DatePickerDialog(getContext(),
                        new mDateSetListener(), mYear, mMonth, mDay);
                dateDialog.setOnCancelListener(new mDateCancelListener());
                dateDialog.show();
            }
        });

        timeView = (TextView) view.findViewById(R.id.task_time);
        String time = "12:00 AM";
        timeView.setText(time);
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timeDialog = new TimePickerDialog(getContext(),
                        new mTimeSetListener(), hour, minute, false);
                timeDialog.setOnCancelListener(new mTimeCancelListener());
                timeDialog.show();
            }
        });

        descriptionEditText = (EditText) view.findViewById(R.id.task_description);
        locationEditText = (EditText) view.findViewById(R.id.task_location);
        return view;
    }

    private String setDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("EEE, MMM d yyyy", Locale.US);
        return format1.format(cal.getTime());
    }

    // changes date in date textview
    private void setDate(String date) {
        SimpleDateFormat from = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        SimpleDateFormat to = new SimpleDateFormat("EEE, MMM d yyy", Locale.US);

        try {
            String correctDate = to.format(from.parse(date));
            dateView.setText(correctDate);
        }
        catch (ParseException e) {
            Log.d("TaskDialog", e.getMessage());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task_dialog, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Discard new task?");
                builder.setPositiveButton("ERASE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager().popBackStack();    //go back to TaskFragment
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case R.id.create_task:
                if (descriptionEditText.getText().toString().equals("") || locationEditText.getText
                        ().toString().equals("") || date == null || time == null) {

                    Log.d("TaskDialog", "desc: " + descriptionEditText.getText().toString() + ", " +
                            "loc: " + locationEditText.getText().toString() + ", date: " + date +
                            ", time: " + time);

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                    alertBuilder.setMessage("Please fill in all fields to create a task.");
                    alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();
                } else {

                    //close keyboard on save
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    mListener.sendTaskToTaskFrag(descriptionEditText.getText().toString(), locationEditText
                            .getText().toString(), date, time);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //select the date of the event
    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            date = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
            setDate(date);
        }
    }

    //if the user cancels the date dialog, this is called
    class mDateCancelListener implements DatePickerDialog.OnCancelListener{
        @Override
        public void onCancel(DialogInterface dialog) {
            // nothing is saved
            date = null;
        }
    }

    //if the user cancels the time dialog, this is called
    //will cancel time dialog and all dialogs after
    //removes newest date from list since it was already added by a previous dialog
    class mTimeCancelListener implements TimePickerDialog.OnCancelListener {
        @Override
        public void onCancel(DialogInterface dialog) {
            //nothing is saved if cancel is pressed
            time = null;
        }
    }

    //dialog for user to set time of event
    class mTimeSetListener implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            int mHour = hourOfDay;
            String mMinute;
            boolean AM = false;

            if (mHour < 12) {
                AM = true;
                if (mHour == 0) {
                    mHour = 12;
                }
            }
            else if (mHour > 12) {
                mHour -= 12;
            }

            if (minute < 10) {
                mMinute = "0" + minute;
            } else {
                mMinute = String.valueOf(minute);
            }

            StringBuilder timeSb = new StringBuilder();
            timeSb.append(mHour);
            timeSb.append(":");
            timeSb.append(mMinute);

            if (AM) {
                timeSb.append(" AM");
            } else {
                timeSb.append(" PM");
            }

            time = timeSb.toString();
            timeView.setText(time);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSendTaskListener) {
            mListener = (OnSendTaskListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSendTaskListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSendTaskListener {
        void sendTaskToTaskFrag(String desc, String loc, String date, String time);
    }
}
