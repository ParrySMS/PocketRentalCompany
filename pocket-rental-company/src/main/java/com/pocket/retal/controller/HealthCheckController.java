package com.pocket.retal.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Api
public class HealthCheckController {

    @GetMapping("/health-check")
    public String healthCheck() {
        return " It's OK. " + LocalDate.now();
    }

    @GetMapping("/database-check")
    public String check() {
        return "";
    }
}
