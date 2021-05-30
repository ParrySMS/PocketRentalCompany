package com.pocket.retal.controller;

import com.pocket.retal.exception.ValidationException;
import com.pocket.retal.model.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
@Slf4j
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {
    // TODO: this shoule be set as the config for Env params
    public static boolean ENABLE_DETAIL_MESSAGE = true;
    public static String DEFAULT_MESSAGE = "System error.";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult<Object>> globalExceptionHandler(Exception ex, WebRequest request) {
        String msg = ENABLE_DETAIL_MESSAGE ? ex.getMessage() : DEFAULT_MESSAGE;
        if (ex instanceof ValidationException) {
            return ApiResult.failedWithBadRequest(msg);
        }
        return ApiResult.failed(msg);
    }

}