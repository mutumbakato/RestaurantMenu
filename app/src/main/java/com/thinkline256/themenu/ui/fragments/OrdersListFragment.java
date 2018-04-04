package com.thinkline256.themenu.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thinkline256.themenu.R;
import com.thinkline256.themenu.data.DataSource;
import com.thinkline256.themenu.data.DataUtils;
import com.thinkline256.themenu.data.Repository;
import com.thinkline256.themenu.data.models.Order;
import com.thinkline256.themenu.ui.adapters.OrdersListAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrdersListFragment extends Fragment implements DataSource.ListOrderCallBacks {

    private OnListFragmentInteractionListener mListener;
    private OrdersListAdapter adapter;
    private Repository repository;
    private ProgressBar progress;
    private TextView errorTextView;

    public OrdersListFragment() {

    }

    @SuppressWarnings("unused")
    public static OrdersListFragment newInstance() {
        OrdersListFragment fragment = new OrdersListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new OrdersListAdapter(new ArrayList<Order>(), mListener);
        repository = DataUtils.getRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orderslist_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new OrdersListAdapter(new ArrayList<Order>(), mListener));

        errorTextView = view.findViewById(R.id.error_message);
        progress = view.findViewById(R.id.order_progress);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        repository.getOrders(this);
    }

    @Override
    public void onLoad(List<Order> orders) {
        progress.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        adapter.updateData(orders);
    }

    @Override
    public void onFail(String message) {
        progress.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(message);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Order item);
    }
}
