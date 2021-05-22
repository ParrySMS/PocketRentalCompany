package com.pocket.retal.controller;

import com.pocket.retal.model.ApiResult;
import com.pocket.retal.model.dto.VehicleDTO;
import com.pocket.retal.service.VehicleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Api
@RequestMapping("/vehicles")
public class VehicleController {
    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("")
    public ResponseEntity<ApiResult<List<VehicleDTO>>> getVehicles() {
        try {
            return ApiResult.ok(vehicleService.getVehicles());
        } catch (Exception e) {
            return ApiResult.failed(e.getMessage());
        }
    }
}
