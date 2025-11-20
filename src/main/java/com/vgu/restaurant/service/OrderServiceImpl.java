package com.vgu.restaurant.service;

import com.vgu.restaurant.dao.OrderDAO;
import com.vgu.restaurant.dao.OrderDAOImpl;
import com.vgu.restaurant.dao.OrderItemDAO;
import com.vgu.restaurant.dao.OrderItemDAOImpl;
import com.vgu.restaurant.model.Order;
import com.vgu.restaurant.model.OrderItem;
import com.vgu.restaurant.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO = OrderDAOImpl.getInstance();
    private final OrderItemDAO orderItemDAO = OrderItemDAOImpl.getInstance();

    @Override
    public boolean create(Order order) {
        return orderDAO.add(order);
    }

    @Override
    public boolean addItem(int orderId, OrderItem item) {
        item.setOrderId(orderId);
        return orderItemDAO.add(item);
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
    public List<Order> getByCustomer(int customerId) {
        return orderDAO.getByCustomer(customerId);
    }

    @Override
    public List<Order> getByStatus(OrderStatus status) {
        return orderDAO.getByStatus(status);
    }

    @Override
    public boolean updateItemQuantity(int orderItemId, int newQuantity) {
        Optional<OrderItem> opt = orderItemDAO.getById(orderItemId);
        if (opt.isEmpty()) return false;
        
        OrderItem found = opt.get();
        found.setQuantity(newQuantity);
        return orderItemDAO.update(found);
    }

    @Override
    public boolean updateStatus(int orderId, OrderStatus status) {
        return orderDAO.updateStatus(orderId, status);
    }

    @Override
    public boolean removeItem(int orderItemId) {
        Optional<OrderItem> item = orderItemDAO.getById(orderItemId);
        return item.isPresent() && orderItemDAO.delete(item.get());
    }

    @Override
    public double calculateTotal(int orderId) {
        Optional<Order> opt = orderDAO.getById(orderId);
        if (opt.isEmpty()) return 0.0;

        Order found = opt.get();
        return found.getItems().stream()
            .mapToDouble(i -> i.getPrice() * i.getQuantity())
            .sum();
    }
}
