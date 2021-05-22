package com.pocket.retal.mock;

import com.pocket.retal.model.dto.VehicleDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockRepo {

    public static List<VehicleDTO> getSomeMockVehicles(){
        var mockVehicle1 = VehicleDTO.builder()
                .id(1)
                .name("mockVehicle1")
                .modelYear(2020)
                .description("nice car.")
                .build();

        var mockVehicle2 = VehicleDTO.builder()
                .id(2)
                .name("mockVehicle1")
                .modelYear(2018)
                .description("good car.")
                .build();
        return Arrays.asList(mockVehicle1,mockVehicle2);
    }
}
