package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.MenuItem;
import java.util.List;


public interface MenuItemDAO extends DAOInterface<MenuItem> {
    List<MenuItem> getByCategory(String category);
    List<MenuItem> getAvailableItems(boolean isAvailable);
    boolean setAvailability(int id, boolean available);
}