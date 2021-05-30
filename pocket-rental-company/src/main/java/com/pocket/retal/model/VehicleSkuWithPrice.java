package com.pocket.retal.model;

import com.pocket.retal.model.dto.VehicleSkuDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleSkuWithPrice extends VehicleSkuDTO {
    String finalPrice;
    String averageDailyPrice;
}
