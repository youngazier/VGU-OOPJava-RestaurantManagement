package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.Order;
import com.vgu.restaurant.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDAO extends DAOInterface<Order> {
    List<Order> getByCustomer(int customerId);
    List<Order> getByStatus(OrderStatus status);
    boolean updateStatus(int orderId, OrderStatus newStatus);
}