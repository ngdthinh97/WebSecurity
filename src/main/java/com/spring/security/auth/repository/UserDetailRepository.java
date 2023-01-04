package com.spring.security.auth.repository;

import com.spring.security.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByUsername(String name);

}