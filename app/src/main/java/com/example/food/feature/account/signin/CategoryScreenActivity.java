package com.example.food.feature.account.signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.food.Domain.CategoryDomain;
import com.example.food.R;
import com.example.food.viewmodel.CategoryListViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryScreenActivity extends AppCompatActivity {

    private RecyclerView rvCategory;
    private CategoryListViewModel categoryListViewModel;
    private List<CategoryDomain> categories;
    private TextView txtEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_screen);

        rvCategory = findViewById(R.id.rv_category_screen);
        txtEmpty = findViewById(R.id.txt_empty_category);
        categories = new ArrayList<>();
        rvCategory.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCategory.setLayoutManager(layoutManager);

        CategoryListAdapter adapter = new CategoryListAdapter(categoryListViewModel, new CategoryListAdapter.CategoryDiff());
        System.out.println("new adapter");
        rvCategory.setAdapter(adapter);
        System.out.println("set adapter");

        System.out.println("langnghe");
        categoryListViewModel = ViewModelProviders.of(this).get(CategoryListViewModel.class);
        categoryListViewModel.getCategoriesObserver().observe(this, new Observer<List<CategoryDomain>>() {
            @Override
            public void onChanged(List<CategoryDomain> categoryDomains) {
                if(categoryDomains!=null){
                    System.out.println("vao khac null" + categoryDomains.size());

                    adapter.submitList(categoryDomains);
//                    categories = categoryDomains;
//                    categories.forEach(categoryDomain -> {
//                        System.out.println(categoryDomain.toString());
//                    });
//                    adapter.notifyDataSetChanged();
                    txtEmpty.setVisibility(View.GONE);
                }else{
//                    txtEmpty.setVisibility(View.VISIBLE);
                    System.out.println("Vao null");
                }
            }
        });
        System.out.println("call api1");
        categoryListViewModel.makeApiCall();
        System.out.println("call api2");

    }
}