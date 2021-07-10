package com.pocket.retal.controller;

import com.pocket.retal.model.ApiResult;
import com.pocket.retal.model.VehicleSkuWithPrice;
import com.pocket.retal.model.dto.VehicleDTO;
import com.pocket.retal.model.dto.VehicleSkuDTO;
import com.pocket.retal.service.VehicleService;
import com.pocket.retal.util.ValidateUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@Api
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("")
    public ResponseEntity<ApiResult<List<VehicleDTO>>> getVehicles(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) throws ParseException {
        Date startDate = ValidateUtil.parseDate(startDateStr);
        Date endDate = ValidateUtil.parseDate(endDateStr);
        if (startDate != null && endDate != null) {
            ValidateUtil.Friendly.assertFalse(endDate.before(startDate),
                    "endDate should not before startDate");
            ValidateUtil.Friendly.assertFalse(endDate.equals(startDate),
                    "endDate should not be the same as startDate");
        }
        return ApiResult.ok(vehicleService.getVehiclesWithDates(startDate, endDate));
    }

    @GetMapping("/{offset}/{pageSize}")
    public ResponseEntity<ApiResult<List<VehicleDTO>>> getVehicles(
            @PathVariable("offset") int offset,
            @PathVariable("pageSize") int pageSize) {
        ValidateUtil.notNull(offset, "offset");
        ValidateUtil.notNull(pageSize, "pageSize");
        return ApiResult.ok(vehicleService.getVehicles(offset, pageSize));
    }

    @GetMapping("/{vehicleId}/skus")
    public ResponseEntity<ApiResult<List<VehicleSkuDTO>>> getAllSkusFromOneVehicle(
            @PathVariable("vehicleId") int vehicleId) {
        ValidateUtil.min(vehicleId, 1, "vehicleId");
        return ApiResult.ok(vehicleService.getAllSkusFromOneVehicle(vehicleId));
    }

    @GetMapping("/{vehicleId}/skus/{offset}/{pageSize}")
    public ResponseEntity<ApiResult<List<VehicleSkuDTO>>> getAllSkusFromOneVehicle(
            @PathVariable("vehicleId") int vehicleId,
            @PathVariable("offset") int offset,
            @PathVariable("pageSize") int pageSize) {
        ValidateUtil.min(vehicleId, 1, "vehicleId");
        ValidateUtil.notNull(offset, "offset");
        ValidateUtil.notNull(pageSize, "pageSize");
        return ApiResult.ok(vehicleService.getAllSkusFromOneVehicle(vehicleId, offset, pageSize));
    }

    @GetMapping("/{vehicleId}/skus/{skuGuid}/price")
    public ResponseEntity<ApiResult<VehicleSkuWithPrice>> getVehicleSkuWithPriceInSelectedPeriod(
            @RequestParam(value = "startDate") String startDateStr,
            @RequestParam(value = "endDate") String endDateStr,
            @PathVariable("vehicleId") int vehicleId,
            @PathVariable("skuGuid") String skuGuid) throws ParseException {
        Date startDate = ValidateUtil.parseDate(startDateStr, null, true);
        Date endDate = ValidateUtil.parseDate(endDateStr, null, true);
        ValidateUtil.Friendly.assertFalse(endDate.before(startDate),
                "endDate should not before startDate");
        ValidateUtil.Friendly.assertFalse(endDate.equals(startDate),
                "endDate should not be the same as startDate");
        ValidateUtil.min(vehicleId, 1, "vehicleId");
        ValidateUtil.notNullOrEmptyOrBlank(skuGuid, "skuGuid");
        ValidateUtil.IsValidUUID(skuGuid, "skuGuid");
        return ApiResult.ok(vehicleService.getVehicleSkuWithPriceInSelectedPeriod(vehicleId, skuGuid, startDate, endDate));
    }

}
