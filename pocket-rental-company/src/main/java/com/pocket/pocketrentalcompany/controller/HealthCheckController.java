package com.pocket.pocketrentalcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class HealthCheckController {
    @Autowired
    private Environment env;

    @GetMapping(value = "/health-check", produces = "application/json")
    public String get() {
        return "It's OK. " + LocalDate.now();
    }
}
