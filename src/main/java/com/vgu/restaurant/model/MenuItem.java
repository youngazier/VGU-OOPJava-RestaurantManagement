package com.vgu.restaurant.model;

public class MenuItem {
    private int id;
    private String name;
    private String description;
    private String imgUrl;
    private double cost; // Internal cost for the business
    private double price; // Selling price
    private String category;
    private boolean isAvailable;

    public MenuItem() {}

    public MenuItem(int id, String name, String description, String imgUrl, double cost, double price, String category, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.cost = cost;
        this.price = price;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public MenuItem(String name, String description, String imgUrl, double cost, double price, String category, boolean isAvailable) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.cost = cost;
        this.price = price;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getImgUrl() {return imgUrl;}
    public void setImgUrl(String imgUrl) {this.imgUrl = imgUrl;}

    public double getCost() {return cost;}
    public void setCost(double cost) {this.cost = cost;}

    public double getPrice() {return price;}
    public void setPrice(double price) {this.price = price;}

    public String getCategory() {return category;}
    public void setCategory(String category) {this.category = category;}

    public boolean isAvailable() {return isAvailable;}
    public void setAvailable(boolean available) {this.isAvailable = available;}
}