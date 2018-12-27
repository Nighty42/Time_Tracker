package com.zeiterfassung.model;

import com.zeiterfassung.App;
import com.zeiterfassung.ErrorCode;
import com.zeiterfassung.R;

import java.util.ArrayList;

public class Project {
    private static String dummyItem = App.getContext().getString(R.string.ecEmptySpinner);
    private static ArrayList<String> list = new ArrayList<>();

    public static String getDummyItem() {
        return dummyItem;
    }

    public static ArrayList<String> getList() {
        return list;
    }

    public static String addItem(String item) {
        String errorCode;

        if (item.trim().isEmpty()) {
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

    public static int getIndexOfItem(String item) {
        return list.indexOf(item);
    }

    public static void delItem(String item) {
        if (!item.equals(dummyItem)) {
            list.remove(item);
        }
    }
}
