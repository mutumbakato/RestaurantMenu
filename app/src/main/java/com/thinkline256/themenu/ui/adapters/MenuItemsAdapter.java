package com.thinkline256.themenu.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.thinkline256.themenu.R;
import com.thinkline256.themenu.data.models.Order;
import com.thinkline256.themenu.data.models.RestaurantMenuItem;
import com.thinkline256.themenu.ui.fragments.MenuFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.ViewHolder> {

    private List<RestaurantMenuItem> mItems;
    private final OnListFragmentInteractionListener mListener;
    private Order mOrder;

    public MenuItemsAdapter(List<RestaurantMenuItem> items, Order order, OnListFragmentInteractionListener listener) {
        mItems = items;
        mListener = listener;
        mOrder = order;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_menuitems, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mItems.get(position);
        holder.mNameView.setText(mItems.get(position).getName());
        holder.mPriceView.setText(com.thinkline256.themenu.utils.Currency.format(mItems.get(position).getPrice()));
        holder.mCheckBox.setChecked(mItems.get(position).isAvailable());
        if (mOrder != null)
            holder.isChecked = mOrder.getItems().contains(mItems.get(position));
        holder.mCheckBox.setChecked(holder.isChecked);
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    holder.isChecked = !holder.isChecked;
                    holder.mCheckBox.setChecked(holder.isChecked);
                    mListener.onListFragmentInteraction(holder.mItem, holder.isChecked);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mNameView;
        public final TextView mPriceView;
        public final CheckBox mCheckBox;
        public RestaurantMenuItem mItem;
        public boolean isChecked = false;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.item_name);
            mPriceView = (TextView) view.findViewById(R.id.item_price);
            mCheckBox = view.findViewById(R.id.item_check);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }

    public void updateData(List<RestaurantMenuItem> items, Order order) {
        mItems = items;
        mOrder = order;
        notifyDataSetChanged();
    }

}
