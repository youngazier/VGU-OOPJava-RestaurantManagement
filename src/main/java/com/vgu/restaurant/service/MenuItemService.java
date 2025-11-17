package com.vgu.restaurant.service;

import com.vgu.restaurant.model.MenuItem;
import java.util.List;
import java.util.Optional;

public interface MenuItemService {
    Optional<MenuItem> getById(int id);
    List<MenuItem> getAll();
}
