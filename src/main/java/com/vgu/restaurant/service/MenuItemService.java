package com.vgu.restaurant.service;

import com.vgu.restaurant.model.MenuItem;
import java.util.List;

public interface MenuItemService {
    MenuItem getById(int id);
    List<MenuItem> getAll();
}
