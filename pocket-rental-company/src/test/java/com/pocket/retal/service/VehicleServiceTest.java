package com.pocket.retal.service;

import com.pocket.retal.mock.MockRepo;
import com.pocket.retal.repository.SkuRepository;
import com.pocket.retal.repository.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class VehicleServiceTest {

    private VehicleService vehicleService;
    private VehicleRepository vehicleRepository = mock(VehicleRepository.class);
    private SkuRepository skuRepository = mock(SkuRepository.class);

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

    @Test
    void getAllSkusFromOneVehicle_normal_returnList() {
        int mockVehicleId = 123;
        when(skuRepository.selectAllSkusFromOneVehicle(eq(mockVehicleId), anyInt(), anyInt()))
                .thenReturn(MockRepo.getSomeMockVehicleSkus(mockVehicleId));
        vehicleService = new VehicleService(skuRepository);
        var skus = vehicleService.getAllSkusFromOneVehicle(mockVehicleId);

        Assertions.assertNotNull(skus);
        Assertions.assertFalse(skus.isEmpty());
    }

    @Test
    void getAllSkusFromOneVehicle_empty_returnEmptyList() {
        int mockVehicleId = 456;
        when(skuRepository.selectAllSkusFromOneVehicle(eq(mockVehicleId), anyInt(), anyInt()))
                .thenReturn(new ArrayList<>());
        vehicleService = new VehicleService(skuRepository);
        var skus = vehicleService.getAllSkusFromOneVehicle(mockVehicleId);

        Assertions.assertNotNull(skus);
        Assertions.assertTrue(skus.isEmpty());
    }

    @Test
    void getAllSkusFromOneVehicle_null_returnEmptyList() {
        int mockVehicleId = 789;
        when(skuRepository.selectAllSkusFromOneVehicle(eq(mockVehicleId), anyInt(), anyInt()))
                .thenReturn(null);
        vehicleService = new VehicleService(skuRepository);
        var skus = vehicleService.getAllSkusFromOneVehicle(mockVehicleId);

        Assertions.assertNotNull(skus);
        Assertions.assertTrue(skus.isEmpty());
    }
}