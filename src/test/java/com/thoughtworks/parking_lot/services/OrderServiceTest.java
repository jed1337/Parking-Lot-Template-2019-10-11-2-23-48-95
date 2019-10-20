package com.thoughtworks.parking_lot.services;

import com.thoughtworks.parking_lot.entities.Order;
import com.thoughtworks.parking_lot.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void should_save_new_order() {
        Order order = new Order();
        order.setOrderNum("1");

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order savedOrder = orderService.save(order);

        assertThat(savedOrder.getOrderNum(), is(order.getOrderNum()));
        assertThat(savedOrder.getCloseTime(), is(nullValue()));
        assertThat(savedOrder.getOrderStatus(), is("OPEN"));
    }
}