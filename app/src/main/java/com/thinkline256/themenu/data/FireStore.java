package com.thinkline256.themenu.data;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.thinkline256.themenu.data.models.Category;
import com.thinkline256.themenu.data.models.Order;
import com.thinkline256.themenu.data.models.RestaurantMenuItem;

import java.util.ArrayList;
import java.util.List;

public class FireStore implements DataSource {

    private CollectionReference menuDb = FirebaseFirestore.getInstance().collection("menu");
    private CollectionReference ordersDb = FirebaseFirestore.getInstance().collection("orders");

    @Override
    public void getCategories(ListCategoriesCallBacks callBacks) {

    }

    @Override
    public void getMenu(final ListMenuCallBacks callBacks) {
        menuDb.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<RestaurantMenuItem> menu = new ArrayList<>();
                    for (QueryDocumentSnapshot data : task.getResult()) {
                        menu.add(data.toObject(RestaurantMenuItem.class));
                    }

                    if (menu.size() > 0)
                        callBacks.onLoad(menu);
                    else
                        callBacks.onFail("No items found!");

                } else {
                    callBacks.onFail("No items found!");
                }
            }
        });
    }

    @Override
    public void getMenuByCategory(String category, final ListMenuCallBacks callBacks) {
        menuDb.whereEqualTo("category", category).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<RestaurantMenuItem> menu = new ArrayList<>();
                    for (QueryDocumentSnapshot data : task.getResult()) {
                        menu.add(data.toObject(RestaurantMenuItem.class));
                    }
                    if (menu.size() > 0)
                        callBacks.onLoad(menu);
                    else
                        callBacks.onFail("No items found!");
                } else {
                    callBacks.onFail("No items found!");
                }
            }
        });
    }

    @Override
    public void getOrders(final ListOrderCallBacks callBacks) {
        ordersDb.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Order> orders = new ArrayList<>();
                    for (QueryDocumentSnapshot data : task.getResult()) {
                        orders.add(data.toObject(Order.class));
                    }
                    if (orders.size() > 0)
                        callBacks.onLoad(orders);
                    else
                        callBacks.onFail("No orders found!");
                } else {
                    callBacks.onFail("No orders found");
                }
            }
        });
    }

    @Override
    public void addToOrder(RestaurantMenuItem item) {
        //Implemented in the repository
    }

    @Override
    public void removeFromOrders(RestaurantMenuItem item) {
        //Implemented in the repository
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
    public void addMenuItem(final RestaurantMenuItem item, final MenuCallback callback) {
        menuDb.document(item.getId()).set(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onSuccess(item);
                } else callback.onFail("Item was not added!");
            }
        });
    }

    @Override
    public void getMenuItem(String id, String category, final MenuCallback callback) {
        menuDb.document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    callback.onSuccess(task.getResult().toObject(RestaurantMenuItem.class));
                } else {
                    callback.onFail("Item not found!");
                }
            }
        });
    }

    @Override
    public void updateMenuItem(RestaurantMenuItem item) {
        menuDb.document(item.getId()).set(item);
    }

    @Override
    public void deleteMenuItem(RestaurantMenuItem id) {
        menuDb.document(id.getId()).delete();
    }

    @Override
    public void setOrderListener(OrderUpdateListener listener) {
        //Implemented in the repository
    }

    @Override
    public void addOrder(final Order order, final OrderCallback callback) {
        ordersDb.document(order.getId()).set(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    callback.onSuccess(order);
                } else {
                    callback.onFail("Order was not sent!");
                }
            }
        });
    }

    @Override
    public void deleteOrder(Order order) {
        ordersDb.document(order.getId()).delete();
    }

    @Override
    public void commitCurrentOrder(OrderCallback callback) {
        //Implemented in the repository
    }
}
