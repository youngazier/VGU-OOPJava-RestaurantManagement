package com.vgu.restaurant.service;

import com.vgu.restaurant.dao.OrderDAO;
import com.vgu.restaurant.dao.OrderDAOImpl;
import com.vgu.restaurant.model.Order;
import com.vgu.restaurant.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO = OrderDAOImpl.getInstance();

    @Override
    public boolean create(Order order) {
        return orderDAO.add(order);
    }

    @Override
    public Optional<Order> getById(int id) {
        return orderDAO.getById(id);
    }

    @Override
    public List<Order> getAll() {
        return orderDAO.getAll();
    }

    @Override
    public List<Order> findByCustomer(int customerId) {
        return orderDAO.findByCustomer(customerId);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orderDAO.findByStatus(status);
    }

    @Override
    public boolean updateStatus(int orderId, OrderStatus status) {
        return orderDAO.updateStatus(orderId, status);
    }

    @Override
    public boolean update(Order order) {
        return orderDAO.update(order);
    }

    @Override
    public boolean delete(int orderId) {
        Optional<Order> order = orderDAO.getById(orderId);
        if (order == null) return false;
        return orderDAO.delete(order);
    }
}
