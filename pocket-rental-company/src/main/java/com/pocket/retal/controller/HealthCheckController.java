package com.pocket.retal.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Api
public class HealthCheckController {

    @GetMapping(value = "/health-check", produces = "application/json")
    public String healthCheck() {
        return " It's OK. " + LocalDate.now();
    }
}
