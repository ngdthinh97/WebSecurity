package com.spring.security.auth.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TestServiceAspect {


    @Before("execution(* com.spring.security.auth.controller..*.*(..))") // for all sub package
//    @Before("execution(* com.spring.security.auth.controller.log.*.*(..))")
//    @Before("execution(* com.spring.security.auth.controller.log.AOPTestController.*(..))")
    public void before(JoinPoint joinPoint) {
        log.info(" before called " + joinPoint.toString());
    }

    @After("execution(* com.spring.security.auth.controller..*.*(..))") // for all sub package
//    @After("execution(* com.spring.security.auth.controller.log.*.*(..))")
//    @Before("execution(* com.spring.security.auth.controller.log.AOPTestController.*(..))")
    public void after(JoinPoint joinPoint) {
        log.info(" after called " + joinPoint.toString());
    }
}