package com.spring.security.auth.configuration;

import com.spring.security.auth.filter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public BCryptPasswordEncoder encoderPassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return authentication;
            }
        };

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
        System.out.println("User detail 1: " + "" + manager.loadUserByUsername("user") + " " + manager.loadUserByUsername("admin"));
        return manager;
    }

    @Bean
    SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {

        http.headers().frameOptions().disable();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/h2-console/**").permitAll()
//                    .antMatchers("/user/login/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

//        http.headers().frameOptions().disable();
//        http.csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/login*")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//        return http.build();
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