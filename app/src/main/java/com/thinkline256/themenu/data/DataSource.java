package com.thinkline256.themenu.data;

import com.thinkline256.themenu.data.models.Category;
import com.thinkline256.themenu.data.models.RestaurantMenuItem;
import com.thinkline256.themenu.data.models.Order;

import java.util.List;

/**
 * Created by cato on 3/27/18.
 */

public interface DataSource {

    interface MenuCallback {
        void onSuccess(RestaurantMenuItem restaurantMenuItem);

        void onFail(String message);
    }

    interface CategoryCallback {
        void onSuccess(Category category);

        void onFail(String message);
    }

    interface ListMenuCallBacks {
        void onLoad(List<RestaurantMenuItem> menu);

        void onFail(String message);
    }

    interface ListCategoriesCallBacks {
        void onLoad(List<Category> categories);

        void onFail(String message);
    }

    interface ListOrderCallBacks {
        void onLoad(List<Order> orders);

        void onFail(String message);
    }

    interface OrderUpdateListener {
        void onUpdate(List<Order> orders);
    }

    void getCategories(ListCategoriesCallBacks callBacks);

    void getMenu(String category, ListMenuCallBacks callBacks);

    void getOrders(ListOrderCallBacks callBacks);

    void addToOrder(RestaurantMenuItem item);

    void removeFromOrders(RestaurantMenuItem item);

    void addCategory(Category category, CategoryCallback callback);

    void updateCategory(Category category);

    void deleteCategory(Category category);

    void addMenuItem(RestaurantMenuItem item, MenuCallback callback);

    void getMenuItem(String id, String category, MenuCallback callback);

    void updateMenuItem(RestaurantMenuItem item);

    void deleteMenuItem(RestaurantMenuItem id);

    void setOrderListener(OrderUpdateListener listener);

    void commitCurrentOrder();

}
