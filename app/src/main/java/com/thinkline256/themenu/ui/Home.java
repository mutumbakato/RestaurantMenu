package com.thinkline256.themenu.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.thinkline256.themenu.R;
import com.thinkline256.themenu.data.DataUtils;
import com.thinkline256.themenu.data.Repository;
import com.thinkline256.themenu.data.models.Category;
import com.thinkline256.themenu.data.models.RestaurantMenuItem;
import com.thinkline256.themenu.ui.fragments.MainMenuFragment;
import com.thinkline256.themenu.ui.fragments.MenuItemsFragment;
import com.thinkline256.themenu.ui.fragments.OrderFragment;

public class Home extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        MainMenuFragment.OnListFragmentInteractionListener,
        MenuItemsFragment.OnListFragmentInteractionListener,
        OrderFragment.OnFragmentInteractionListener {

    private BottomSheetBehavior orderView;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = DataUtils.getRepository();
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        orderView = BottomSheetBehavior.from(findViewById(R.id.order_frame));

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        findViewById(R.id.order_frame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderView.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        orderView.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (orderView.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            orderView.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_menu) {

        } else if (id == R.id.nav_dashboard) {
            DashBoard.start(this);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Category item) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.menu_frame, MenuItemsFragment.newInstance(item.getId(), item.getName()))
                .addToBackStack("")
                .commit();
    }

    @Override
    public void onListFragmentInteraction(RestaurantMenuItem item, boolean isChecked) {
//        MenuFormActivity.start(this, item.getId(), item.getCategory());
        if (isChecked) {
            repository.addToOrder(item);
        } else {
            repository.removeFromOrders(item);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
