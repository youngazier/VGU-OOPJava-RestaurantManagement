package com.vgu.restaurant.dao;

import com.vgu.restaurant.model.OrderItem;
import java.util.List;

public interface OrderItemDAO extends DAOInterface<OrderItem> {
    List<OrderItem> getByOrderId(int orderId) throws Exception;
}
