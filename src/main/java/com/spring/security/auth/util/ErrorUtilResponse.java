package com.spring.security.auth.util;

import com.spring.security.auth.enums.ErrorMessageEnum;
import com.spring.security.auth.model.out.ResError;

public class ErrorUtilResponse {

    public static ResError ErrorRes(String key, String message) {
        return ErrorMessageEnum.resError(key, message);
    }
}
