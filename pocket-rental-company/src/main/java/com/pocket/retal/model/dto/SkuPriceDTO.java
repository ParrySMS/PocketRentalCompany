package com.pocket.retal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuPriceDTO {
    String skuGuid;
    int priceFrequencyId;
    String price;
    String frequencyType;
}
