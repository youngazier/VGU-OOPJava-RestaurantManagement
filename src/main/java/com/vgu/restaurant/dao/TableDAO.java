package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.OrderStatus;
import com.vgu.restaurant.model.Table;
import com.vgu.restaurant.model.TableStatus;

import java.util.List;
import java.util.Optional;

public interface TableDAO extends DAOInterface<Table> {
    boolean updateStatus(int tableId, TableStatus newStatus);

    List<Table> getByStatus(TableStatus status);
    Optional<Table> getById(int id);
}
