package com.thinkline256.themenu.data;

import com.thinkline256.themenu.data.models.Category;
import com.thinkline256.themenu.data.models.MenuItem;
import com.thinkline256.themenu.data.models.OrderItem;

import java.util.List;

/**
 * Created by cato on 3/27/18.
 */

public interface DataSource {

    interface ListMenuCallBacks {
        void onLoad(List<MenuItem> menu);

        void onFail(String message);
    }

    interface ListCategoriesCallBacks {
        void onLoad(List<Category> categories);

        void onFail(String message);
    }

    interface ListOrderCallBacks {
        void onLoad(List<OrderItem> orders);

        void onFail(String message);
    }

    interface onOrderUpdateListener {
        void onUpdate(List<OrderItem> orderItems);
    }

    void getCategories(ListCategoriesCallBacks callBacks);

    void getMenu(ListMenuCallBacks callBacks);

    void getOrders(ListOrderCallBacks callBacks);

    void addToOrder(MenuItem item);

    void removeFromOrders(MenuItem item);

    void addMenuItem(MenuItem item);

    void addCategory(Category category);

    void updateMenuItem(MenuItem item);

    void deleteMenuItem();

    void notifyMenuUpdates(ListOrderCallBacks callBacks);

}
