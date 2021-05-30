package com.pocket.retal.model.enumeration;

public enum PocketResponseStatus {
    NORMAL("200200", ""),
    SYSTEM_INTERNAL_ERROR("500500", "Internal Error"),
    BAD_REQUEST_ERROR("400400", "Bad Request Error")
    ;

    private String appCode;
    private String message;

    public String getAppCode() {
        return this.appCode;
    }

    public String getMessage() {
        return this.message;
    }

    PocketResponseStatus(String appCode, String message) {
        this.appCode = appCode;
        this.message = message;
    }
}