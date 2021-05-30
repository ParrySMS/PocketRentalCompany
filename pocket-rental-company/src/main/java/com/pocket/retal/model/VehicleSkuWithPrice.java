package com.pocket.retal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleSkuWithPrice {
    Integer vehicleId;
    String skuGuid;
    String color;
    String skuPrice;
    String averageDailyPrice;
}
