package com.zeiterfassung.model;

import android.support.annotation.NonNull;

import com.zeiterfassung.App;
import com.zeiterfassung.ErrorCode;
import com.zeiterfassung.R;

import java.util.ArrayList;

public class Project {
    private static Project dummyItem = new Project(App.getContext().getString(R.string.ecEmptySpinner));
    private static ArrayList<Project> list = new ArrayList<>();

    private String name;

    public Project(String name) {
        this.name = name;
    }

    public static Project getDummyItem() {
        return dummyItem;
    }

    public static ArrayList<Project> getList() {
        return list;
    }

    public static String addItem(Project item) {
        String errorCode;

        if (item.getName().trim().isEmpty()) {
            errorCode = ErrorCode.EmptyInput;
        } else if (list.contains(item)) {
            errorCode = ErrorCode.Duplicate;
        } else {
            errorCode = ErrorCode.OK;
            list.add(item);
        }

        if (list.contains(dummyItem) && list.size() > 1
                && errorCode.equals(ErrorCode.OK)) {
            list.remove(dummyItem);
        }

        return errorCode;
    }

    public static void delItem(Project item) {
        if (!item.equals(dummyItem)) {
            list.remove(item);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Project)) {
            return false;
        }

        return getName().equals(((Project) obj).getName());
    }

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
