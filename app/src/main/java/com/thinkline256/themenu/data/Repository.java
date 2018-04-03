package com.thinkline256.themenu.data;

import com.thinkline256.themenu.R;
import com.thinkline256.themenu.data.models.Category;
import com.thinkline256.themenu.data.models.RestaurantMenuItem;
import com.thinkline256.themenu.data.models.Order;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by cato on 3/27/18.
 */

public class Repository implements
        DataSource {

    private static Repository INSTANCE;
    private Map<String, Map<String, RestaurantMenuItem>> menuItemsCache;
    private Map<String, Category> categoryCache;
    private Map<String, Order> orders;
    private Order order;
    private DataSource remoteDataSource;
    private OrderUpdateListener mlistener;

    private Repository(DataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
        categoryCache = new LinkedHashMap<>();
        categoryCache.put("1", new Category("1", "Break First", "Served between 8:900am and 12:00am", R.drawable.coffee));
        categoryCache.put("2", new Category("2", "Buffet", "Served between 11:00am and 06:00pm", R.drawable.restaurant));
        categoryCache.put("3", new Category("3", "Dinner", "Served between 12:00am and 04:00pm", R.drawable.cutlery));
        categoryCache.put("4", new Category("4", "Drinks", "Served All day", R.drawable.cocktail));
        categoryCache.put("5", new Category("5", "Snacks", "Served All day", R.drawable.cake));
    }

    public static Repository getInstance(DataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getCategories(final ListCategoriesCallBacks callBacks) {
        if (categoryCache != null && categoryCache.size() > 0) {
            callBacks.onLoad(new ArrayList<>(categoryCache.values()));
            return;
        }
        remoteDataSource.getCategories(new ListCategoriesCallBacks() {
            @Override
            public void onLoad(List<Category> categories) {
                for (Category category : categories) {
                    addCategoryToCache(category);
                }
                callBacks.onLoad(categories);
            }

            @Override
            public void onFail(String message) {
                callBacks.onFail(message);
            }
        });
    }

    @Override
    public void getMenu(String category, final ListMenuCallBacks callBacks) {
        if (menuItemsCache != null && menuItemsCache.get(category) != null) {
            callBacks.onLoad(new ArrayList<>(menuItemsCache.get(category).values()));
            return;
        }
        remoteDataSource.getMenu(category, new ListMenuCallBacks() {
            @Override
            public void onLoad(List<RestaurantMenuItem> menu) {
                for (RestaurantMenuItem item : menu) {
                    addMenuItemToCache(item);
                }
                callBacks.onLoad(menu);
            }

            @Override
            public void onFail(String message) {
                callBacks.onFail(message);
            }
        });
    }

    @Override
    public void getOrders(ListOrderCallBacks callBacks) {

        remoteDataSource.getOrders(new ListOrderCallBacks() {
            @Override
            public void onLoad(List<Order> orders) {

            }

            @Override
            public void onFail(String message) {

            }
        });
    }

    @Override
    public void addToOrder(RestaurantMenuItem item) {
        if (order == null)
            order = new Order(UUID.randomUUID().toString(), "", "", 0);
        List<RestaurantMenuItem> i = order.getItems();
        i.add(item);
        order.setItems(i);
        if (orders == null)
            orders = new LinkedHashMap<>();
        orders.put(order.getId(), order);
        notifyOrder();
    }

    @Override
    public void removeFromOrders(RestaurantMenuItem item) {
        if (order == null) {
            notifyOrder();
            return;
        }
        List<RestaurantMenuItem> i = order.getItems();
        i.remove(item);
        order.setItems(i);
        if (orders == null)
            orders = new LinkedHashMap<>();
        orders.put(order.getId(), order);
        notifyOrder();
    }

    @Override
    public void addMenuItem(final RestaurantMenuItem item, final MenuCallback callback) {
        remoteDataSource.addMenuItem(item, new MenuCallback() {
            @Override
            public void onSuccess(RestaurantMenuItem restaurantMenuItem) {
                addMenuItemToCache(restaurantMenuItem);
                callback.onSuccess(item);
            }

            @Override
            public void onFail(String message) {
                callback.onFail(message);
            }
        });
    }

    @Override
    public void getMenuItem(String id, String category, final MenuCallback callback) {

        if (menuItemsCache != null && menuItemsCache.get(category) != null) {
            callback.onSuccess(menuItemsCache.get(category).get(id));
        } else {
            remoteDataSource.getMenuItem(id, category, new MenuCallback() {
                @Override
                public void onSuccess(RestaurantMenuItem restaurantMenuItem) {
                    callback.onSuccess(restaurantMenuItem);
                    addMenuItemToCache(restaurantMenuItem);
                }

                @Override
                public void onFail(String message) {
                    callback.onFail(message);
                }
            });
        }
    }

    @Override
    public void addCategory(Category category, final CategoryCallback categoryCallback) {
        remoteDataSource.addCategory(category, new CategoryCallback() {
            @Override
            public void onSuccess(Category category) {
                addCategoryToCache(category);
                categoryCallback.onSuccess(category);
            }

            @Override
            public void onFail(String message) {
                categoryCallback.onFail(message);
            }
        });
    }

    @Override
    public void updateCategory(Category category) {
        remoteDataSource.updateCategory(category);
        addCategoryToCache(category);
    }

    @Override
    public void deleteCategory(Category category) {
        remoteDataSource.deleteCategory(category);
        removeCategoryFromCache(category);
    }

    @Override
    public void updateMenuItem(RestaurantMenuItem item) {
        remoteDataSource.updateMenuItem(item);
        addMenuItemToCache(item);
    }

    @Override
    public void deleteMenuItem(RestaurantMenuItem item) {
        remoteDataSource.deleteMenuItem(item);
        removeMenuItemFromCache(item);
    }

    @Override
    public void setOrderListener(OrderUpdateListener listener) {
        mlistener = listener;
    }

    @Override
    public void commitCurrentOrder() {

    }

    private void addCategoryToCache(Category category) {
        if (categoryCache == null)
            categoryCache = new LinkedHashMap<>();
        categoryCache.put(category.getId(), category);
    }

    private void addMenuItemToCache(RestaurantMenuItem item) {

        if (menuItemsCache == null)
            menuItemsCache = new LinkedHashMap<>();

        if (menuItemsCache.get(item.getCategory()) == null)
            menuItemsCache.put(item.getCategory(), new LinkedHashMap<String, RestaurantMenuItem>());

        menuItemsCache.get(item.getCategory()).put(item.getId(), item);
    }

    private void removeMenuItemFromCache(RestaurantMenuItem item) {
        if (menuItemsCache != null && menuItemsCache.get(item.getCategory()) != null) {
            menuItemsCache.get(item.getCategory()).remove(item.getId());
        }
    }

    private void removeCategoryFromCache(Category category) {
        if (categoryCache != null) {
            categoryCache.remove(category.getId());
        }
    }

    private void addOrderToCache(Order item) {
        if (order == null) {
            orders.put(item.getId(), item);
        }
        notifyOrder();
    }

    private void removeOrderFromCache(Order item) {
        if (order != null) {
            orders.remove(item.getId());
        }
        notifyOrder();
    }

    private void notifyOrder() {
        if (mlistener != null) {
            mlistener.onUpdate(new ArrayList<>(orders.values()));
        }
    }

}
