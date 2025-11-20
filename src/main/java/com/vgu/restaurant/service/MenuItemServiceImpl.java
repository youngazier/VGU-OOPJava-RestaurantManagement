package com.vgu.restaurant.service;

import com.vgu.restaurant.dao.MenuItemDAO;
import com.vgu.restaurant.dao.MenuItemDAOImpl;
import com.vgu.restaurant.model.MenuItem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemDAO menuItemDAO = MenuItemDAOImpl.getInstance();

    @Override
    public boolean add(MenuItem item) {
        if (!isValid(item)) return false;
        return menuItemDAO.add(item);
    }

    @Override
    public boolean update(MenuItem item) {
        if (!isValid(item)) return false;
        return menuItemDAO.update(item);
    }
    
    @Override
    public boolean setAvailability(int id, boolean available) {
        return menuItemDAO.setAvailability(id, available);
    }

    @Override
    public Optional<MenuItem> getById(int id){
        return menuItemDAO.getById(id);
    }

    @Override
    public List<MenuItem> getAll(){
        return menuItemDAO.getAll();
    }

    @Override
    public List<MenuItem> getByCategory(String category){
        return menuItemDAO.getByCategory(category);
    }

    @Override
    public List<MenuItem> getAvailableItems(boolean isAvailable){
        return menuItemDAO.getAvailableItems(isAvailable);
    }

    @Override
    public List<MenuItem> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return List.of();

        String lower = keyword.toLowerCase();
        return menuItemDAO.getAll().stream()
                .filter(i -> i.getName().toLowerCase().contains(lower)
                        || i.getDescription().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(int id) {
        Optional<MenuItem> item = menuItemDAO.getById(id);
        if (item.isEmpty()) return false;

        return menuItemDAO.delete(item.get());
    }

    private boolean isValid(MenuItem i) {
        return i != null
                && i.getName() != null
                && !i.getName().trim().isEmpty()
                && i.getPrice() >= 0
                && i.getCost() >= 0;
    }
}