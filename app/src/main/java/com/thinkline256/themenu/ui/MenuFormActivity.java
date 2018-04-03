package com.thinkline256.themenu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.thinkline256.themenu.R;
import com.thinkline256.themenu.data.DataSource;
import com.thinkline256.themenu.data.Repository;
import com.thinkline256.themenu.data.DataUtils;
import com.thinkline256.themenu.data.models.RestaurantMenuItem;
import com.thinkline256.themenu.utils.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MenuFormActivity extends AppCompatActivity implements DataSource.MenuCallback {

    private TextInputEditText vName;
    private TextInputEditText vPrice;
    private TextInputEditText vDescription;
    private TextInputEditText vType;
    private TextInputEditText vCategory;
    private AppCompatCheckBox vAvailable;

    private boolean isAvailable = false;
    private List<TextInputEditText> inputs;

    private Repository repository;
    private String mItemId;
    private String mCategoryId;
    private RestaurantMenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemId = getIntent().getStringExtra("id");
        mCategoryId = getIntent().getStringExtra("category");
        repository = DataUtils.getRepository();

        setContentView(R.layout.activity_menu_form);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu Item");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vName = findViewById(R.id.menu_name);
        vPrice = findViewById(R.id.menu_price);
        vDescription = findViewById(R.id.menu_description);
        vType = findViewById(R.id.menu_type);
        vCategory = findViewById(R.id.menu_category);
        vAvailable = findViewById(R.id.menu_available);

        vAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isAvailable = b;
            }
        });

        findViewById(R.id.menu_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });
        inputs = new ArrayList<>();
        inputs.add(vName);
        inputs.add(vPrice);
        inputs.add(vDescription);
        inputs.add(vType);
        inputs.add(vCategory);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mItemId != null) {
            repository.getMenuItem(mItemId, mCategoryId, this);
        }
    }

    private void saveItem() {

        String mName = vName.getText().toString().trim();
        String mPrice = vPrice.getText().toString().trim();
        String mDescription = vDescription.getText().toString().trim();
        String mType = vType.getText().toString().trim();
        String mCategory = vCategory.getText().toString().trim();

        if (formIsValid()) {
            if (isNew()) {
                RestaurantMenuItem restaurantMenuItem =
                        new RestaurantMenuItem(UUID.randomUUID().toString(), mName, mDescription, mCategory, mType, Float.parseFloat(mPrice), isAvailable);
                repository.addMenuItem(restaurantMenuItem, new DataSource.MenuCallback() {
                    @Override
                    public void onSuccess(RestaurantMenuItem item) {
                        Toast.makeText(MenuFormActivity.this, "Item saved successfully!", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFail(String message) {
                        Toast.makeText(MenuFormActivity.this, "Failed to save Item.", Toast.LENGTH_LONG).show();

                    }
                });
            } else {
                RestaurantMenuItem edited = new RestaurantMenuItem(item.getId(), mName, mDescription,
                        mCategory, mType, Float.parseFloat(mPrice), isAvailable);
                repository.updateMenuItem(edited);
                finish();
            }
        }
    }

    private boolean formIsValid() {
        for (TextInputEditText textInputEditText : inputs) {
            if (TextUtils.isEmpty(textInputEditText.getText().toString().trim())) {
                textInputEditText.setError("This field is required");
                return false;
            }
        }
        return true;
    }

    public static void start(Context context, String menu, String mCategory) {
        Intent starter = new Intent(context, MenuFormActivity.class);
        starter.putExtra("id", menu);
        starter.putExtra("category", mCategory);
        context.startActivity(starter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(RestaurantMenuItem restaurantMenuItem) {
        item = restaurantMenuItem;
        vName.setText(restaurantMenuItem.getName());
        vPrice.setText(Currency.format(restaurantMenuItem.getPrice()));
        vDescription.setText(restaurantMenuItem.getDescription());
        vType.setText(restaurantMenuItem.getType());
        vCategory.setText(restaurantMenuItem.getCategory());
        isAvailable = restaurantMenuItem.isAvailable();
        vAvailable.setChecked(isAvailable);
        mItemId = restaurantMenuItem.getId();
        mCategoryId = restaurantMenuItem.getCategory();
    }

    @Override
    public void onFail(String message) {

    }

    public boolean isNew() {
        return mItemId == null;
    }
}
