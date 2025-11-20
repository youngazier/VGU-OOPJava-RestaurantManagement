package com.vgu.restaurant.service;

import com.vgu.restaurant.model.Order;
import com.vgu.restaurant.model.OrderItem;
import com.vgu.restaurant.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    boolean create(Order order);
    boolean addItem(int orderId, OrderItem item);

    Optional<Order> getById(int id);
    List<Order> getAll();
    List<Order> getByCustomer(int customerId);
    List<Order> getByStatus(OrderStatus status);

    boolean updateItemQuantity(int orderItemId, int newQuantity);
    boolean updateStatus(int orderId, OrderStatus status);

    boolean removeItem(int OrderItemId);

    double calculateTotal(int orderId);
}