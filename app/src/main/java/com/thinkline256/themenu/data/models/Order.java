package com.thinkline256.themenu.data.models;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cato on 3/28/18.
 */

public class Order {

    private String id;
    private String orderNUmber;
    private String status;
    private long time;
    private List<RestaurantMenuItem> items;

    public Order() {
    }

    public Order(String id, String orderNumber, String status, long time) {
        this.id = id;
        this.orderNUmber = orderNumber;
        this.status = status;
        this.time = time;
    }

    public String getOrderNUmber() {
        return orderNUmber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Exclude
    public float getTotalCost() {
        float total = 0;
        for (RestaurantMenuItem item : getItems()) {
            total += item.getTotal();
        }
        return total;
    }

    public String getId() {
        return id;
    }

    public List<RestaurantMenuItem> getItems() {
        if (items == null)
            items = new ArrayList<>();
        return items;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setItems(List<RestaurantMenuItem> items) {
        this.items = items;
    }

    public long getTime() {
        return time;
    }
}
