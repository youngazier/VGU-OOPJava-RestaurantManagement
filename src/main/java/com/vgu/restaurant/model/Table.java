package com.vgu.restaurant.model;

import com.vgu.restaurant.model.TableStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Table {
    private int id;
    private Integer customerId; // can be null
    private int capacity;
    private TableStatus status;
    
    public Table() {};

    public Table(int id, Integer customerId, int capacity, TableStatus status) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.capacity = capacity;
    }

    public Table(int customerId,int capacity, TableStatus status) {
        this.customerId = customerId;
        this.status = status;
        this.capacity = capacity;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getCapacity() {return capacity;}
    public void setCapacity(int capacity) {this.capacity = capacity;}

    public TableStatus getStatus() {return status;}
    public void setStatus(TableStatus status) {this.status = status;}
}
