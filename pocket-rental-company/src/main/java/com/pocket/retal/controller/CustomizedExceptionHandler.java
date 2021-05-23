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
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult<Object>> globalExceptionHandler(Exception ex, WebRequest request) {
        String msg = ex.getMessage() + System.getProperty("line.separator") + request.getParameterMap().toString();
        if (ex instanceof ValidationException) {
            return ApiResult.failedWithBadRequest(msg);
        }
        return ApiResult.failed(msg);
    }

}