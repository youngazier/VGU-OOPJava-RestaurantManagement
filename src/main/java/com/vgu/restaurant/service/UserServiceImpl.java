package com.vgu.restaurant.service;

import com.vgu.restaurant.dao.UserDAO;
import com.vgu.restaurant.dao.UserDAOImpl;
import com.vgu.restaurant.model.User;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = UserDAOImpl.getInstance();

    @Override
    public boolean register(User user) {
        // Check trung username
        if (userDAO.getByUsername(user.getUsername()).isPresent()) {
            System.out.println("Username is already in use");
            return false;
        }
        return userDAO.add(user);
    }

    @Override
    public Optional<User> login(String username, String password) {

        if (isBlank(username) || isBlank(password)) return Optional.empty();

        Optional<User> dbUser = userDAO.getByUsername(username);

        if (dbUser.isEmpty()) {
            System.out.println("Username not found");
            return Optional.empty();
        }

        if (!dbUser.get().getPassword().equals(password)) {
            System.out.println("Wrong password");
            return Optional.empty();
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
