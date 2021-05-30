package com.pocket.retal.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
    private MathUtil() {
    }

    public static final int DEF_DIV_SCALE = 2;
    public static final int DEF_MUL_SCALE = 2;
    private static final BigDecimal ONE = new BigDecimal("1");

    public static double multiply(String v1, String v2) {
        isValidDoubleString(v1, true);
        isValidDoubleString(v2, true);
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).doubleValue();
    }

    public static double multiplyWithScale(String v1, String v2) {
        return multiplyWithScale(v1, v2, DEF_MUL_SCALE);
    }

    public static double multiplyWithScale(String v1, String v2, int scale) {
        isValidDoubleString(v1, true);
        isValidDoubleString(v2, true);
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static boolean isEqualByBigDecimalCompareTo(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return (0 == b1.compareTo(b2));
    }

    public static double div(double dividend, double divisor) {
        return div(dividend, divisor, DEF_DIV_SCALE);
    }

    public static double div(double dividend, double divisor, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor));
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static double round(double num) {
        return round(num, DEF_MUL_SCALE);
    }

    public static Double round(Double num, int scale) {
        if (num == null) {
            return num;
        }
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal numDecimal = new BigDecimal(Double.toString(num));
        return numDecimal.divide(ONE, scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static boolean isValidDoubleString(String str, boolean isThrowException) {
        try {
            Double value = Double.valueOf(str);
            return true;
        } catch (Exception e) {
            if (isThrowException) {
                throw new IllegalArgumentException("This is not valid double String:" + str);
            } else {
                return false;
            }
        }
    }

}
