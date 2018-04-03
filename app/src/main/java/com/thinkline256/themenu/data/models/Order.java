package com.thinkline256.themenu.data.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cato on 3/28/18.
 */

public class Order {

    private String id;
    private String name;
    private String itemId;
    private float price;
    private List<RestaurantMenuItem> items;

    public Order() {

    }

    public Order(String id, String name, String itemId, float price) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public List<RestaurantMenuItem> getItems() {
        if (items == null)
            items = new ArrayList<>();
        return items;
    }

    public void setItems(List<RestaurantMenuItem> items) {
        this.items = items;
    }
}
