package com.thinkline256.themenu.data;

import android.view.MenuItem;
import com.thinkline256.themenu.data.models.OrderItem;
import java.util.List;

/**
 * Created by cato on 3/27/18.
 */

public interface DataSource {

    interface listMenuCallBacks {
        void onLoad(List<MenuItem> items);
        void onFaile(String message);
    }

    interface orderUpdateListener {
        void onUpdate(List<OrderItem> orderItems);
    }

}
