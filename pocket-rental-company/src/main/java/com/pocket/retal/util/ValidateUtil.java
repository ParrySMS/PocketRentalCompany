package com.pocket.retal.util;

import com.pocket.retal.exception.PocketApiException;
import com.pocket.retal.exception.ValidationException;
import com.pocket.retal.model.enumeration.PocketResponseStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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

    public static void notNullOrEmptyOrBlank(String value, String paramName) throws ValidationException {
        if (value == null || value.isEmpty() || value.isBlank()) {
            throw new ValidationException(paramName + " should not be null or empty or blank");
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

    public static Date parseDate(String dateString) throws ValidationException, ParseException {
        return parseDate(dateString, null, false);
    }

    public static Date parseDate(String dateString, Date defaultDate, boolean isThrowException) throws ValidationException, ParseException {
        if (dateString == null || dateString.isEmpty() || dateString.isBlank()) {
            if (isThrowException) {
                throw new ValidationException("parse dateString error");
            }
            return defaultDate;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(YMD_DATE_FORMAT);
        Date date = formatter.parse(dateString);
        Date validDate = Optional.of(date).orElse(defaultDate);
        if (validDate == defaultDate && isThrowException) {
            throw new ValidationException("parse date error");
        }
        return validDate;
    }

    public static void IsValidUUID(String uuidString, String paramName) {
        try {
            UUID uuid = UUID.fromString(uuidString);
            uuid = null;
            //Friendly to Java garbage collection
        } catch (IllegalArgumentException e) {
            throw new ValidationException(paramName + "is not a valid uuid");
        }
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

    public static void effectRowsAssert(int expectedNum, int effectRows, String reposName) {
        if (effectRows != expectedNum) {
            throw new PocketApiException(
                    PocketResponseStatus.SYSTEM_INTERNAL_ERROR,
                    "effectRows != expectedNum on " + reposName);
        }
    }
}