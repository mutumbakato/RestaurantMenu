package com.thinkline256.themenu.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkline256.themenu.R;
import com.thinkline256.themenu.data.models.RestaurantMenuItem;
import com.thinkline256.themenu.ui.fragments.MenuListFragment.OnListFragmentInteractionListener;
import com.thinkline256.themenu.utils.Currency;

import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {

    private List<RestaurantMenuItem> menuItems;
    private final OnListFragmentInteractionListener mListener;

    public MenuListAdapter(List<RestaurantMenuItem> items, OnListFragmentInteractionListener listener) {
        menuItems = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_menulist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = menuItems.get(position);
        holder.mIdView.setText(menuItems.get(position).getName());
        holder.mContentView.setText(Currency.format(menuItems.get(position).getPrice()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public RestaurantMenuItem mItem;

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

    public void updateData(List<RestaurantMenuItem> items) {
        menuItems = items;
        notifyDataSetChanged();
    }
}
