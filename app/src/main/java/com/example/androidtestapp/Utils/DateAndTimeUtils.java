package com.example.androidtestapp.Utils;

import java.util.Calendar;

public class DateAndTimeUtils {
    // get current time in minutes and seconds.
    public static String getCurrentTimeAsString(){
            Calendar calendar = Calendar.getInstance();
            int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
            int hour12hrs = calendar.get(Calendar.HOUR);
            int minutes = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);
        return minutes+":"+ seconds;
    }
}
