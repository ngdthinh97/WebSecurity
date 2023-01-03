package com.spring.security.auth.bootstrap;

import com.spring.security.auth.entity.UserDetail;
import com.spring.security.auth.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    public UserDetailRepository userDetailRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        System.out.println("------------------- Bootstrap Data -------------------");
        UserDetail userDetail = new UserDetail();
        userDetail.setUsername("user");
        userDetail.setPassword("userP");
        userDetail.setRole("user");

        UserDetail saved = userDetailRepository.save(userDetail);

        UserDetail userDetail2 = new UserDetail();
        userDetail.setUsername("Admin");
        userDetail.setPassword("adminP");
        userDetail.setRole("admin");

        UserDetail saved2 = userDetailRepository.save(userDetail);
        System.out.println(userDetail);
    }
}
