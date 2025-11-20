package com.vgu.restaurant.service;

import com.vgu.restaurant.dao.TableDAO;
import com.vgu.restaurant.dao.TableDAOImpl;
import com.vgu.restaurant.model.Order;
import com.vgu.restaurant.model.OrderStatus;
import com.vgu.restaurant.model.Table;
import com.vgu.restaurant.model.TableStatus;

import java.util.List;
import java.util.Optional;

public class TableServiceImpl implements TableService {

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
    public List<Table> getByStatus(TableStatus status) {
        return tableDAO.getByStatus(status);
    }

    @Override
    public List<Table> getAll() {
        return tableDAO.getAll();
    }

    @Override
    public boolean update(Table table) {
        return tableDAO.update(table);
    }

    @Override
    public boolean updateStatus(int tableId, TableStatus status) {
        return tableDAO.updateStatus(tableId, status);
    }

    // chỉ xóa bàn khi ko có khách
    @Override
    public boolean delete(int tableId) {
        return tableDAO.getById(tableId)
                .filter(t -> t.getStatus() == TableStatus.AVAILABLE)
                .map(tableDAO::delete)
                .orElse(false);
    }
}