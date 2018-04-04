package com.thinkline256.themenu.data.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cato on 3/28/18.
 */

public class Order {

    private String id;
    private String orderNUmber;
    private String status;
    private float price;
    private List<RestaurantMenuItem> items;

    public Order() {

    }

    public Order(String id, String orderNumber, String status, float price) {
        this.id = id;
        this.orderNUmber = orderNumber;
        this.status = status;
        this.price = price;
    }

    public String getOrderNUmber() {
        return orderNUmber;
    }

    public String getStatus() {
        return status;
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
