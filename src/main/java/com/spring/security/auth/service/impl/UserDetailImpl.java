package com.spring.security.auth.service.impl;

import com.spring.security.auth.request.JwtRequest;
import com.spring.security.auth.service.UserService;
import com.spring.security.auth.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailImpl implements UserService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String authenticate(JwtRequest userInfo) {

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(userInfo.getUsername());

        boolean isPasswordMatched = passwordEncoder.matches(userInfo.getPassword(), userDetails.getPassword());
        if (!isPasswordMatched) {
            return "Bad request";
        }

//        if (!userDetails.getPassword().equals(userInfo.getPassword())) {
//            throw new RuntimeException("Bad request");
//        }
        return jwtTokenUtil.generateToken(userDetails);

    }
}
