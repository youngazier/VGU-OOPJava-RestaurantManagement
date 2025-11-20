package com.vgu.restaurant.service;

import com.vgu.restaurant.model.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {

    boolean add(MenuItem item);

    boolean update(MenuItem item);
    boolean setAvailability(int id, boolean available);
    
    Optional<MenuItem> getById(int id);
    List<MenuItem> getAll();
    List<MenuItem> getByCategory(String category);
    List<MenuItem> getAvailableItems(boolean isAvailable);
    List<MenuItem> search(String keyword);

    boolean delete(int id);
}
