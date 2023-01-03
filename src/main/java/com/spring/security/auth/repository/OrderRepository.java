package com.spring.security.auth.repository;

import com.spring.security.auth.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select * from Order_table o left join Customer c On o.customer_id = c.id",nativeQuery = true)
    public Optional<List<Order>> findByOrderByCustomer();
}