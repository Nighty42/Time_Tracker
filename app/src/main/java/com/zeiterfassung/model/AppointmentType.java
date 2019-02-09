package com.zeiterfassung.model;

import java.util.ArrayList;

public class AppointmentType {
    private static ArrayList<AppointmentType> list = new ArrayList<>();

    private String id;

    public AppointmentType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
