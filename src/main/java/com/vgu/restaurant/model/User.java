package com.vgu.restaurant.model;

import com.vgu.restaurant.model.Role;

public abstract class User {
    private int id;
    private String username;
    private String password;
    private String fullName;
    private Role role;
    private String phone;

    // CONSTRUCTOR
    public User() {}

    // Use for retrieving from DB
    public User(int id, String username, String password, String fullName, Role role, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.phone = phone;
    }

    // Use for initializing a new obj
    public User(String username, String password, String fullName, Role role, String phone) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.phone = phone;
    }

    // GETTER, SETTER
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}

    public Role getRole() {return role;}
    public void setRole(Role role) {this.role = role;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

}