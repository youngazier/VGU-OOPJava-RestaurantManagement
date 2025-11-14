package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.User;
import java.util.List;

public interface UserDAO extends DAOInterface<User> {
    User getByUsername(String username);
}