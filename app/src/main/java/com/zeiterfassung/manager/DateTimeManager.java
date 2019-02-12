package com.zeiterfassung.manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeManager {
    private static final DateFormat dateFormat = new SimpleDateFormat("EE, dd. MMMM yyyy", Locale.GERMANY);
    private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.GERMANY);

    public static Date stringToDate(String date) throws Exception {
        try {
            return dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }

    public static Date stringToTime(String time) throws Exception {
        try {
            return timeFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public static String timeToString(Date time) {
        return timeFormat.format(time);
    }
}
