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
import com.thinkline256.themenu.ui.adapters.MenuItemsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MenuFragment extends Fragment implements DataSource.ListMenuCallBacks {

    public static final String ARG_TITLE = "title";
    public static final String ARG_CATEGORY_ID = "category_id";
    private OnListFragmentInteractionListener mListener;

    private String mTitle;
    private String mCategoryId;
    private MenuItemsAdapter adapter;
    private Repository repository;
    private ProgressBar progress;
    private TextView errorTextView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MenuFragment() {

    }

    @SuppressWarnings("unused")
    public static MenuFragment newInstance(String id, String title) {
        MenuFragment fragment = new MenuFragment();
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
        repository = DataUtils.getRepository();
        adapter = new MenuItemsAdapter(new ArrayList<RestaurantMenuItem>(),
                repository.getCurrentOrder(), mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menuitems_list, container, false);
        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        TextView vTitle = view.findViewById(R.id.sub_menu_title);

        progress = view.findViewById(R.id.menu_progress);
        errorTextView = view.findViewById(R.id.error_message);

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
        repository.getMenuByCategory(mCategoryId, this);
    }

    @Override
    public void onLoad(List<RestaurantMenuItem> menu) {
        errorTextView.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
        Collections.sort(menu, new Comparator<RestaurantMenuItem>() {
            @Override
            public int compare(RestaurantMenuItem item, RestaurantMenuItem t1) {
                return item.getName().compareTo(t1.getName());
            }
        });
        adapter.updateData(menu, repository.getCurrentOrder());
    }

    @Override
    public void onFail(String message) {
        errorTextView.setVisibility(View.VISIBLE);
        errorTextView.setText(message);
        progress.setVisibility(View.GONE);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(RestaurantMenuItem item, boolean isChecked);
    }

}
