package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO extends DAOInterface<User> {
    Optional<User> getByUsername(String username);
}