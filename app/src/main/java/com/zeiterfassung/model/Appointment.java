package com.zeiterfassung.model;

import android.support.annotation.NonNull;

import com.zeiterfassung.manager.DateTimeManager;

import java.util.ArrayList;
import java.util.Date;

public class Appointment {
    private static ArrayList<Appointment> list = new ArrayList<>();

    private Date date;
    private Date fromTime;
    private Date toTime;
    private Project project;
    private AppointmentType appointmentType;
    private String description;

    public Appointment(Date date, Date fromTime, Date toTime, Project project, AppointmentType appointmentType, String description) {
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.project = project;
        this.appointmentType = appointmentType;
        this.description = description;
    }

    public static ArrayList<Appointment> getList() {
        return list;
    }

    public static void addItem(Appointment item) {
        list.add(item);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Appointment)) {
            return false;
        }

        return date.equals(((Appointment) obj).getDate());
    }

    @NonNull
    @Override
    public String toString() {
        return DateTimeManager.dateToString(getDate());
    }
}
