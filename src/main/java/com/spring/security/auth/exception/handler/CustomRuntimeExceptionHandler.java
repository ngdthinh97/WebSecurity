package com.spring.security.auth.exception.handler;

import com.spring.security.auth.exception.CustomRuntimeException;
import com.spring.security.auth.model.out.ResError;
import com.spring.security.auth.util.ErrorUtilResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomRuntimeExceptionHandler {

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ResError> handleCustomException(CustomRuntimeException ex) {
        ResError resError = ErrorUtilResponse.ErrorRes(ex.getKey(), ex.getErrorMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resError);
    }
}