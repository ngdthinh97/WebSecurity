package com.spring.security.auth.controller;

import com.spring.security.auth.entity.Customer;
import com.spring.security.auth.entity.Order;
import com.spring.security.auth.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<List<Order>>> OrderDoJob(){
        List<Order> lstOrders = initOrderData();
        return new ResponseEntity<>(orderService.runOrderJob(lstOrders, 10), HttpStatus.OK);
    }

    @GetMapping("/2")
    public ResponseEntity<List<List<Order>>> OrderDoJob2() {
        List<Order> lstOrders = initOrderData();
        return new ResponseEntity<>(orderService.doRunThreads(lstOrders), HttpStatus.OK);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Order> confirmOrder () throws Exception {
        Order order = Order.builder().customer(Customer.builder().id(1l).build()).build();
        return new ResponseEntity<>(orderService.confirmOrder(order), HttpStatus.OK);
    }

    private static List<Order> initOrderData() {
        List<Order> orders = new ArrayList<>();
        for (int i = 1; i < 10000; i++) {
            Order order = new Order();
            order.setId(Long.valueOf(i));
            order.setCustomer(new Customer("thinh" + i));
            orders.add(order);
        }
        return orders;
    }
}