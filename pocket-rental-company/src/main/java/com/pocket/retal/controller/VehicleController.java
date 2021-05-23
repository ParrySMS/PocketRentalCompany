package com.pocket.retal.controller;

import com.pocket.retal.model.ApiResult;
import com.pocket.retal.model.dto.VehicleDTO;
import com.pocket.retal.model.dto.VehicleSkuDTO;
import com.pocket.retal.service.VehicleService;
import com.pocket.retal.util.ValidateUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<ApiResult<List<VehicleDTO>>> getVehicles(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) throws ParseException {
        Optional<Date> startDate = ValidateUtil.parseDate(startDateStr);
        Optional<Date> endDate = ValidateUtil.parseDate(endDateStr);
        if (startDate.isPresent() && endDate.isPresent()) {
            ValidateUtil.Friendly.assertFalse(endDate.get().before(startDate.get()),
                    "endDate should not before startDate");
        }
        try {
            return ApiResult.ok(vehicleService.getVehicles(startDate, endDate));
        } catch (Exception e) {
            return ApiResult.failed(e.getMessage());
        }
    }

    @GetMapping("/{offset}/{pageSize}")
    public ResponseEntity<ApiResult<List<VehicleDTO>>> getVehicles(
            @PathVariable("offset") int offset,
            @PathVariable("pageSize") int pageSize) {
        ValidateUtil.notNull(offset, "offset");
        ValidateUtil.notNull(pageSize, "pageSize");
        try {
            return ApiResult.ok(vehicleService.getVehicles(offset, pageSize));
        } catch (Exception e) {
            return ApiResult.failed(e.getMessage());
        }
    }

    @GetMapping("/{vehicleId}/skus")
    public ResponseEntity<ApiResult<List<VehicleSkuDTO>>> getAllSkusFromOneVehicle(
            @PathVariable("vehicleId") int vehicleId) {
        ValidateUtil.min(vehicleId, 1, "vehicleId");
        try {
            return ApiResult.ok(vehicleService.getAllSkusFromOneVehicle(vehicleId));
        } catch (Exception e) {
            return ApiResult.failed(e.getMessage());
        }
    }

    @GetMapping("/{vehicleId}/skus/{offset}/{pageSize}")
    public ResponseEntity<ApiResult<List<VehicleSkuDTO>>> getAllSkusFromOneVehicle(
            @PathVariable("vehicleId") int vehicleId,
            @PathVariable("offset") int offset,
            @PathVariable("pageSize") int pageSize) {
        ValidateUtil.min(vehicleId, 1, "vehicleId");
        ValidateUtil.notNull(offset, "offset");
        ValidateUtil.notNull(pageSize, "pageSize");
        try {
            return ApiResult.ok(vehicleService.getAllSkusFromOneVehicle(vehicleId, offset, pageSize));
        } catch (Exception e) {
            return ApiResult.failed(e.getMessage());
        }
    }
}
