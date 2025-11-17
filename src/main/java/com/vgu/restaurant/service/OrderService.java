package com.vgu.restaurant.service;

import com.vgu.restaurant.model.Order;
import com.vgu.restaurant.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    boolean create(Order order);

    Optional<Order> getById(int id);

    List<Order> getAll();

    List<Order> findByCustomer(int customerId);

    List<Order> findByStatus(OrderStatus status);

    boolean updateStatus(int orderId, OrderStatus status);

    boolean update(Order order);

    boolean delete(int orderId);
}