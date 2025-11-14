package com.vgu.restaurant.model;

public class Customer extends User {
    public Customer() {
        super();
        setRole(Role.CUSTOMER);
    }

    public Customer(int id, String username, String password,
                    String fullName, String phone) {
        super(id, username, password, fullName, Role.CUSTOMER, phone);
    }
}
