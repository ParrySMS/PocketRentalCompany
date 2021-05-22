package com.pocket.retal.exception;


import com.pocket.retal.model.PocketResponseStatus;

public class PocketApiException extends RuntimeException {
    private PocketResponseStatus pocketResponseStatus;

    public PocketApiException(PocketResponseStatus pocketResponseStatus) {
        this(pocketResponseStatus, pocketResponseStatus.getMessage());
    }

    public PocketApiException(PocketResponseStatus pocketResponseStatus, String errorMessage) {
        super(errorMessage);
        this.pocketResponseStatus = pocketResponseStatus;
    }

    public PocketApiException(PocketResponseStatus pocketResponseStatus, String error, Exception e) {
        super(error, e);
        this.pocketResponseStatus = pocketResponseStatus;
    }

    public PocketResponseStatus getPocketResponseStatus() {
        return pocketResponseStatus;
    }
}
