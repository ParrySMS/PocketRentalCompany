package com.pocket.retal.controller;

import com.pocket.retal.model.ApiResult;
import com.pocket.retal.model.dto.PriceFrequencyDTO;
import com.pocket.retal.service.PriceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@Api
public class HealthCheckController {

    PriceService priceService;

    @Autowired
    public HealthCheckController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return " It's OK. " + LocalDate.now();
    }

    @GetMapping("/database-check")
    public ResponseEntity<ApiResult<List<PriceFrequencyDTO>>> check() {
        return ApiResult.ok(priceService.getPriceFrequencies());
    }
}
