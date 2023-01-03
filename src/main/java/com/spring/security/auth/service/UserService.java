package com.spring.security.auth.service;

import com.spring.security.auth.request.JwtRequest;

public interface UserService {

    String authenticate(JwtRequest userInfo);


}
