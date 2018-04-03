package com.thinkline256.themenu.data;

import com.thinkline256.themenu.data.models.Category;
import com.thinkline256.themenu.data.models.RestaurantMenuItem;

/**
 * Created by cato on 3/28/18.
 */

public class FireStore implements DataSource {

    @Override
    public void getCategories(ListCategoriesCallBacks callBacks) {

    }

    @Override
    public void getMenu(String category, ListMenuCallBacks callBacks) {

    }

    @Override
    public void getOrders(ListOrderCallBacks callBacks) {

    }

    @Override
    public void addToOrder(RestaurantMenuItem item) {

    }

    @Override
    public void removeFromOrders(RestaurantMenuItem item) {

    }

    @Override
    public void addCategory(Category category, CategoryCallback callback) {
    }

    @Override
    public void updateCategory(Category category) {

    }

    @Override
    public void deleteCategory(Category category) {

    }

    @Override
    public void addMenuItem(RestaurantMenuItem item, MenuCallback callback) {
        callback.onSuccess(item);
    }

    @Override
    public void getMenuItem(String id, String category, MenuCallback callback) {

    }

    @Override
    public void updateMenuItem(RestaurantMenuItem item) {

    }

    @Override
    public void deleteMenuItem(RestaurantMenuItem id) {

    }

    @Override
    public void setOrderListener(OrderUpdateListener listener) {

    }

    @Override
    public void commitCurrentOrder() {

    }
}
