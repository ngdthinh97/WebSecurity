package com.spring.security.auth.exception;

public class CustomRuntimeException extends RuntimeException {
    private final String key;
    private final String message;

    public CustomRuntimeException(String message, String key) {
        this.message = message;
        this.key = key;
    }

    public String getErrorMessage() {
        return this.message;
    }

    public String getKey() {
        return this.key;
    }
}
