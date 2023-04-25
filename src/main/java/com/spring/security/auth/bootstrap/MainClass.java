package com.spring.security.auth.bootstrap;

import com.spring.security.auth.enums.ErrorMessageEnum;

public class MainClass {

    public static void main(String[] args) {

        ErrorMessageEnum badRequestInfo = ErrorMessageEnum.ErrorInfo("ERM000400");
        System.out.println(badRequestInfo.getMessage());
    }
}