package com.zeiterfassung.com.zeiterfassung.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.zeiterfassung.MainActivity;
import com.zeiterfassung.R;
import com.zeiterfassung.com.zeiterfassung.model.Project;

import java.util.Date;
import java.util.Objects;

public class PlaceholderFragment extends Fragment {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            int resource = 0;
            if (getArguments() != null) {
                resource = MainFragment.getResource(getArguments().getInt(ARG_SECTION_NUMBER));
            }
            return inflater.inflate(resource, container, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        TextView textView;

        try {
            textView = Objects.requireNonNull(getActivity()).findViewById(R.id.inpDate);
            textView.setText(MainActivity.dateFormat.format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Spinner spinner = getActivity().findViewById(R.id.spnProject);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                getActivity(), R.layout.spinner_item, Project.getList()
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }
}
