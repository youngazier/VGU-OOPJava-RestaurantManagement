package com.vgu.restaurant.service;

import com.vgu.restaurant.dao.TableDAO;
import com.vgu.restaurant.dao.TableDAOImpl;
import com.vgu.restaurant.model.Order;
import com.vgu.restaurant.model.OrderStatus;
import com.vgu.restaurant.model.Table;
import com.vgu.restaurant.model.TableStatus;

import java.util.List;
import java.util.Optional;

public class TableServiceImpl {
    private final TableDAO tableDAO = TableDAOImpl.getInstance();

    @Override
    public boolean create(Table table) {
        return tableDAO.add(table);
    }

    @Override
    public Optional<Table> getById(int id) {
        return tableDAO.getById(id);
    }

    @Override
    public List<Order> getAll() {
        return tableDAO.getAll();
    }

    @Override
    public boolean updateStatus(int tableId, TableStatus status) {
        return tableDAO.updateStatus(tableId, status);
    }

    @Override
    public boolean update(Table table) {
        return tableDAO.update(table);
    }

    @Override
    public boolean delete(int tableId) {
        Optional<Table> table = tableDAO.getById(tableId);
        if (table == null) return false;
        return tableDAO.delete(table);
    }
}
