package com.thinkline256.themenu.ui.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkline256.themenu.R;
import com.thinkline256.themenu.data.models.Order;
import com.thinkline256.themenu.data.models.RestaurantMenuItem;
import com.thinkline256.themenu.ui.fragments.OrdersListFragment.OnListFragmentInteractionListener;
import com.thinkline256.themenu.utils.Currency;
import com.thinkline256.themenu.utils.TimeUtils;

import java.util.List;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.ViewHolder> {

    private List<Order> mOrders;
    private final OnListFragmentInteractionListener mListener;

    public OrdersListAdapter(List<Order> items, OnListFragmentInteractionListener listener) {
        mOrders = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_orderslist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mOrders.get(position);
        holder.orderNumberView.setText(String.format("Order NO. %s",
                mOrders.get(position).getId().split("-")[1]));
        holder.priceView.setText(String.format("%s/=", String.valueOf(mOrders.get(position).getTotalCost())));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.itemsView.removeAllViews();
        for (RestaurantMenuItem item : holder.mItem.getItems()) {
            TextView textView = new TextView(holder.mView.getContext());
            textView.setText(String.format("%s - @ - %s", item.getName(),
                    Currency.format(item.getPrice())));
            holder.itemsView.addView(textView);
        }
        holder.timeView.setText(TimeUtils.formatTime(holder.mItem.getTime()));
        holder.statusView.setText(holder.mItem.getStatus());
        holder.statusView.setTextColor(holder.mItem.getStatus().equalsIgnoreCase("Pending") ?
                ContextCompat.getColor(holder.mView.getContext(), R.color.colorGreen) :
                ContextCompat.getColor(holder.mView.getContext(), R.color.colorYellowLite));
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView orderNumberView;
        public final TextView priceView;
        public final TextView statusView;
        public final TextView timeView;
        public final LinearLayout itemsView;
        public Order mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            orderNumberView = view.findViewById(R.id.id);
            priceView = view.findViewById(R.id.content);
            statusView = view.findViewById(R.id.order_status);
            timeView = view.findViewById(R.id.order_time);
            itemsView = view.findViewById(R.id.order_item_holder);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + priceView.getText() + "'";
        }

    }

    public void updateData(List<Order> orders) {
        mOrders = orders;
        notifyDataSetChanged();
    }
}
