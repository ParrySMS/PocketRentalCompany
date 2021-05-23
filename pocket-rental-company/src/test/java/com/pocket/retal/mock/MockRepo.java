package com.pocket.retal.mock;

import com.pocket.retal.model.dto.RentalScheduleVehicleSkuDTO;
import com.pocket.retal.model.dto.VehicleDTO;
import com.pocket.retal.model.dto.VehicleSkuDTO;

import java.util.Arrays;
import java.util.Date;
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

    public static RentalScheduleVehicleSkuDTO getRentalScheduleVehicleSku(String skuGuid,
                                                                          int vehicleId,
                                                                          Date startTime,
                                                                          Date endTime) {
        return RentalScheduleVehicleSkuDTO.builder()
                .skuGuid(skuGuid)
                .vehicleId(vehicleId)
                .vehicleName("mockVehicle1")
                .vehicleModelYear(2020)
                .startTime(startTime)
                .endTime(endTime)
                .schedulePrice("123")
                .rentalOrderId(1)
                .build();
    }
}
