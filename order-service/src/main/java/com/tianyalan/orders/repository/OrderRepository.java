package com.tianyalan.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tianyalan.orders.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
