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

import com.thinkline256.themenu.R;
import com.thinkline256.themenu.data.DataSource;
import com.thinkline256.themenu.data.Repository;
import com.thinkline256.themenu.data.DataUtils;
import com.thinkline256.themenu.data.models.Category;
import com.thinkline256.themenu.ui.adapters.MainMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MenuCategoryFragment extends Fragment implements DataSource.ListCategoriesCallBacks {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private OnListFragmentInteractionListener mListener;
    private MainMenuAdapter adapter;
    private Repository repo;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public MenuCategoryFragment() {

    }

    @SuppressWarnings("unused")
    public static MenuCategoryFragment newInstance(int columnCount) {
        MenuCategoryFragment fragment = new MenuCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MainMenuAdapter(new ArrayList<Category>(), mListener);
        repo = DataUtils.getRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainmenu_list, container, false);
        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        repo.getCategories(this);
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
    public void onLoad(List<Category> categories) {
        adapter.updateData(categories);
    }

    @Override
    public void onFail(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Category item);
    }
}
