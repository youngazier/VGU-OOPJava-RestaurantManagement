package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.Table;
import com.vgu.restaurant.model.TableStatus;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TableDAOImpl implements TableDAO {

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
        String sql = "INSERT INTO tables (capacity,status) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, table.getCapacity());
            ps.setString(2, table.getStatus().name());

            int affected = ps.executeUpdate();
            if (affected == 0) return false;

            try (ResultSet key = ps.getGeneratedKeys()) {
                if (key.next()) {
                    table.setId(key.getInt(1));
                }
            }
            return true;

        } catch (Exception e) {
            System.out.println("addTable error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Table table) {
        String sql = "UPDATE tables SET capacity = ?, status = ? WHERE id = ? ";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, table.getCapacity());
            ps.setString(2, table.getStatus().name());
            ps.setInt(3, table.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("updateTable error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateStatus(int tableId, TableStatus newStatus) {
        String sql = "UPDATE tables SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus.name());
            ps.setInt(2, tableId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("updateStatus error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Table table) {
        String sql = "DELETE FROM tables WHERE id = ?";

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
        String sql = "SELECT * FROM tables WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(map(rs));
            }

        } catch (Exception e) {
            System.out.println("getTableById error: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Table> getByStatus(TableStatus status) {
        List<Table> list = new ArrayList<>();
        String sql = "SELECT * FROM tables WHERE status = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }

        } catch (Exception e) {
            System.out.println("getTableByStatus error: " + e.getMessage());
        }

        return list;
    }

    @Override
    public List<Table> getAll() {
        List<Table> list = new ArrayList<>();
        String sql = "SELECT * FROM tables";

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
                rs.getInt("customerId"),
                rs.getInt("capacity"),
                TableStatus.valueOf(rs.getString("status"))
        );
    }
}