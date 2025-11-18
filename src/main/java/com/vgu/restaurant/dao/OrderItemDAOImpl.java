package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.Order;
import com.vgu.restaurant.model.OrderItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderItemDAOImpl implements OrderItemDAO {

    private static OrderItemDAOImpl instance;
    private OrderItemDAOImpl() {}

    public static OrderItemDAOImpl getInstance() {
        if (instance == null) {
            instance = new OrderItemDAOImpl();
        }
        return instance;
    }

    @Override
    public boolean add(OrderItem item) {
        String sql = "INSERT INTO order_items (orderId, menuItemId, price, quantity, note) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getMenuItemId());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getQuantity());
            ps.setString(5, item.getNote());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("addOrderItem error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<OrderItem> getByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE orderId = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) list.add(map(rs));

        } catch (Exception e) {
            System.out.println("getOrderItemsByOrderId error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean update(OrderItem item) {
        String sql = "UPDATE order_items SET menuItemId=?, price=?, quantity=?, note=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getMenuItemId());
            ps.setDouble(2, item.getPrice());
            ps.setInt(3, item.getQuantity());
            ps.setString(4, item.getNote());
            ps.setInt(5, item.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("updateOrderItem error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(OrderItem item) {
        String sql = "DELETE FROM order_items WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getId());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("deleteOrderItem error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<OrderItem> getById(int id) {
        String sql = "SELECT * FROM order_items WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return Optional.of(map(rs));

        } catch (Exception e) {
            System.out.println("getOrderItemById error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<OrderItem> getAll() {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (Exception e) {
            System.out.println("getAllOrderItems error: " + e.getMessage());
        }

        return list;
    }

    private OrderItem map(ResultSet rs) throws Exception {
        return new OrderItem(
                rs.getInt("id"),
                rs.getInt("orderId"),
                rs.getInt("menuItemId"),
                rs.getDouble("price"),
                rs.getInt("quantity"),
                rs.getString("note")
        );
    }
}
