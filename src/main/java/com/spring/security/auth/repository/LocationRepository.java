package com.spring.security.auth.repository;

import com.spring.security.auth.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
