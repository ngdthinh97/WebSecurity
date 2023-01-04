package com.spring.security.auth.service;

import com.spring.security.auth.entity.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OrderService {
    List<List<Order>> runOrderJob(List<Order> lstOrders, int n);

    List<List<Order>> doRunThreads(List<Order> lstOrders);

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    Order confirmOrder(Order order) throws Exception;
}
