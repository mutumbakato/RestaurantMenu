package com.thinkline256.themenu.data.models;

/**
 * Created by cato on 3/28/18.
 */

public class MenuItem {

    private String name;
    private String description;
    private String category;
    private String type;
    private float price;
    private boolean available;

    public MenuItem() {

    }

    public MenuItem(String name, String description, String category, String type, float price, boolean available) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

}
