package com.vgu.restaurant.model;

public class Employee extends User {
    public Employee() {
        super();
    }

    public Employee(String username, String password, String fullName, Role role, String phone) {
        super(username, password, fullName, role, phone);
    }

    public Employee(int id, String username, String password, String fullName, Role role, String phone) {
        super(id, username, password, fullName, role, phone);
    }
}
