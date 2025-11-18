package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.Order;
import com.vgu.restaurant.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDAO extends DAOInterface<Order> {
    List<Order> findByCustomer(int customerId);
    List<Order> findByStatus(OrderStatus status);
    boolean updateStatus(int orderId, OrderStatus newStatus);
}
