package com.vgu.restaurant.service;

import com.vgu.restaurant.dao.UserDAO;
import com.vgu.restaurant.dao.UserDAOImpl;
import com.vgu.restaurant.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final   UserDAO userDAO = new UserDAOImpl();

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
    public User getById(int id) {
        return userDAO.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

}
