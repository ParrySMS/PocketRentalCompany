package com.pocket.retal.model.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum PriceFrequency {
    DAILY(1, "Daily"),
    WEEKLY(2, "Weekly"),
    MONTHLY(3, "Monthly"),
    HALF_YEARLY(4, "Half-Yearly"),
    YEARLY(5, "Yearly"),
    ;

    public int getPriceFrequencyId() {
        return priceFrequencyId;
    }

    public String getFrequencyType() {
        return frequencyType;
    }

    private int priceFrequencyId;
    private String frequencyType;

    PriceFrequency(int priceFrequencyId, String frequencyType) {
        this.priceFrequencyId = priceFrequencyId;
        this.frequencyType = frequencyType;
    }

    public static Map<Integer, Integer> getPriceFrequencyIdMapToFactorUnit(int defaultFactorUnit) {
        return new HashMap<>() {{
            put(PriceFrequency.DAILY.getPriceFrequencyId(), 0);
            put(PriceFrequency.WEEKLY.getPriceFrequencyId(), 0);
            put(PriceFrequency.MONTHLY.getPriceFrequencyId(), 0);
            put(PriceFrequency.HALF_YEARLY.getPriceFrequencyId(), 0);
            put(PriceFrequency.YEARLY.getPriceFrequencyId(), 0);
        }};
    }
}
