package com.zeiterfassung.com.zeiterfassung.model;

import java.util.ArrayList;

public class Project {
    private static ArrayList<String> list = new ArrayList<>();

    public static ArrayList<String> getList() {
        return list;
    }

    public static void addItem(String... items) {
        for (String item : items) {
            if (!item.isEmpty()) {
                list.add(item);
            }
        }
    }
}
