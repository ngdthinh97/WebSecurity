package com.spring.security.auth.controller.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
@Slf4j
public class AOPTestController {

    @GetMapping
    public String test() {
        log.debug("----------- Controller -----------");
        goToRunner();
        return "test";
    }

    private void goToRunner() {
        log.debug("----------- Function -----------");

    }
}
