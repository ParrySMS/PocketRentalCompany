package com.pocket.retal.util;

import com.pocket.retal.exception.PocketApiException;
import com.pocket.retal.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidateUtilTest {

    @Test
    void notNull() {
        assertThrows(ValidationException.class, () -> {
            ValidateUtil.notNull(null, "Integer");
        });
    }

    @Test
    void internalNotNull() {
        assertThrows(PocketApiException.class, () -> {
            ValidateUtil.internalNotNull(null);
        });
    }

    @Test
    void notNullOrEmptyOrBlank() {
        assertThrows(ValidationException.class, () -> {
            ValidateUtil.notNullOrEmptyOrBlank(null, "null");
        });

        assertThrows(ValidationException.class, () -> {
            ValidateUtil.notNullOrEmptyOrBlank("", "");
        });

        assertThrows(ValidationException.class, () -> {
            ValidateUtil.notNullOrEmptyOrBlank("  ", "");
        });
    }

    @Test
    void min() {
        assertThrows(ValidationException.class, () -> {
            ValidateUtil.min(-123, 0, "");
        });
    }

    @Test
    void doubleMin() {
        assertThrows(ValidationException.class, () -> {
            ValidateUtil.doubleMin(-123.13, 0, "");
        });
    }

    @Test
    void friendlyAssertTrue() {
        assertThrows(ValidationException.class, () -> {
            ValidateUtil.Friendly.assertTrue(false, "message");
        });
    }
}