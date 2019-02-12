package com.zeiterfassung.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.zeiterfassung.R;
import com.zeiterfassung.manager.DateTimeManager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public static final Calendar calendar = Calendar.getInstance();

    public static DatePickerFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("picker_id", id);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = null;

        try {
            datePickerDialog = new DatePickerDialog((Objects.requireNonNull(getActivity())), this, year, month, day);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //noinspection ConstantConditions
        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = new GregorianCalendar(year, month, day);
        Date date = calendar.getTime();
        setDate(date);
    }

    private void setDate(final Date date) {
        try {
            ((TextView) Objects.requireNonNull(getActivity()).findViewById(R.id.inpDate)).setText(DateTimeManager.dateToString(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
