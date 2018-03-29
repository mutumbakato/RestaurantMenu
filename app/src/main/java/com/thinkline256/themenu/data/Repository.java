package com.thinkline256.themenu.data;

import com.thinkline256.themenu.data.models.Category;
import com.thinkline256.themenu.data.models.MenuItem;
import com.thinkline256.themenu.data.models.OrderItem;
import java.util.List;
import java.util.Map;

/**
 * Created by cato on 3/27/18.
 */

public class Repository implements DataSource {

    private Map<String, List<MenuItem>> menuCache;
    private Map<String, List<Category>> categoryCache;
    private Map<String, List<OrderItem>> orderCache;

    private DataSource localDataSource;
    private DataSource remoteDataSource;

    @Override
    public void getCategories(ListCategoriesCallBacks callBacks) {

    }

    @Override
    public void getMenu(ListMenuCallBacks callBacks) {

    }

    @Override
    public void getOrders(ListOrderCallBacks callBacks) {

    }

    @Override
    public void addToOrder(MenuItem item) {

    }

    @Override
    public void removeFromOrders(MenuItem item) {

    }

    @Override
    public void addMenuItem(MenuItem item) {

    }

    @Override
    public void addCategory(Category category) {

    }

    @Override
    public void updateMenuItem(MenuItem item) {

    }

    @Override
    public void deleteMenuItem() {

    }

    @Override
    public void notifyMenuUpdates(ListOrderCallBacks callBacks) {

    }
}
