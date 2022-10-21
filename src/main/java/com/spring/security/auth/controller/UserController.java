package com.spring.security.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    @GetMapping("user/login")
    public String login() {
        return "Login success!";
    }

    @GetMapping
    public String hello() {
        return "pass auth!";
    }
}
