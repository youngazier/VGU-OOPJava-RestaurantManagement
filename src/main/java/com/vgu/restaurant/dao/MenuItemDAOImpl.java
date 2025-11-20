package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.MenuItem;
import com.vgu.restaurant.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemDAOImpl implements MenuItemDAO {

    private static MenuItemDAOImpl instance;
    private MenuItemDAOImpl() {}

    public static MenuItemDAOImpl getInstance() {
        if (instance == null) {
            instance = new MenuItemDAOImpl();
        }
        return instance;
    }

    @Override
    public boolean add(MenuItem item){
        String sql = "INSERT INTO MenuItem (name, description, imgUrl, cost, price, category, isAvailable) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection()){
            if (conn == null){
                System.out.println("addMenuItem: DB connection is null");
                return false;
            }

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getImgUrl());
            ps.setDouble(4, item.getCost());
            ps.setDouble(5, item.getPrice());
            ps.setString(6, item.getCategory());
            ps.setBoolean(7, item.isAvailable());

            int affected = ps.executeUpdate();
            if (affected == 0) return false;

            try (ResultSet key = ps.getGeneratedKeys()) {
                if (key.next()) {
                    item.setId(key.getInt(1));
                }
            }
            return true;

        } catch (Exception e) {
            System.out.println("addMenuItem error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(MenuItem item) {
        String sql = "UPDATE MenuItem SET name=?, description=?, imgUrl=?, cost=?, price=?, category=?, isAvailable=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            if (conn == null) {
                System.out.println("updateMenuItem: DB Connection is null");
                return false;
            }

            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setString(3, item.getImgUrl());
            ps.setDouble(4, item.getCost());
            ps.setDouble(5, item.getPrice());
            ps.setString(6, item.getCategory());
            ps.setBoolean(7, item.isAvailable());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("update(MenuItem) error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(MenuItem item) {
        String sql = "DELETE FROM MenuItem WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            if (conn == null) {
                System.out.println("deleteUser: DB Connection is null");
                return false;
            }

            ps.setInt(1, item.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("delete(MenuItem) error: " + e.getMessage());
            return false;
        }
    }


    public Optional<MenuItem> getById(int id) {
        String sql = "SELECT * FROM MenuItem WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            if (conn == null) {
                System.out.println("getMenuItemById: DB Connection is null");
                return Optional.empty();
            }

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }

        } catch (Exception e) {
            System.out.println("getMenuItemById error: " + e.getMessage());
        }
        return Optional.empty();
    }


    public List<MenuItem> getAll() {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM MenuItem";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("getAllMenuItem: DB Connection is null");
                return list;
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (Exception e) {
            System.out.println("getAllMenuItem error: " + e.getMessage());
        }
        return list;
    }

    public List<MenuItem> getByCategory(String category) {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM MenuItem WHERE category = ?";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("getByCategory: DB Connection is null");
                return list; // trả về list rỗng thay vì null
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, category);

            ResultSet rs = ps.executeQuery();

            // Duyệt tất cả kết quả
            while (rs.next()) {
                list.add(map(rs));   // thêm MenuItem vào list
            }

        } catch (Exception e) {
            System.out.println("getByCategory error: " + e.getMessage());
        }

        return list; // luôn trả về list (không bao giờ return null)
    }

    public List<MenuItem> getAvailableItems(boolean IsAvailable) {
        String sql = "SELECT * FROM MenuItem WHERE IsAvailable =?";
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("getMenuItembyIsAvailable: DB Connection is null");
                return null;
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, IsAvailable);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return null;
            }
        } catch (Exception e) {
            System.out.println("getMenuItembyIsAvailable error: " + e.getMessage());
        }
        return null;
    }

    public boolean setAvailability(int id, boolean available) {
        String sql = "UPDATE MenuItem SET isAvailable = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setBoolean(1, available);
            ps.setInt(2, id);
            
            return ps.executeUpdate() > 0;
        
        }
        catch (Exception e) {
            System.out.println("setAvailability error: " + e.getMessage());
            return false;
        }
    }

    private MenuItem map(ResultSet rs) throws Exception {
        return new MenuItem(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("imgUrl"),
                rs.getDouble("cost"),
                rs.getDouble("price"),
                rs.getString("category"),
                rs.getBoolean("isAvailable")
        );
    }
}