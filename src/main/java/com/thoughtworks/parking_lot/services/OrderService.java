package com.thoughtworks.parking_lot.services;

import com.thoughtworks.parking_lot.entities.Order;
import com.thoughtworks.parking_lot.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public static final String OPEN = "OPEN";

    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order){
        order.setCloseTime(null);
        order.setOrderStatus(OPEN);
        return orderRepository.save(order);
    }
}
