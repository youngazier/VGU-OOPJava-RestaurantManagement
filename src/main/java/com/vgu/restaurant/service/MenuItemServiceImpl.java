package com.vgu.restaurant.service;

import com.vgu.restaurant.dao.MenuItemDAO;
import com.vgu.restaurant.dao.MenuItemDAOImpl;
import com.vgu.restaurant.model.MenuItem;

import java.util.List;
import java.util.Optional;

public class MenuItemServiceImpl implements MenuItemService{
    private final MenuItemDAO menuitemDAO = new MenuItemDAOImpl();

    public Optional<MenuItem> getById(int id){
        return menuitemDAO.getById(id);
    }
    public List<MenuItem> getAll(){
        return menuitemDAO.getAll();
    }
}
