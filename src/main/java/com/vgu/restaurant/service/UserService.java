package com.vgu.restaurant.service;

import com.vgu.restaurant.model.User;
import java.util.List;

public interface UserService {
    boolean register(User user);
    User login(String username, String password);
    User getById(int id);
    List<User> getAll();
}
