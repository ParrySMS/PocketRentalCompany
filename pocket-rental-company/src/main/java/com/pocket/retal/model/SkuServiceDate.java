package com.pocket.retal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuServiceDate {
    String skuGuid;
    List<TimeInterval> serviceDates;
}
