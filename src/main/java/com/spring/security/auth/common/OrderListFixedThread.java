package com.spring.security.auth.common;

import com.spring.security.auth.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class OrderListFixedThread implements Runnable {
    private List<Order> orderList;

    @Override
    public void run() {
        List<String> orderIds = orderList.parallelStream().map(p -> p.getId().toString()).collect(Collectors.toList());
        System.out.println(Thread.currentThread().getName() + "--->" + orderIds);
    }
}