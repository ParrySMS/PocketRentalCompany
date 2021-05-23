package com.pocket.retal.util;

import com.pocket.retal.exception.PocketApiException;
import com.pocket.retal.exception.ValidationException;
import com.pocket.retal.model.PocketResponseStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class ValidateUtil {
    private ValidateUtil() {
    }

    private static final String YMD_DATE_FORMAT = "yyyy/MM/dd";

    public static <T> void notNull(T value, String paramName) throws ValidationException {
        if (value == null) {
            throw new ValidationException(paramName + " should not be null");
        }
    }

    public static <T> void internalNotNull(T value) throws PocketApiException {
        if (value == null) {
            throw new PocketApiException(PocketResponseStatus.SYSTEM_INTERNAL_ERROR);
        }
    }

    public static void notNullOrEmpty(String value, String paramName) throws ValidationException {
        if (value == null || value.isEmpty()) {
            throw new ValidationException(paramName + " should not be null or empty");
        }
    }

    public static void min(int value, int minimum, String paramName) throws ValidationException {
        if (value < minimum) {
            throw new ValidationException(paramName + " minimum is " + minimum);
        }
    }

    public static void doubleMin(double value, double minimum, String paramName) throws ValidationException {
        if (value < minimum) {
            throw new ValidationException(paramName + " minimum is " + minimum);
        }
    }

    public static Optional<Date> parseDate(String dateString) throws ValidationException, ParseException {
        return parseDate(dateString, null, false);
    }

    public static Optional<Date> parseDate(String dateString, Date defaultDate, boolean isThrowException) throws ValidationException, ParseException {
        if (dateString == null || dateString.isEmpty() || dateString.isBlank()) {
            if (isThrowException) {
                throw new ValidationException("parse dateString error");
            }
            return Optional.of(defaultDate);
        }

        SimpleDateFormat formatter = new SimpleDateFormat(YMD_DATE_FORMAT);
        Date date = formatter.parse(dateString);
        Date validDate = Optional.of(date).orElse(defaultDate);
        if (validDate == defaultDate && isThrowException) {
            throw new ValidationException("parse date error");
        }
        return Optional.of(validDate);
    }

    public static class Friendly {
        private Friendly() {
        }

        public static void assertTrue(boolean condition, String friendlyMessage) throws ValidationException {
            if (!condition) {
                throw new ValidationException(friendlyMessage);
            }
        }

        public static void assertFalse(boolean condition, String friendlyMessage) throws ValidationException {
            if (condition) {
                throw new ValidationException(friendlyMessage);
            }
        }
    }
}
