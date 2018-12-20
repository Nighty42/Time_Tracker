package com.zeiterfassung.model;

import java.util.ArrayList;
import java.util.Date;

public class Termin {
    public static ArrayList<Termin> list = new ArrayList<>();

    private Date date;
    private Date fromTime;
    private Date toTime;
    private Project project;
    private String description;

    public Termin(Date date, Date fromTime, Date toTime, Project project, String description) {
        this.date = date;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.project = project;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
