package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    // Singleton Pattern
    private static UserDAOImpl instance;
    private UserDAOImpl() {}

    public static UserDAOImpl getInstance() {
        if (instance == null) {
            instance = new UserDAOImpl();
        }
        return instance;
    }

    @Override
    public boolean add(User user) {
        String sql = "INSERT INTO users (username, password, fullName, role, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("addUser: DB Connection is null");
                return false;
            }

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getRole().toString());
            ps.setString(5, user.getPhone());

            int affected = ps.executeUpdate();
            if (affected == 0) return false;

            // Lấy id từ DB
            try (ResultSet key = ps.getGeneratedKeys()) {
                if (key.next()) {
                    user.setId(key.getInt(1));
                }
            }
            return true;

        } catch (Exception e) {
            System.out.println("addUser error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        String sql = "UPDATE users SET password=?, fullName=?, role=?, phone=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            if (conn == null) {
                System.out.println("updateUser: DB Connection is null");
                return false;
            }

            ps.setString(1, user.getPassword());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getRole().name());
            ps.setString(4, user.getPhone());
            ps.setInt(5, user.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("update(User) error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(User user) {
        String sql = "DELETE FROM users WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            if (conn == null) {
                System.out.println("deleteUser: DB Connection is null");
                return false;
            }

            ps.setInt(1, user.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("delete(User) error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            
            if (conn == null) {
                System.out.println("getAllUsers: DB Connection is null");
                return list;
            }

            while (rs.next()) {
                list.add(map(rs));
            }
        } catch (Exception e) {
            System.out.println("getAllUsers error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public Optional<User> getById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            if (conn == null) {
                System.out.println("getUserById: DB Connection is null");
                return Optional.empty();
            }

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }

        } catch (Exception e) {
            System.out.println("getUserById error: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> getByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.out.println("getUserByUsername: DB Connection is null");
                return Optional.empty();
            }

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }

        } catch (Exception e) {
            System.out.println("getUserByUsername error: " + e.getMessage());
        }
        return Optional.empty();
    }

    private User map(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String fullName = rs.getString("fullName");
        String phone = rs.getString("phone");
        Role role = Role.valueOf(rs.getString("role"));

        if (role == Role.CUSTOMER) {
            return new Customer(id, username, password, fullName, phone);
        }

        return new Employee(id, username, password, fullName, role, phone);
    }
}