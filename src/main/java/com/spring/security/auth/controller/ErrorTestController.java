package com.spring.security.auth.controller;

import com.spring.security.auth.enums.ErrorMessageEnum;
import com.spring.security.auth.exception.CustomRuntimeException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorTestController {
    @GetMapping("/run-time")
    public ResponseEntity<?> runTimeExceptionTest(){
        throw new CustomRuntimeException( "Something went wrong" , ErrorMessageEnum.ERM000404.getKey());
    }
}
