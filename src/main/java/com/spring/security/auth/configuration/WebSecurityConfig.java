package com.spring.security.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoderPassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(BCryptPasswordEncoder encoderPassword) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(encoderPassword.encode("userPass"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(encoderPassword.encode("adminPass"))
                .roles("USER", "ADMIN")
                .build());
        System.out.println("User detail 1: " + "" + manager.loadUserByUsername("user") + " "  + manager.loadUserByUsername("admin"));
        return manager;
    }

    @Bean
    SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/login*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(User.withUsername("employee").password(encoderPassword().encode("password"))
                .roles("EMPLOYEE", "USER").build());
        userDetailsList.add(User.withUsername("manager").password(encoderPassword().encode("password"))
                .roles("MANAGER", "USER").build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }
}