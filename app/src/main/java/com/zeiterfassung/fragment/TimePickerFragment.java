package com.zeiterfassung.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zeiterfassung.R;
import com.zeiterfassung.manager.DateTimeManager;

import java.util.Calendar;
import java.util.Objects;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private int mId;

    public static TimePickerFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("picker_id", id);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour = DatePickerFragment.calendar.get(Calendar.HOUR_OF_DAY);
        int minute = DatePickerFragment.calendar.get(Calendar.MINUTE);

        try {
            if (getArguments() != null) {
                mId = getArguments().getInt("picker_id");
            }
        } catch (Exception e) {
            e.printStackTrace();

            mId = R.id.inpFromTime;
        }

        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        setTime(view, calendar);
    }

    private void setTime(final View view, final Calendar calendar) {
        TextView textView;

        try {
            switch (mId) {
                case R.id.inpToTime:
                    textView = Objects.requireNonNull(getActivity()).findViewById(R.id.inpToTime);
                    break;
                default:
                    textView = Objects.requireNonNull(getActivity()).findViewById(R.id.inpFromTime);
                    break;
            }

            textView.setText(DateTimeManager.timeToString(calendar.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
