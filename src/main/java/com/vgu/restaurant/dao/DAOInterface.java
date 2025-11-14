package com.vgu.restaurant.dao;

import java.util.List;

public interface DAOInterface<T> {
    boolean add(T t);
    boolean update(T t);
    boolean delete(T t);
    T getById(int id);
    List<T> getAll();
}
