package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {

    private static OrderDAOImpl instance;
    private OrderDAOImpl() {}

    public static OrderDAOImpl getInstance() {
        if (instance == null) {
            instance = new OrderDAOImpl();
        }
        return instance;
    }

    private final OrderItemDAO orderItemDAO = OrderItemDAOImpl.getInstance();


    @Override
    public Optional<Order> getById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();

                Order order = map(rs);
                order.setItems(orderItemDAO.getByOrderId(id));
                return Optional.of(order);
            }

        } catch (Exception e) {
            System.out.println("getOrderById error: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean add(Order order) {
        String sql = "INSERT INTO orders (tableId, customerId, status, createdAt, note) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getTableId());
            if (order.getCustomerId() == null) ps.setNull(2, Types.INTEGER);
            else ps.setInt(2, order.getCustomerId());

            ps.setString(3, order.getStatus().name());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(5, order.getNote());

            int affected = ps.executeUpdate();
            if (affected == 0) return false;

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);

                for (OrderItem item : order.getItems()) {
                    item.setOrderId(orderId);
                    orderItemDAO.add(item);
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("addOrder error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Order> getByCustomer(int customerId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customerId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = map(rs);
                order.setItems(orderItemDAO.getByOrderId(order.getId()));
                list.add(order);
            }

        } catch (Exception e) {
            System.out.println("getByCustomer error: " + e.getMessage());
        }

        return list;
    }

    @Override
    public List<Order> getByStatus(OrderStatus status) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = map(rs);
                order.setItems(orderItemDAO.getByOrderId(order.getId()));
                list.add(order);
            }

        } catch (Exception e) {
            System.out.println("getByStatus error: " + e.getMessage());
        }

        return list;
    }

    @Override
    public boolean updateStatus(int orderId, OrderStatus newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus.name());
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("updateStatus error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Order order) {
        String sql = "UPDATE orders SET tableId=?, customerId=?, status=?, note=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, order.getTableId());
            if (order.getCustomerId() == null) ps.setNull(2, Types.INTEGER);
            else ps.setInt(2, order.getCustomerId());

            ps.setString(3, order.getStatus().name());
            ps.setString(4, order.getNote());
            ps.setInt(5, order.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("updateOrder error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Order order) {

        String deleteItems = "DELETE FROM order_items WHERE orderId = ?";
        String deleteOrder = "DELETE FROM orders WHERE id = ?";
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // delete order_items
            try (PreparedStatement psi = conn.prepareStatement(deleteItems)) {
                psi.setInt(1, order.getId());
                psi.executeUpdate();
            }

            // delete order
            int affected;
            try (PreparedStatement pso = conn.prepareStatement(deleteOrder)) {
                pso.setInt(1, order.getId());
                affected = pso.executeUpdate();
            }

            conn.commit();
            return affected > 0;

        } catch (Exception e) {
            System.out.println("deleteOrder error: " + e.getMessage());
            try { if (conn != null) conn.rollback(); } catch (Exception ignored) {}
            return false;

        } finally {
            try { if (conn != null) conn.setAutoCommit(true); } catch (Exception ignored) {}
        }
    }


    @Override
    public List<Order> getAll() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order order = map(rs);
                order.setItems(orderItemDAO.getByOrderId(order.getId()));
                list.add(order);
            }

        } catch (Exception e) {
            System.out.println("getAllOrders error: " + e.getMessage());
        }

        return list;
    }

    private Order map(ResultSet rs) throws Exception {
        return new Order(
                rs.getInt("id"),
                rs.getInt("tableId"),
                (Integer) rs.getObject("customerId"),
                new ArrayList<>(),
                OrderStatus.valueOf(rs.getString("status")),
                rs.getTimestamp("createdAt").toLocalDateTime(),
                rs.getString("note")
        );
    }
}
