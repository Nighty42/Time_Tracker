package com.zeiterfassung.listview;

import com.zeiterfassung.model.Appointment;
import com.zeiterfassung.model.Project;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<Appointment>> getData() {
        HashMap<String, List<Appointment>> expandableListDetail = new HashMap<>();

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

        ArrayList<Appointment> appointments = new ArrayList<>();
        Appointment tempAppointment = null;

        for (int i = Appointment.getList().size() - 1; i >= 0; i--) {
            Appointment appointment = Appointment.getList().get(i);

            if (tempAppointment != null && !appointment.equals(tempAppointment)) {
                expandableListDetail.put(tempAppointment.toString(), new ArrayList<>(appointments));
                appointments.clear();
            }

            tempAppointment = appointment;
            appointments.add(tempAppointment);
        }

        assert tempAppointment != null;
        expandableListDetail.put(tempAppointment.toString(), appointments);
        // TODO: Sort expandableListDetail by Date ascending

        return expandableListDetail;
    }
}
