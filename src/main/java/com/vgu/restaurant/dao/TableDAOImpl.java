package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TableDAOImpl implements TableDAO{
    private static TableDAOImpl instance;
    private TableDAOImpl() {}

    public static TableDAOImpl getInstance() {
        if (instance == null) {
            instance = new TableDAOImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Table table) {
        String sql = "INSERT INTO order_items (capacity,status) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, table.getCapacity());
            ps.setString(2, table.getTableStatus().name());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("addTable error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateStatus(int tableId, TableStatus newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus.name());
            ps.setInt(2, tableId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("updateStatus error: " + e.getMessage());
            return false;
        }
        return false;
    }

    @Override
    public boolean update(Table table) {
        String sql = "UPDATE orders SET capacity=?, status=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, table.getCapacity());
            ps.setString(2, table.getStatus().name());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("updateTable error: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean delete(Optional<Table> table) {
        String sql = "DELETE FROM table WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, table.getId());
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("deleteOrderItem error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Table> getById(int id) {
        String sql = "SELECT * FROM order_items WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return Optional.of(map(rs));

        } catch (Exception e) {
            System.out.println("getTableById error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Table> getAll() {
        List<Table> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (Exception e) {
            System.out.println("getAllTable error: " + e.getMessage());
        }

        return list;
    }

    private Table map(ResultSet rs) throws Exception {
        return new Table(
                rs.getInt("id"),
                rs.getInt("capacity"),
                TableStatus.valueOf(rs.getString("status"))
        );
    }
}





}
