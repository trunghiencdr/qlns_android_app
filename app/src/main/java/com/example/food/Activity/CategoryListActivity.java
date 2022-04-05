package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.food.Adapter.CategoryAdapter;
import com.example.food.Adapter.CustomCategoryGridAdapter;
import com.example.food.Api.Api;
import com.example.food.Domain.Category;
import com.example.food.Listener.CategoryResponseListener;
import com.example.food.R;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListActivity extends AppCompatActivity {
    GridView gridView;
    Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setControl();

        api = new Api(CategoryListActivity.this);
        api.getCategories(categoryResponseListener);
    }

    private void setControl() {
        gridView= findViewById(R.id.gridViewCategory);
    }

    private final CategoryResponseListener categoryResponseListener = new CategoryResponseListener() {
        @Override
        public void didFetch(ArrayList<Category> response, String message) {
            gridView.setAdapter(new CustomCategoryGridAdapter(CategoryListActivity.this, response));
        }

        @Override
        public void didError(String message) {
            Log.d("api", message);
            Toast.makeText(CategoryListActivity.this, message, Toast.LENGTH_LONG).show();
        }
    };
}