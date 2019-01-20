package com.zeiterfassung.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zeiterfassung.App;
import com.zeiterfassung.MainActivity;
import com.zeiterfassung.R;
import com.zeiterfassung.listview.CustomExpandableListAdapter;
import com.zeiterfassung.listview.ExpandableListDataPump;
import com.zeiterfassung.manager.FileManager;
import com.zeiterfassung.model.Appointment;
import com.zeiterfassung.model.Project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int count = 2;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<Appointment>> expandableListDetail;

    public MainFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public static int getCount() {
        return count;
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            View view = null;

            if (getArguments() != null) {
                switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                    case 1:
                        view = inflater.inflate(R.layout.fragment_main_add, container, false);
                        break;
                    case 2:
                        view = inflater.inflate(R.layout.fragment_main_show, null);

                        expandableListView = view.findViewById(R.id.expandableListView);
                        expandableListDetail = ExpandableListDataPump.getData();
                        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
                        expandableListAdapter = new CustomExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
                        expandableListView.setAdapter(expandableListAdapter);
                        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                            @Override
                            public void onGroupExpand(int groupPosition) {
                                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),
                                        expandableListTitle.get(groupPosition) + " List Expanded.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                            @Override
                            public void onGroupCollapse(int groupPosition) {
                                Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(),
                                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        });

                        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v,
                                                        int groupPosition, int childPosition, long id) {
                                Toast.makeText(
                                        Objects.requireNonNull(getActivity()).getApplicationContext(),
                                        expandableListTitle.get(groupPosition)
                                                + " -> "
                                                + Objects.requireNonNull(expandableListDetail.get(
                                                expandableListTitle.get(groupPosition))).get(
                                                childPosition), Toast.LENGTH_SHORT
                                ).show();
                                return false;
                            }
                        });
                        break;
                }
            }

            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        if (getArguments() != null) {
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    try {
                        TextView textView = Objects.requireNonNull(getActivity()).findViewById(R.id.inpDate);
                        textView.setText(MainActivity.dateFormat.format(new Date()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Spinner spinner = getActivity().findViewById(R.id.spnProject);

                    FileManager.read(App.getContext());

                    if (Project.getList().isEmpty()) {
                        Project.addItem(Project.getDummyItem());
                        spinner.setClickable(false);
                    }

                    ArrayAdapter<Project> spinnerArrayAdapter = new ArrayAdapter<>(
                            getActivity(), R.layout.spinner_item, Project.getList()
                    );
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    spinner.setAdapter(spinnerArrayAdapter);
                    break;
            }
        }
    }
}
