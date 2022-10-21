package com.spring.security.auth.repository;

import com.spring.security.auth.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {

}
