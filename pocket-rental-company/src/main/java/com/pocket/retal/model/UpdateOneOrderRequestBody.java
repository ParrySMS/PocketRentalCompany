package com.pocket.retal.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOneOrderRequestBody {
    Integer clientId;
    String skuGuid;
    @JsonProperty("startDate")
    String startDateStr;
    @JsonProperty("endDate")
    String endDateStr;
}
