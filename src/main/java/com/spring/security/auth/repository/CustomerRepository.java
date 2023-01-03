package com.spring.security.auth.repository;

import com.spring.security.auth.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

    Customer findByName(String name);
}
