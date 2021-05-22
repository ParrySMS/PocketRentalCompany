package com.pocket.retal.controller;

import com.pocket.retal.mock.MockRepo;
import com.pocket.retal.service.VehicleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
class VehicleControllerTest {

    private VehicleController vehicleController;
    private VehicleService vehicleService = mock(VehicleService.class);

    @Test
    void getVehicles_normal_returnApiResultOK() {
        when(vehicleService.getVehicles())
                .thenReturn(MockRepo.getSomeMockVehicles());
        vehicleController = new VehicleController(vehicleService);
        var apiResult = vehicleController.getVehicles();

        Assertions.assertNotNull(apiResult);
        Assertions.assertNotNull(apiResult.getBody());
        Assertions.assertTrue(apiResult.getBody().status);
        Assertions.assertFalse(apiResult.getBody().data.isEmpty());
    }

    @Test
    void getVehicles_exception_returnApiResultFailed() {
        doThrow(new IllegalStateException()).when(vehicleService).getVehicles();
        vehicleController = new VehicleController(vehicleService);
        var apiResult = vehicleController.getVehicles();

        Assertions.assertNotNull(apiResult);
        Assertions.assertNotNull(apiResult.getBody());
        Assertions.assertFalse(apiResult.getBody().status);
    }
}