package com.vgu.restaurant.model;

import com.vgu.restaurant.model.OrderStatus;

import java.util.List;

public class Order {
    private int id;
    private int tableId;
    private Integer customerId;
    private List<OrderItem> items;
    private OrderStatus status;
    private String note;

    public Order() {};

    public Order(int id, int tableId, Integer customerId, List<OrderItem> items, OrderStatus status, String note) {
        this.id = id;
        this.tableId = tableId;
        this.customerId = customerId;
        this.items = items;
        this.status = status;
        this.note = note;
    }

    public Order(int tableId, Integer customerId, List<OrderItem> items, OrderStatus status, String note) {
        this.tableId = tableId;
        this.customerId = customerId;
        this.items = items;
        this.status = status;
        this.note = note;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getTableId() {return tableId;}
    public void setTableId(int tableId) {this.tableId = tableId;}

    public Integer getCustomerId() {return customerId;}
    public void setCustomerId(Integer customerId) {this.customerId = customerId;}

    public List<OrderItem> getItems() {return items;}
    public void setItems(List<OrderItem> items) {this.items = items;}

    public OrderStatus getStatus() {return status;}
    public void setStatus(OrderStatus status) {this.status = status;}

    public String getNote() {return note;}
    public void setNote(String note) {this.note = note;}
}
