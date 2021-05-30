package com.pocket.retal.model;

import com.pocket.retal.model.dto.VehicleSkuDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class VehicleSkuWithPrice extends VehicleSkuDTO {
    String skuPrice;
    String averageDailyPrice;

    public VehicleSkuWithPrice(int vehicleId, String skuGuid, String color, String skuPrice, String averageDailyPrice) {
        super(vehicleId, skuGuid, color);
        this.skuPrice = skuPrice;
        this.averageDailyPrice = averageDailyPrice;
    }

    public VehicleSkuWithPrice(String skuPrice, String averageDailyPrice) {
        this.skuPrice = skuPrice;
        this.averageDailyPrice = averageDailyPrice;
    }
}
