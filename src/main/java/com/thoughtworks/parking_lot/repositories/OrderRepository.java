package com.thoughtworks.parking_lot.repositories;

import com.thoughtworks.parking_lot.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
