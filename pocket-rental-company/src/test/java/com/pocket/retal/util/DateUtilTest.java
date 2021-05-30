package com.pocket.retal.util;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilTest {
    private static final String YMD_DATE_FORMAT = "yyyy/MM/dd";

    @Test
    void getNextDay() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(YMD_DATE_FORMAT);
        Date date = formatter.parse("2021/12/05");
        Date nextDate = formatter.parse("2021/12/06");
        Date actual = DateUtil.getNextDay(date);
        assertEquals(actual, nextDate);
    }

    @Test
    void getLastDay() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(YMD_DATE_FORMAT);
        Date lastDate = formatter.parse("2021/12/05");
        Date date = formatter.parse("2021/12/06");
        Date actual = DateUtil.getLastDay(date);
        assertEquals(actual, lastDate);
    }
}