package com.zeiterfassung.listview;

import com.zeiterfassung.model.Appointment;
import com.zeiterfassung.model.Project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        Project.addItem(new Project("Project 1"));
        Project.addItem(new Project("Project 2"));
        Project.addItem(new Project("Project 3"));

        Calendar cal = Calendar.getInstance();
        cal.set(2018, Calendar.DECEMBER, 26, 8, 30, 0);

        Date date = cal.getTime();
        Date fromTime = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 30);

        Date toTime = cal.getTime();

        Appointment ter = new Appointment(date, fromTime, toTime, Project.getList().get(0), "Test 1 Description");
        Appointment.addItem(ter);

        cal.set(2018, Calendar.DECEMBER, 27, 8, 0, 0);
        date = cal.getTime();
        fromTime = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 0);

        toTime = cal.getTime();

        ter = new Appointment(date, fromTime, toTime, Project.getList().get(1), "Test 2 Description");
        Appointment.addItem(ter);

        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 0);

        fromTime = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 15);
        cal.set(Calendar.MINUTE, 0);

        toTime = cal.getTime();

        ter = new Appointment(date, fromTime, toTime, Project.getList().get(1), "Test 3 Description");
        Appointment.addItem(ter);

        ArrayList<String> projects = new ArrayList<>();
        Appointment tempTermin = null;

        for (int i = Appointment.getList().size() - 1; i >= 0; i--) {
            Appointment termin = Appointment.getList().get(i);

            if (tempTermin != null && !termin.equals(tempTermin)) {
                expandableListDetail.put(tempTermin.toString(), new ArrayList<>(projects));
                projects.clear();
            }

            tempTermin = termin;
            projects.add(tempTermin.getProject().toString());
        }

        assert tempTermin != null;
        expandableListDetail.put(tempTermin.toString(), projects);

        return expandableListDetail;
    }
}
