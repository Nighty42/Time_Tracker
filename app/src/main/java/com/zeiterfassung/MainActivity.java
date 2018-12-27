package com.zeiterfassung;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zeiterfassung.fragment.DatePickerFragment;
import com.zeiterfassung.fragment.SectionsPagerAdapter;
import com.zeiterfassung.fragment.TimePickerFragment;
import com.zeiterfassung.manager.DimAmountManager;
import com.zeiterfassung.model.Project;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final DateFormat dateFormat = new SimpleDateFormat("EE, dd. MMMM yyyy", Locale.GERMANY);
    public static final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.GERMANY);

    private PopupWindow popupWindow = null;
    private EditText inpProject;

    private boolean doubleBackToExitPressedOnce = false;

    /*
     * TODO:
     * - Possibility to delete projects (maybe at long press on Item in Spinner)
     * -
     */

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

    public void onClickBtnAddProject(View v) {
        addProject();
    }

    public void onClickBtnAddProjectPopUp(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.popup_add_project, null);

        inpProject = popupView.findViewById(R.id.inpProject);
        inpProject.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEND:
                    case EditorInfo.IME_ACTION_DONE:
                        addProject();
                        handled = true;
                        break;
                }

                return handled;
            }
        });

        popupWindow = new PopupWindow(popupView,
                ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT, true);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        popupWindow.setWidth(size.x - 50);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, -30);

        DimAmountManager.dim(popupWindow);
    }

    private void addProject() {
        String item = inpProject.getText().toString();
        String errorCode = Project.addItem(item);

        if (errorCode.equals(ErrorCode.OK)) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.spinner_item, Project.getList());
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

            Spinner spinner = findViewById(R.id.spnProject);
            spinner.setAdapter(adapter);
            spinner.setSelection(Project.getIndexOfItem(item));
            spinner.setClickable(true);

            popupWindow.dismiss();
        } else {
            inpProject.setText("");
            inpProject.setHint(getResources().getText(getResources().getIdentifier(errorCode,
                    "string", "com.zeiterfassung")));
        }
    }

    public void onClickBtnDelProject(View v) {
        delProject();
    }

    private void delProject() {
        Spinner spinner = findViewById(R.id.spnProject);

        Project.delItem((String) spinner.getSelectedItem());

        if (Project.getList().size() > 0) {
            spinner.setClickable(true);
        } else {
            Project.addItem(Project.getDummyItem());
            spinner.setClickable(false);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(App.getContext(),
                R.layout.spinner_item, Project.getList());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public void onClickBtnAddTermin(View v) {
        // TODO: Implement Logic
    }

    public void onClickBtnResetTermin(View v) {
        resetAddFragment();
    }

    public void resetAddFragment() {
        TextView textView = findViewById(R.id.inpDate);
        textView.setText(MainActivity.dateFormat.format(new Date()));

        textView = findViewById(R.id.inpFromTime);
        textView.setText(getResources().getText(R.string.defaultFromTime));

        textView = findViewById(R.id.inpToTime);
        textView.setText(getResources().getText(R.string.defaultToTime));

        textView = findViewById(R.id.inpDescription);
        textView.setText("");
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

    /***********************************************************************************************
     * EXIT
     **********************************************************************************************/

    @Override
    protected void onDestroy() {
        // FileManager.write(this);

        super.onDestroy();
    }
}
