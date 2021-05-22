package com.pocket.retal.model;

import lombok.Builder;
import org.springframework.http.ResponseEntity;

@Builder
public class ApiResult<T> {
    public boolean status;
    public String message;
    public T data;

    public static <TResult> ResponseEntity<ApiResult<TResult>> ok(
            String message,
            TResult data) {
        return ResponseEntity.ok(
                ApiResult.<TResult>builder().status(true).message(message).data(data).build());
    }


    public static <TResult> ResponseEntity<ApiResult<TResult>> ok() {
        return ResponseEntity.ok(
                ApiResult.<TResult>builder().status(true).message("success").data(null).build());
    }

    public static <TResult> ResponseEntity<ApiResult<TResult>> ok(TResult data) {
        return ResponseEntity.ok(
                ApiResult.<TResult>builder().status(true).message("success").data(data).build());
    }

    public static <TResult> ResponseEntity<ApiResult<TResult>> failed(String message) {
        return ResponseEntity.ok(
                ApiResult.<TResult>builder().status(false).message(message).build());
    }

    public static <TResult> ResponseEntity<ApiResult<TResult>> failed(String message, TResult data) {
        return ResponseEntity.ok(
                ApiResult.<TResult>builder().status(false).message(message).data(data).build());
    }

    public static <TResult> ResponseEntity<ApiResult<TResult>> failedWithBadRequest(String message) {
        return ResponseEntity.badRequest().body(
                ApiResult.<TResult>builder().status(false).message(message).build());
    }

    public static <TResult> ResponseEntity<ApiResult<TResult>> result(boolean status, TResult data) {
        return ResponseEntity.badRequest().body(
                ApiResult.<TResult>builder().status(status).data(data).build());
    }


}
