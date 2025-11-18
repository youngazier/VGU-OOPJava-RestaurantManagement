package com.vgu.restaurant.model;

public class OrderItem {
    private int id;
    private int orderId;
    private int menuItemId;
    private double price;
    private int quantity;
    private String note;

    public OrderItem() {};

    // for db
    public OrderItem(int id, int orderId, int menuItemId, double price, int quantity, String note) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.price = price;
        this.quantity = quantity;
        this.note = note;
    }

    // for new obj
    public OrderItem(int orderId, int menuItemId, double price, int quantity, String note) {
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.price = price;
        this.quantity = quantity;
        this.note = note;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getOrderId() {return orderId;}
    public void setOrderId(int orderId) {this.orderId = orderId;}

    public int getMenuItemId() {return menuItemId;}
    public void setMenuItemId(int menuItemId) {this.menuItemId = menuItemId;}

    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public String getNote() {return note;}
    public void setNote(String note) {this.note = note;}
}