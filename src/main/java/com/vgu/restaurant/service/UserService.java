package com.vgu.restaurant.service;

import com.vgu.restaurant.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean register(User user);
    Optional<User> login(String username, String password);
    Optional<User> getById(int id);
    List<User> getAll();
}