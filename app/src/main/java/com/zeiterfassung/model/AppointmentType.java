package com.zeiterfassung.model;

import java.util.ArrayList;

public class AppointmentType {
    private static ArrayList<AppointmentType> list = new ArrayList<>();

    private String id;

    private AppointmentType(String id) {
        this.id = id;
    }

    public static AppointmentType createAndGet(String id) {
        AppointmentType appointmentType = new AppointmentType(id);
        int i = AppointmentType.getList().indexOf(appointmentType);

        switch (i) {
            case -1:
                AppointmentType.getList().add(appointmentType);
                break;
            default:
                appointmentType = AppointmentType.getList().get(i);
                break;
        }

        return appointmentType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static ArrayList<AppointmentType> getList() {
        return list;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AppointmentType)) {
            return false;
        }

        return id.equals(((AppointmentType) obj).getId());
    }
}
