package com.vgu.restaurant.model;

public enum Role {
    CUSTOMER,
    WAITER,
    CHEF,
    MANAGER;

    public static Role fromString(String s) {
        if (s == null) return CUSTOMER;

        try {
            return Role.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CUSTOMER;
        }
    }
}