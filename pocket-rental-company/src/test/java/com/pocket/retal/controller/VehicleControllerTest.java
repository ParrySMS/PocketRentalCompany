package com.pocket.retal.controller;

import com.pocket.retal.mock.MockRepo;
import com.pocket.retal.service.VehicleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VehicleControllerTest {

    private VehicleController vehicleController;
    private VehicleService vehicleService = mock(VehicleService.class);

    @Test
    void getVehicles_normalFirstPage_returnApiResultOK() throws ParseException {
        when(vehicleService.getVehiclesWithDates(any(), any()))
                .thenReturn(MockRepo.getSomeMockVehicles());
        vehicleController = new VehicleController(vehicleService);
        var apiResult = vehicleController.getVehicles("", "");

        Assertions.assertNotNull(apiResult);
        Assertions.assertNotNull(apiResult.getBody());
        Assertions.assertTrue(apiResult.getBody().status);
        Assertions.assertFalse(apiResult.getBody().data.isEmpty());
    }

    @Test
    void getVehicles_normalSecondPage_returnApiResultOK() {
        when(vehicleService.getVehicles(anyInt(), anyInt()))
                .thenReturn(MockRepo.getSomeMockVehicles());
        vehicleController = new VehicleController(vehicleService);
        var apiResult = vehicleController.getVehicles(0, 1);

        Assertions.assertNotNull(apiResult);
        Assertions.assertNotNull(apiResult.getBody());
        Assertions.assertTrue(apiResult.getBody().status);
        Assertions.assertFalse(apiResult.getBody().data.isEmpty());
    }


    @Test
    void getAllSkusFromOneVehicle_normalFirstPage_returnApiResultOK() {
        int mockVehicleId = 12;
        when(vehicleService.getAllSkusFromOneVehicle(mockVehicleId))
                .thenReturn(MockRepo.getSomeMockVehicleSkus(mockVehicleId));
        vehicleController = new VehicleController(vehicleService);
        var apiResult = vehicleController.getAllSkusFromOneVehicle(mockVehicleId);

        Assertions.assertNotNull(apiResult);
        Assertions.assertNotNull(apiResult.getBody());
        Assertions.assertTrue(apiResult.getBody().status);
        Assertions.assertFalse(apiResult.getBody().data.isEmpty());
    }

    @Test
    void getAllSkusFromOneVehicle_normalSecondPage_returnApiResultOK() {
        int mockVehicleId = 34;
        when(vehicleService.getAllSkusFromOneVehicle(eq(mockVehicleId), anyInt(), anyInt()))
                .thenReturn(MockRepo.getSomeMockVehicleSkus(mockVehicleId));
        vehicleController = new VehicleController(vehicleService);
        var apiResult = vehicleController.getAllSkusFromOneVehicle(mockVehicleId, 0, 1);

        Assertions.assertNotNull(apiResult);
        Assertions.assertNotNull(apiResult.getBody());
        Assertions.assertTrue(apiResult.getBody().status);
        Assertions.assertFalse(apiResult.getBody().data.isEmpty());
    }

}