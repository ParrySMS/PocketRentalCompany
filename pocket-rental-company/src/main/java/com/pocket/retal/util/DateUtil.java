package com.pocket.retal.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static int DAILY_DAY_UNIT = 1;
    public static int WEEKLY_DAY_UNIT = 7;
    public static int MONTHLY_DAY_UNIT = 28;
    public static int HALF_YEARLY_DAY_UNIT = 180;
    public static int YEARLY_DAY_UNIT = 365;
    public static int DAY_IN_MILLIS = 1000 * 3600 * 24;
    public static int WEEK_IN_MILLIS = DAY_IN_MILLIS * WEEKLY_DAY_UNIT;
    public static int MONTH_MIN_IN_MILLIS = DAY_IN_MILLIS * MONTHLY_DAY_UNIT;

    public static Date getNextDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date getLastDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    public static int getIntervalBetween(Date date1, Date date2, int timeInMillis) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long between = (time2 - time1) / timeInMillis;
        return Integer.parseInt(String.valueOf(between));
    }

    public static int getDaysBetween(Date date1, Date date2) {
        return getIntervalBetween(date1, date2, DAY_IN_MILLIS);
    }

    public static int getWeeksBetween(Date date1, Date date2) {
        return getIntervalBetween(date1, date2, WEEK_IN_MILLIS);
    }

    public static int getMonthsBetween(Date date1, Date date2) {
        return getIntervalBetween(date1, date2, MONTH_MIN_IN_MILLIS);
    }
}
