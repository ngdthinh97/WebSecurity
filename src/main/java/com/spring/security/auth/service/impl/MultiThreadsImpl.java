package com.spring.security.auth.service.impl;

import com.spring.security.auth.entity.Order;
import com.spring.security.auth.service.MultiThreads;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class MultiThreadsImpl implements MultiThreads {
    private List<Order> orderList;

    @Override
    public void run() {
        List<String> orderIds = orderList.parallelStream().map(p -> p.getId().toString()).collect(Collectors.toList());
        System.out.println(Thread.currentThread().getName() + "--->" + orderIds);
    }

    public MultiThreadsImpl(List<Order> orderList) {
        this.orderList = orderList;
    }
}
