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
    int vehicleId;
    int vehicleName;
    int vehicleModelYear;
    Date startTime;
    Date endTime;
    String schedulePrice;
    int rentalOrderId;
}
