package com.pocket.retal.mock;

import com.pocket.retal.model.dto.VehicleDTO;
import com.pocket.retal.model.dto.VehicleSkuDTO;

import java.util.Arrays;
import java.util.List;

public class MockRepo {
    public static List<VehicleSkuDTO> getSomeMockVehicleSkus(int vehicleId) {
        var mockSku1 = VehicleSkuDTO.builder()
                .skuGuid("mockGuid1")
                .vehicleId(vehicleId)
                .color("mockColor1")
                .build();
        var mockSku2 = VehicleSkuDTO.builder()
                .skuGuid("mockGuid2")
                .vehicleId(vehicleId)
                .color("mockColor2")
                .build();
        return Arrays.asList(mockSku1, mockSku2);
    }

    public static List<VehicleDTO> getSomeMockVehicles() {
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
        return Arrays.asList(mockVehicle1, mockVehicle2);
    }
}
