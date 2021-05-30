package com.pocket.retal.exception;


import com.pocket.retal.model.enumeration.PocketResponseStatus;

public class ValidationException extends PocketApiException {

    public ValidationException(String message) {
        super(PocketResponseStatus.BAD_REQUEST_ERROR, message);
    }
}
