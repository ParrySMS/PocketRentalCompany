package com.pocket.retal.service;

import com.pocket.retal.mock.MockRepo;
import com.pocket.retal.repository.RentalScheduleRepository;
import com.pocket.retal.repository.SkuRepository;
import com.pocket.retal.repository.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class VehicleServiceTest {

    private VehicleService vehicleService;
    private VehicleRepository vehicleRepository = mock(VehicleRepository.class);
    private RentalScheduleRepository rentalScheduleRepository = mock(RentalScheduleRepository.class);
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
    void getVehicles_withBothNullDateParam_returnList() {
        when(vehicleRepository.selectAllVehicles(anyInt(), anyInt()))
                .thenReturn(MockRepo.getSomeMockVehicles());
        vehicleService = new VehicleService(vehicleRepository);
        var vehicles = vehicleService.getVehiclesWithDates(null, null);

        Assertions.assertNotNull(vehicles);
        Assertions.assertFalse(vehicles.isEmpty());
    }

    @Test
    void getVehicles_withBothNonNullDateParam_returnList() {
        when(vehicleRepository.selectAllVehicles(anyInt(), anyInt()))
                .thenReturn(MockRepo.getSomeMockVehicles());

        when(rentalScheduleRepository.getRentalScheduleVehicleSkus(any(), any()))
                .thenReturn(new ArrayList<>());

        vehicleService = new VehicleService(vehicleRepository, skuRepository, rentalScheduleRepository);
        var date = new Date();

        var vehicles = vehicleService.getVehiclesWithDates(date, date);
        Assertions.assertNotNull(vehicles);
        Assertions.assertFalse(vehicles.isEmpty());
    }


    @Test
    void getVehicles_withOneNullOneNonNullDateParam_returnList() {
        when(vehicleRepository.selectAllVehicles(anyInt(), anyInt()))
                .thenReturn(MockRepo.getSomeMockVehicles());

        when(rentalScheduleRepository.getRentalScheduleVehicleSkus(any(), any()))
                .thenReturn(new ArrayList<>());

        vehicleService = new VehicleService(vehicleRepository, skuRepository, rentalScheduleRepository);
        var date = new Date();

        var vehiclesStartDateNull = vehicleService.getVehiclesWithDates(null, date);
        Assertions.assertNotNull(vehiclesStartDateNull);
        Assertions.assertFalse(vehiclesStartDateNull.isEmpty());

        var vehiclesEndDateNull = vehicleService.getVehiclesWithDates(date, null);
        Assertions.assertNotNull(vehiclesEndDateNull);
        Assertions.assertFalse(vehiclesEndDateNull.isEmpty());
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

    @Test
    void getAvailableVehicles() {
    }

    @Test
    void getRentalScheduleVehicleSkus() {
    }

    @Test
    void fixSkuAvailableDate() {
    }

    @Test
    void breakAvailableTimeInterval() {
    }

    @Test
    void buildSkuServiceDate() {
    }

    @Test
    void buildFreeSkuAvailableDate() {
    }
}