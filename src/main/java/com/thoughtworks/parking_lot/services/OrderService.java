package com.thoughtworks.parking_lot.services;

import com.thoughtworks.parking_lot.entities.Order;
import com.thoughtworks.parking_lot.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order){
        return orderRepository.save(order);
    }
}
