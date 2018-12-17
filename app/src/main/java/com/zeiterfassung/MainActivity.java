package com.zeiterfassung;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
                                                               TimePickerDialog.OnTimeSetListener
{
    static final DateFormat dateFormat = new SimpleDateFormat("EE, dd.MM.yyyy", Locale.GERMANY);
    static final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.GERMANY);
    boolean doubleBackToExitPressedOnce = false;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int resource = MainFragment.getResource(getArguments().getInt(ARG_SECTION_NUMBER));
            return inflater.inflate(resource, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState){
            TextView textView = getActivity().findViewById(R.id.inpDate);
            textView.setText(dateFormat.format(new Date()));

            TextView inpDate = getActivity().findViewById(R.id.inpDate);
            inpDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerFragment fragment = new DatePickerFragment();
                    fragment.show(getActivity().getSupportFragmentManager(), "date");
                }
            });

            // TODO: Differentiate caller

            TextView inpFromTime = getActivity().findViewById(R.id.inpFromTime);
            inpFromTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerFragment fragment = new TimePickerFragment();
                    fragment.show(getActivity().getSupportFragmentManager(), "time");
                }
            });

            TextView inpToTime = getActivity().findViewById(R.id.inpToTime);
            inpToTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TimePickerFragment fragment = new TimePickerFragment();
                    fragment.show(getActivity().getSupportFragmentManager(), "time");
                }
            });

            // Get reference of widgets from XML layout
            Spinner spinner = (Spinner) getActivity().findViewById(R.id.spnProject);

            // Initializing a String Array
            String[] projects = new String[]{
                    "INTORQ",
                    "Ford",
                    "Mennekes",
                    "MFS-Standard"
            };

            // Initializing an ArrayAdapter
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    getActivity(), R.layout.spinner_item, projects
            );
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
            spinner.setAdapter(spinnerArrayAdapter);

            getActivity().findViewById(R.id.btnAddProject).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // TODO: PopUp with Input for new Project Name
                }
            });
        }
    }

    /***********************************************************************************************
     * DatePicker
     **********************************************************************************************/

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = new GregorianCalendar(year, month, day);
        Date date = calendar.getTime();
        setDate(date);
    }

    private void setDate(final Date date) {
        ((TextView) findViewById(R.id.inpDate)).setText(dateFormat.format(date));
    }

    /***********************************************************************************************
     * TimePicker
     **********************************************************************************************/

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        setTime(view, calendar);
    }

    private void setTime(final View view, final Calendar calendar) {
        TextView textView = findViewById(R.id.inpFromTime);

        switch(view.getId())
        {
            case R.id.inpToTime:
                textView = findViewById(R.id.inpToTime);
                break;
            default:
                textView = findViewById(R.id.inpFromTime);
                break;
        }

        textView.setText(timeFormat.format(calendar.getTime()));
    }

    /***********************************************************************************************
     * Back-Button
     **********************************************************************************************/

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.how_to_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
