package com.pocket.retal.service;

import com.pocket.retal.mock.MockRecourse;
import com.pocket.retal.model.TimeInterval;
import com.pocket.retal.model.dto.RentalScheduleVehicleSkuDTO;
import com.pocket.retal.repository.RentalScheduleRepository;
import com.pocket.retal.repository.SkuRepository;
import com.pocket.retal.repository.VehicleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

class VehicleServiceTest {
    private static final String YMD_DATE_FORMAT = "yyyy/MM/dd";

    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private RentalScheduleRepository rentalScheduleRepository;
    @Mock
    private SkuRepository skuRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getVehicles_normal_returnList() {
        when(vehicleRepository.selectAllVehicles(anyInt(), anyInt()))
                .thenReturn(MockRecourse.getSomeMockVehicles());
        var vehicles = vehicleService.getVehicles();

        Assertions.assertNotNull(vehicles);
        Assertions.assertFalse(vehicles.isEmpty());
    }

    @Test
    void getVehicles_withBothNullDateParam_returnList() {
        when(vehicleRepository.selectAllVehicles(anyInt(), anyInt()))
                .thenReturn(MockRecourse.getSomeMockVehicles());
        var vehicles = vehicleService.getVehiclesWithDates(null, null);

        Assertions.assertNotNull(vehicles);
        Assertions.assertFalse(vehicles.isEmpty());
    }

    @Test
    void getVehicles_withBothNonNullDateParam_returnList() {
        when(vehicleRepository.selectAllVehicles(anyInt(), anyInt()))
                .thenReturn(MockRecourse.getSomeMockVehicles());

        when(rentalScheduleRepository.getRentalScheduleVehicleSkus(any(), any()))
                .thenReturn(new ArrayList<>());

        var date = new Date();
        var vehicles = vehicleService.getVehiclesWithDates(date, date);
        Assertions.assertNotNull(vehicles);
        Assertions.assertFalse(vehicles.isEmpty());
    }


    @Test
    void getVehicles_withOneNullOneNonNullDateParam_returnList() {
        when(vehicleRepository.selectAllVehicles(anyInt(), anyInt()))
                .thenReturn(MockRecourse.getSomeMockVehicles());

        when(rentalScheduleRepository.getRentalScheduleVehicleSkus(any(), any()))
                .thenReturn(new ArrayList<>());

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
        var vehicles = vehicleService.getVehicles();

        Assertions.assertNotNull(vehicles);
        Assertions.assertTrue(vehicles.isEmpty());
    }

    @Test
    void getVehicles_null_returnEmptyList() {
        when(vehicleRepository.selectAllVehicles(anyInt(), anyInt()))
                .thenReturn(null);
        var vehicles = vehicleService.getVehicles();

        Assertions.assertNotNull(vehicles);
        Assertions.assertTrue(vehicles.isEmpty());
    }

    @Test
    void getAllSkusFromOneVehicle_normal_returnList() {
        int mockVehicleId = 123;
        when(skuRepository.selectAllSkusFromOneVehicle(eq(mockVehicleId), anyInt(), anyInt()))
                .thenReturn(MockRecourse.getSomeMockVehicleSkus(mockVehicleId));
        var skus = vehicleService.getAllSkusFromOneVehicle(mockVehicleId);

        Assertions.assertNotNull(skus);
        Assertions.assertFalse(skus.isEmpty());
    }

    @Test
    void getAllSkusFromOneVehicle_empty_returnEmptyList() {
        int mockVehicleId = 456;
        when(skuRepository.selectAllSkusFromOneVehicle(eq(mockVehicleId), anyInt(), anyInt()))
                .thenReturn(new ArrayList<>());
        var skus = vehicleService.getAllSkusFromOneVehicle(mockVehicleId);

        Assertions.assertNotNull(skus);
        Assertions.assertTrue(skus.isEmpty());
    }

    @Test
    void getAllSkusFromOneVehicle_null_returnEmptyList() {
        int mockVehicleId = 789;
        when(skuRepository.selectAllSkusFromOneVehicle(eq(mockVehicleId), anyInt(), anyInt()))
                .thenReturn(null);
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
    void getSkuPriceInSelectedPeriod() {

    }

    @Test
    void breakAvailableTimeInterval_noIntersection_returnSameAvailableTimeInterval() throws ParseException {
        List<TimeInterval> actual = breakAvailableTimeIntervalCaseTemplate(
                "2021/12/05",
                "2021/12/07",
                "2021/3/05",
                "2021/3/07"
        );
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(buildTimeInterval("2021/3/05", "2021/3/07"), actual.get(0));
    }

    @Test
    void breakAvailableTimeInterval_ServiceOverAvailable_returnEmptyAvailableTimeInterval() throws ParseException {
        List<TimeInterval> actual = breakAvailableTimeIntervalCaseTemplate(
                "2021/5/05",
                "2021/6/07",
                "2021/5/15",
                "2021/5/25"
        );
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }


    @Test
    void breakAvailableTimeInterval_ServiceEqualsAvailable_returnEmptyAvailableTimeInterval() throws ParseException {
        List<TimeInterval> actual = breakAvailableTimeIntervalCaseTemplate(
                "2021/5/05",
                "2021/6/07",
                "2021/5/05",
                "2021/6/07"
        );
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void breakAvailableTimeInterval_ServiceTakeTheFrontAvailable_returnPartAvailableTimeInterval() throws ParseException {
        List<TimeInterval> actual = breakAvailableTimeIntervalCaseTemplate(
                "2021/5/05",
                "2021/5/15",
                "2021/5/10",
                "2021/5/25"
        );
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(buildTimeInterval("2021/5/16", "2021/5/25"), actual.get(0));
    }

    @Test
    void breakAvailableTimeInterval_ServiceTakeTheEndAvailable_returnPartAvailableTimeInterval() throws ParseException {
        List<TimeInterval> actual = breakAvailableTimeIntervalCaseTemplate(
                "2021/5/10",
                "2021/5/25",
                "2021/5/05",
                "2021/5/15"
        );
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals(buildTimeInterval("2021/5/5", "2021/5/9"), actual.get(0));
    }

    @Test
    void breakAvailableTimeInterval_ServiceTakeTheMiddlePartAvailable_returnTwoAvailableTimeInterval() throws ParseException {
        List<TimeInterval> actual = breakAvailableTimeIntervalCaseTemplate(
                "2021/5/10",
                "2021/5/15",
                "2021/5/05",
                "2021/5/25"
        );
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(2, actual.size());
        Assertions.assertEquals(buildTimeInterval("2021/5/5", "2021/5/9"), actual.get(0));
        Assertions.assertEquals(buildTimeInterval("2021/5/16", "2021/5/25"), actual.get(1));
    }

    @Test
    void buildSkuServiceDate() {
        RentalScheduleVehicleSkuDTO scheduleVehicleSku = RentalScheduleVehicleSkuDTO.builder().build();
        Assertions.assertNotNull(vehicleService.buildSkuServiceDate(scheduleVehicleSku));
    }

    @Test
    void buildFreeSkuAvailableDate() {
        Date date = new Date();
        RentalScheduleVehicleSkuDTO scheduleVehicleSku =
                MockRecourse.getRentalScheduleVehicleSku("skuGuid", 1, date, date);
        Assertions.assertNotNull(vehicleService.buildFreeSkuAvailableDate(scheduleVehicleSku, date, date));
    }


    private TimeInterval buildTimeInterval(String startDateString, String endDateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(YMD_DATE_FORMAT);
        Date availableTimeIntervalStart = formatter.parse(startDateString);
        Date availableTimeIntervalEnd = formatter.parse(endDateString);
        return TimeInterval.builder()
                .startDate(availableTimeIntervalStart)
                .endDate(availableTimeIntervalEnd)
                .build();
    }

    private List<TimeInterval> breakAvailableTimeIntervalCaseTemplate(String serviceTimeIntervalStartString,
                                                                      String serviceTimeIntervalEndString,
                                                                      String availableTimeIntervalStartString,
                                                                      String availableTimeIntervalEndString) throws ParseException {
        TimeInterval serviceTimeInterval = buildTimeInterval(serviceTimeIntervalStartString, serviceTimeIntervalEndString);
        TimeInterval availableTimeInterval = buildTimeInterval(availableTimeIntervalStartString, availableTimeIntervalEndString);
        return vehicleService.breakAvailableTimeInterval(serviceTimeInterval, availableTimeInterval);
    }
}