package com.pocket.retal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalScheduleDTO {
    String skuGuid;
    Date startTime;
    Date endTime;
    String schedulePrice;
}
