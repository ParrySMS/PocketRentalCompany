package com.pocket.pocketrentalcompany.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Api
public class HealthCheckController {

    @Operation(summary = "get pending list of user", description = "get pending list of user", tags = {"HealthCheckApi"})
    @Parameter(name = "X-API-UserId", description = "user Id", required = true, in = ParameterIn.HEADER)
    @Parameter(name = "X-API-RequestId", description = "request id", required = true, in = ParameterIn.HEADER)
    @GetMapping(value = "/health-check", produces = "application/json")
    public String healthCheck() {
        return " It's OK. " + LocalDate.now();
    }
}
