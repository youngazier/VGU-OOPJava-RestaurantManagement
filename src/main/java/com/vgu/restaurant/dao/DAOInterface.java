package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.Order;

import java.util.List;
import java.util.Optional;

public interface DAOInterface<T> {
    boolean add(T t);
    boolean update(T t);
    boolean delete(T t);
    Optional<T> getById(int id);
    List<T> getAll();
}
