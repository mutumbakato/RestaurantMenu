package com.thinkline256.themenu.data.models;

/**
 * Created by cato on 3/28/18.
 */

public class OrderItem {

    private String name;
    private String itemId;
    private float price;

    public OrderItem() {

    }

    public OrderItem(String name, String itemId, float price) {
        this.name = name;
        this.itemId = itemId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getItemId() {
        return itemId;
    }

    public float getPrice() {
        return price;
    }

}
