package com.pocket.retal.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalOrderDTO {
    Integer orderId;
    Integer clientId;
    Date signingTime;
    String totalPrice;
    @JsonProperty("rentalSchedule")
    RentalScheduleDTO rentalScheduleDTO;
}
