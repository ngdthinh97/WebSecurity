package com.spring.security.auth.enums;

import com.spring.security.auth.model.out.ResError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@NoArgsConstructor
@Getter
public enum ErrorMessageEnum {

    // ERRM = error message, 000 = stt , 404 error code
    ERM000400("ERM000400", "Bad Request",400),

    ERM000401("ERM000401", "Unauthorized",401),

    ERM000404("ERM000404", "Not Found",404),
    ERM001404("ERM001404", "Id Not Found",404),

    ERM000500("ERR000500", "Internal Server Error",500)
;

    ErrorMessageEnum(String key, String message, int code) {
        this.key = key;
        this.message = message;
        this.code = code;
    }

    public static ErrorMessageEnum ErrorInfo(String key) {
        return ErrorMessageEnum.valueOf(key);
    }

    public static ResError resError (String key, String message) {

        ErrorMessageEnum errorMessageEnum = ErrorMessageEnum.valueOf(String.valueOf(key));
        ResError res = new ResError();
        if(errorMessageEnum.getCode() == 400){
            res.setStatus(String.valueOf(HttpStatus.BAD_REQUEST));
        }
        if(errorMessageEnum.getCode() == 404){
            res.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
        }

        ResError.ErrorInfos ErrorInfos = ResError.ErrorInfos.builder()
                .message(message)
                .code(String.valueOf(errorMessageEnum.getCode()))
                .build();

        res.setErrors(Arrays.asList(ErrorInfos));
        return res;
    }

    private String key;
    private String message;
    private int code;
}
