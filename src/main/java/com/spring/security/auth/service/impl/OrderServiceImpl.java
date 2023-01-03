package com.spring.security.auth.service.impl;

import com.spring.security.auth.common.OrderListFixedThread;
import com.spring.security.auth.entity.Order;
import com.spring.security.auth.repository.OrderRepository;
import com.spring.security.auth.service.MultiThreads;
import com.spring.security.auth.service.OrderService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderListFixedThread orderListFixedThread;
    private MultiThreads multiThreads;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public  List<List<Order>> runOrderJob(List<Order> lstOrders, int n) {

        List<List<Order>> parts = new ArrayList<List<Order>>();
        for (int i = 0; i < lstOrders.size(); i += n) {
            List<Order> orders1 = new ArrayList<>();
            System.out.println(i + n);
            orders1 = lstOrders.subList(i, Math.min(lstOrders.size(), i + n));
            parts.add(orders1);
        }
        System.out.println(parts);

        System.out.println("-------------------------- Fixed Thread Pool ------------------------------");
        ExecutorService executor = Executors.newFixedThreadPool(5);
        parts.stream().forEach(s ->{
            OrderListFixedThread orderListFixedThread = new OrderListFixedThread();
            orderListFixedThread.setOrderList(s);
            executor.submit(orderListFixedThread);
        });
        executor.shutdown();
        return parts;
    }

    @Override
    public List<List<Order>> doRunThreads(List<Order> lstOrders) {
        int n = 10;
        List<List<Order>> parts = new ArrayList<>();
        for (int i = 0; i < lstOrders.size(); i += n) {
            List<Order> orders1 = new ArrayList<>();
            System.out.println(i + n);
            orders1 = lstOrders.subList(i, Math.min(lstOrders.size(), i + n));
            parts.add(orders1);
        }
        System.out.println(parts);

        System.out.println("-------------------------- Fixed Thread Pool ------------------------------");
        ExecutorService executor = Executors.newFixedThreadPool(5);
        parts.stream().forEach(s -> {
            multiThreads = new MultiThreadsImpl(s);
            executor.submit(multiThreads);
        });
        executor.shutdown();
        return parts;
    }

//    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    @Override
    public Order confirmOrder(Order order) throws Exception {
        Order saved = orderRepository.save(order);
        if (ObjectUtils.isNotEmpty(saved)) {
            throw new Exception("test");
        }
        return order;
    }
}
