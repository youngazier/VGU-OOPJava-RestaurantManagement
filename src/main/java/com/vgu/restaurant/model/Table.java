package com.vgu.restaurant.model;
import com.vgu.restaurant.model.TableStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Table {
    private int id;
    private int capacity;
    private TableStatus status;
    public Table() {};

    public Table(int id, int tableId,int capacity, TableStatus status) {
        this.id = id;
        this.status = status;
        this.capacity = capacity;
    }

    public Table(int tableId,int capacity, TableStatus status) {
        this.status = status;
        this.capacity = capacity;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getCapacity() {return capacity;}
    public void setCapacity(int capacity) {this.capacity = capacity;}

    public TableStatus getTableStatus() {return status;}
    public void setTableStatus(TableStatus tableStatus) {this.status = tableStatus;}
}
