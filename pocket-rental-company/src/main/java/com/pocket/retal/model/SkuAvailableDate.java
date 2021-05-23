package com.pocket.retal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuAvailableDate {
    String skuGuid;
    int vehicleId;
    String vehicleName;
    int vehicleModelYear;
    Date timeIntervalStart;
    Date timeIntervalEnd;
    List<TimeInterval> availableDates;
}
