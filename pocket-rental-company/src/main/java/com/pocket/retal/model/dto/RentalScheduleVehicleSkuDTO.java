package com.pocket.retal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalScheduleVehicleSkuDTO {
    String skuGuid;
    Integer vehicleId;
    String vehicleName;
    Integer vehicleModelYear;
    Date startTime;
    Date endTime;
    String schedulePrice;
    Integer rentalOrderId;
}
