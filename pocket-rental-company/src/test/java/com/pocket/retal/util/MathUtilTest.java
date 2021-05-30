package com.pocket.retal.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MathUtilTest {

    @Test
    void multiply() {
    }

    @Test
    void multiplyWithScale() {
    }

    @Test
    void testMultiplyWithScale() {
    }

    @Test
    void isEqualByBigDecimalCompareTo() {
    }

    @Test
    void div() {
    }

    @Test
    void testDiv() {
    }

    @Test
    void round() {
    }

    @Test
    void testRound() {
    }

    @Test
    void isValidDoubleString() {
        assertTrue(MathUtil.isValidDoubleString("123.12", false));
        assertTrue(MathUtil.isValidDoubleString("-123.12", false));
        assertTrue(MathUtil.isValidDoubleString("123", false));
        assertTrue(MathUtil.isValidDoubleString("0", false));
        assertTrue(MathUtil.isValidDoubleString("0.0", false));

        assertFalse(MathUtil.isValidDoubleString("", false));
        assertFalse(MathUtil.isValidDoubleString(null, false));
        assertFalse(MathUtil.isValidDoubleString("   ", false));
    }
}