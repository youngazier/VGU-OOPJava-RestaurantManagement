package com.vgu.restaurant.service;

import com.vgu.restaurant.model.Table;
import com.vgu.restaurant.model.TableStatus;

import java.util.List;
import java.util.Optional;

public interface TableService {
    boolean create(Table table);

    Optional<Table> getById(int id);
    List<Table> getByStatus(TableStatus status);
    List<Table> getAll();

    boolean update(Table table);
    boolean updateStatus(int tableId, TableStatus status);

    boolean delete(int tableId);
}
