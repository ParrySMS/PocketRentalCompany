package com.pocket.retal.service;

import com.pocket.retal.mock.MockRepo;
import com.pocket.retal.repository.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VehicleServiceTest {

    private VehicleService vehicleService;
    private VehicleRepository vehicleRepository = mock(VehicleRepository.class);

    @Test
    void getVehicles_normal_returnList() {
        when(vehicleRepository.selectAllVehicles(anyInt(), anyInt()))
                .thenReturn(MockRepo.getSomeMockVehicles());
        vehicleService = new VehicleService(vehicleRepository);
        var vehicles = vehicleService.getVehicles();

        Assertions.assertNotNull(vehicles);
        Assertions.assertFalse(vehicles.isEmpty());
    }

    @Test
    void getVehicles_empty_returnEmptyList() {
        when(vehicleRepository.selectAllVehicles(anyInt(), anyInt()))
                .thenReturn(new ArrayList<>());
        vehicleService = new VehicleService(vehicleRepository);
        var vehicles = vehicleService.getVehicles();

        Assertions.assertNotNull(vehicles);
        Assertions.assertTrue(vehicles.isEmpty());
    }

    @Test
    void getVehicles_null_returnEmptyList() {
        when(vehicleRepository.selectAllVehicles(anyInt(), anyInt()))
                .thenReturn(null);
        vehicleService = new VehicleService(vehicleRepository);
        var vehicles = vehicleService.getVehicles();

        Assertions.assertNotNull(vehicles);
        Assertions.assertTrue(vehicles.isEmpty());
    }
}