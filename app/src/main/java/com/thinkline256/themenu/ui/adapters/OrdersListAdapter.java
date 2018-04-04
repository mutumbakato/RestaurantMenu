package com.thinkline256.themenu.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkline256.themenu.R;
import com.thinkline256.themenu.data.models.Order;
import com.thinkline256.themenu.ui.fragments.OrdersListFragment.OnListFragmentInteractionListener;

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
        holder.mIdView.setText(mOrders.get(position).getOrderNUmber());
        holder.mContentView.setText(String.valueOf(mOrders.get(position).getItems().size()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Order mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public void updateData(List<Order> orders) {
        mOrders = orders;
        notifyDataSetChanged();
    }
}
