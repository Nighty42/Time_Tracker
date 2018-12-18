package com.zeiterfassung;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.zeiterfassung.com.zeiterfassung.fragment.DatePickerFragment;
import com.zeiterfassung.com.zeiterfassung.fragment.SectionsPagerAdapter;
import com.zeiterfassung.com.zeiterfassung.fragment.TimePickerFragment;
import com.zeiterfassung.com.zeiterfassung.manager.DimAmountManager;
import com.zeiterfassung.com.zeiterfassung.manager.FileManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final DateFormat dateFormat = new SimpleDateFormat("EE, dd. MMMM yyyy", Locale.GERMANY);
    public static final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.GERMANY);

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FileManager.read(this);
        FileManager.write(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onClickInpDate(View v) {
        DatePickerFragment fragment = DatePickerFragment.newInstance(findViewById(R.id.inpDate).getId());
        fragment.show(getSupportFragmentManager(), "date");
    }

    public void onClickInpFromTime(View v) {
        TimePickerFragment fragment = TimePickerFragment.newInstance(findViewById(R.id.inpFromTime).getId());
        fragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onClickInpToTime(View v) {
        TimePickerFragment fragment = TimePickerFragment.newInstance(findViewById(R.id.inpToTime).getId());
        fragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void onClickBtnAddProjectPopUp(View view) {
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.popup_add_project, null);

        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 10);

        DimAmountManager.dim(popupWindow);
    }

    public void onClickBtnAddProject(View v) {
        v.getId();
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
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
