package com.thinkline256.themenu.data.models;

/**
 * Created by cato on 3/28/18.
 */

public class RestaurantMenuItem {

    private String id;
    private String name;
    private String description;
    private String category;
    private String type;
    private float price;
    private boolean available;
    private float quantity;

    public RestaurantMenuItem() {

    }

    public RestaurantMenuItem(String id, String name, String description, String category, String type, float price, boolean available, float quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.type = type;
        this.price = price;
        this.available = available;
        this.quantity = quantity;
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


    public String getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }
    public float getTotal() {
        return price * quantity;
    }

    public float getQuantity() {
        return quantity;
    }
}
