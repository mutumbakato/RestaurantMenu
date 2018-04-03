package com.thinkline256.themenu.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkline256.themenu.R;
import com.thinkline256.themenu.data.DataSource;
import com.thinkline256.themenu.data.Repository;
import com.thinkline256.themenu.data.DataUtils;
import com.thinkline256.themenu.data.models.RestaurantMenuItem;
import com.thinkline256.themenu.ui.adapters.MenuItemsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MenuItemsFragment extends Fragment implements DataSource.ListMenuCallBacks {

    public static final String ARG_TITLE = "title";
    public static final String ARG_CATEGORY_ID = "category_id";
    private OnListFragmentInteractionListener mListener;

    private TextView vTitle;
    private String mTitle;
    private String mCategoryId;
    private MenuItemsAdapter adapter;
    private Repository repository;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MenuItemsFragment() {

    }

    @SuppressWarnings("unused")
    public static MenuItemsFragment newInstance(String id, String title) {
        MenuItemsFragment fragment = new MenuItemsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY_ID, id);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoryId = getArguments().getString(ARG_CATEGORY_ID);
            mTitle = getArguments().getString(ARG_TITLE);
        }
        adapter = new MenuItemsAdapter(new ArrayList<RestaurantMenuItem>(), mListener);
        repository = DataUtils.getRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menuitems_list, container, false);
        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        vTitle = view.findViewById(R.id.sub_menu_title);
        vTitle.setText(mTitle);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
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
        repository.getMenu(mCategoryId, this);
    }

    @Override
    public void onLoad(List<RestaurantMenuItem> menu) {
        adapter.updateData(menu);
    }

    @Override
    public void onFail(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(RestaurantMenuItem item, boolean isChecked);
    }

}
