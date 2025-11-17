package com.vgu.restaurant.service;

import com.vgu.restaurant.dao.UserDAO;
import com.vgu.restaurant.dao.UserDAOImpl;
import com.vgu.restaurant.model.User;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final   UserDAO userDAO = UserDAOImpl.getInstance();

    @Override
    public boolean register(User user) {
        // Check trung username
        if (userDAO.getByUsername(user.getUsername()) != null) {
            System.out.println("Username is already in use");
            return false;
        }
        return userDAO.add(user);
    }

    @Override
    public User login(String username, String password) {

        if (isBlank(username) || isBlank(password)) return null;

        User dbUser = userDAO.getByUsername(username);

        if (dbUser == null) {
            System.out.println("Username not found");
            return null;
        }

        if (!dbUser.getPassword().equals(password)) {
            System.out.println("Wrong password");
            return null;
        }
        return dbUser;
    }

    @Override
    public Optional<User> getById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    private boolean isValid(User user) {
        return user != null
                && !isBlank(user.getUsername())
                && !isBlank(user.getPassword())
                && !isBlank(user.getFullName())
                && user.getRole() != null;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
