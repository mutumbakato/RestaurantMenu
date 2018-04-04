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
import com.thinkline256.themenu.data.models.RestaurantMenuItem;
import com.thinkline256.themenu.ui.adapters.MenuListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MenuListFragment extends Fragment implements DataSource.ListMenuCallBacks {

    private OnListFragmentInteractionListener mListener;
    private MenuListAdapter adapter;
    private Repository repository;

    private TextView errorTextView;
    private ProgressBar progress;

    public MenuListFragment() {
    }

    @SuppressWarnings("unused")
    public static MenuListFragment newInstance() {
        MenuListFragment fragment = new MenuListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = DataUtils.getRepository();
        adapter = new MenuListAdapter(new ArrayList<RestaurantMenuItem>(), mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menulist_list, container, false);
        // Set the adapter
        Context context = view.getContext();

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        errorTextView = view.findViewById(R.id.error_message);
        progress = view.findViewById(R.id.menu_progress);
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
        repository.getMenu(this);
    }

    @Override
    public void onLoad(List<RestaurantMenuItem> menu) {
        progress.setVisibility(View.GONE);
        errorTextView.setVisibility(View.GONE);
        Collections.sort(menu, new Comparator<RestaurantMenuItem>() {
            @Override
            public int compare(RestaurantMenuItem item, RestaurantMenuItem t1) {
                return item.getName().compareTo(t1.getName());
            }
        });
        adapter.updateData(menu);
    }

    @Override
    public void onFail(String message) {
        progress.setVisibility(View.GONE);
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(message);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(RestaurantMenuItem item);
    }
}
