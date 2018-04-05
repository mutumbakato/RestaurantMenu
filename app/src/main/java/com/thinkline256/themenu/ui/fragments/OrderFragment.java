package com.thinkline256.themenu.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkline256.themenu.R;
import com.thinkline256.themenu.data.DataSource;
import com.thinkline256.themenu.data.DataUtils;
import com.thinkline256.themenu.data.Repository;
import com.thinkline256.themenu.data.models.Order;
import com.thinkline256.themenu.data.models.RestaurantMenuItem;
import com.thinkline256.themenu.utils.Currency;

import java.util.List;

public class OrderFragment extends Fragment implements DataSource.OrderUpdateListener {

    private OnFragmentInteractionListener mListener;
    private LinearLayout orderItemsHolder;
    private TextView orderPrice;
    private Repository repository;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = DataUtils.getRepository();
        repository.setOrderListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.order_layout, container, false);
        orderItemsHolder = view.findViewById(R.id.order_item_holder);
        orderPrice = view.findViewById(R.id.order_price);
        Button sendOrder = view.findViewById(R.id.send_order);
        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (repository.getCurrentOrder() == null) {
                    Snackbar.make(orderItemsHolder, "Please make an order first", Snackbar.LENGTH_LONG).show();
                    return;
                }
                final ProgressDialog progress = new ProgressDialog(getContext());
                progress.setMessage("Sending order...");
                progress.show();
                repository.commitCurrentOrder(new DataSource.OrderCallback() {
                    @Override
                    public void onSuccess(Order order) {
                        mListener.onFragmentInteraction(null);
                        progress.dismiss();
                        clearItems();
                        Snackbar.make(orderItemsHolder, "Your order has been sent", Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFail(String message) {
                        progress.dismiss();
                        Snackbar.make(orderItemsHolder, message, Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        repository.setOrderListener(null);
    }


    @Override
    public void onResume() {
        super.onResume();
        Order order = repository.getCurrentOrder();
        if (order != null) {
            showItems(order.getItems());
            showTotal(order);
        }
    }

    @Override
    public void onUpdate(Order order) {
        if (order != null) {
            showItems(order.getItems());
            showTotal(order);
        } else {
            clearItems();
        }
    }

    private void showTotal(Order order) {
        Log.d("OrderFragment", "showTotal: Order Items" + order.getItems().size());
        orderPrice.setText(String.format("%s/=", Currency.format(order.getTotalCost())));
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void showItems(List<RestaurantMenuItem> items) {
        orderItemsHolder.removeAllViews();
        orderPrice.setText("0/=");
        for (RestaurantMenuItem item : items) {
            TextView textView = new TextView(getContext());
            textView.setText(String.format("%s - @ - %s", item.getName(), Currency.format(item.getPrice())));
            orderItemsHolder.addView(textView);
        }
    }

    private void clearItems() {
        orderItemsHolder.removeAllViews();
        orderPrice.setText("0/=");
    }
}
