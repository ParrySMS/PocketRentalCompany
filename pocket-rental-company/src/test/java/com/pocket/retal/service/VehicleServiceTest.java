package com.pocket.retal.service;

import com.pocket.retal.mock.MockRepo;
import com.pocket.retal.model.TimeInterval;
import com.pocket.retal.model.dto.RentalScheduleVehicleSkuDTO;
import com.pocket.retal.repository.RentalScheduleRepository;
import com.pocket.retal.repository.SkuRepository;
import com.pocket.retal.repository.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VehicleServiceTest {
    private static final String YMD_DATE_FORMAT = "yyyy/MM/dd";
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
    void breakAvailableTimeInterval_noIntersection_returnSameAvailableTimeInterval() throws ParseException {
        vehicleService = new VehicleService(vehicleRepository, skuRepository, rentalScheduleRepository);

        SimpleDateFormat formatter = new SimpleDateFormat(YMD_DATE_FORMAT);
        Date serviceTimeIntervalStart = formatter.parse("2021/12/05");
        Date serviceTimeIntervalEnd = formatter.parse("2021/12/07");
        TimeInterval serviceTimeInterval = TimeInterval.builder()
                .startDate(serviceTimeIntervalStart)
                .endDate(serviceTimeIntervalEnd)
                .build();

        Date availableTimeIntervalStart = formatter.parse("2021/3/05");
        Date availableTimeIntervalEnd = formatter.parse("2021/3/07");
        TimeInterval availableTimeInterval = TimeInterval.builder()
                .startDate(availableTimeIntervalStart)
                .endDate(availableTimeIntervalEnd)
                .build();

        List<TimeInterval> actual = vehicleService.breakAvailableTimeInterval(serviceTimeInterval, availableTimeInterval);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(availableTimeInterval, actual.get(0));
    }

    @Test
    void breakAvailableTimeInterval_ServiceOverAvailable_returnEmptyAvailableTimeInterval() throws ParseException {
        vehicleService = new VehicleService(vehicleRepository, skuRepository, rentalScheduleRepository);

        SimpleDateFormat formatter = new SimpleDateFormat(YMD_DATE_FORMAT);
        Date serviceTimeIntervalStart = formatter.parse("2021/5/05");
        Date serviceTimeIntervalEnd = formatter.parse("2021/6/07");
        TimeInterval serviceTimeInterval = TimeInterval.builder()
                .startDate(serviceTimeIntervalStart)
                .endDate(serviceTimeIntervalEnd)
                .build();

        Date availableTimeIntervalStart = formatter.parse("2021/5/15");
        Date availableTimeIntervalEnd = formatter.parse("2021/5/25");
        TimeInterval availableTimeInterval = TimeInterval.builder()
                .startDate(availableTimeIntervalStart)
                .endDate(availableTimeIntervalEnd)
                .build();

        List<TimeInterval> actual = vehicleService.breakAvailableTimeInterval(serviceTimeInterval, availableTimeInterval);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }


    @Test
    void breakAvailableTimeInterval_ServiceEqualsAvailable_returnEmptyAvailableTimeInterval() throws ParseException {
        vehicleService = new VehicleService(vehicleRepository, skuRepository, rentalScheduleRepository);

        SimpleDateFormat formatter = new SimpleDateFormat(YMD_DATE_FORMAT);
        Date serviceTimeIntervalStart = formatter.parse("2021/5/05");
        Date serviceTimeIntervalEnd = formatter.parse("2021/6/07");
        TimeInterval serviceTimeInterval = TimeInterval.builder()
                .startDate(serviceTimeIntervalStart)
                .endDate(serviceTimeIntervalEnd)
                .build();

        Date availableTimeIntervalStart = formatter.parse("2021/5/05");
        Date availableTimeIntervalEnd = formatter.parse("2021/6/07");
        TimeInterval availableTimeInterval = TimeInterval.builder()
                .startDate(availableTimeIntervalStart)
                .endDate(availableTimeIntervalEnd)
                .build();

        List<TimeInterval> actual = vehicleService.breakAvailableTimeInterval(serviceTimeInterval, availableTimeInterval);
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void buildSkuServiceDate() {
        RentalScheduleVehicleSkuDTO scheduleVehicleSku = RentalScheduleVehicleSkuDTO.builder().build();
        vehicleService = new VehicleService(vehicleRepository, skuRepository, rentalScheduleRepository);
        Assertions.assertNotNull(vehicleService.buildSkuServiceDate(scheduleVehicleSku));
    }

    @Test
    void buildFreeSkuAvailableDate() {
        Date date = new Date();
        RentalScheduleVehicleSkuDTO scheduleVehicleSku =
                MockRepo.getRentalScheduleVehicleSku("skuGuid", 1, date, date);
        vehicleService = new VehicleService(vehicleRepository, skuRepository, rentalScheduleRepository);
        Assertions.assertNotNull(vehicleService.buildFreeSkuAvailableDate(scheduleVehicleSku, date, date));
    }
}