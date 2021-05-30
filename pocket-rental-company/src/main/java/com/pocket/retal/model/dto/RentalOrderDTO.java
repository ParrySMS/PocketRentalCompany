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
public class RentalOrderDTO {
    public Integer orderId;
    public Integer clientId;
    public Date signingTime;
    public String totalPrice;
}
