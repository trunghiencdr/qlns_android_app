package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.food.Adapter.CustomCategoryGridAdapter;
import com.example.food.Adapter.CustomProductGridAdapter;
import com.example.food.Api.ApiService;
import com.example.food.Domain.CategoryDomain;
import com.example.food.Domain.ProductDomain;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        List<CategoryDomain> listCategoryDomain = getListData();


    }
    private List<CategoryDomain> getListData() {
        final ArrayList<CategoryDomain>[] categoryDomainList = new ArrayList[]{new ArrayList<>()};
        ApiService.apiService.getListCategoryDomain().enqueue(new Callback<ArrayList<CategoryDomain>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryDomain>> call, Response<ArrayList<CategoryDomain>> response) {
                try {
                    if (response != null) {
                        categoryDomainList[0] =response.body();
                        final GridView gridView = findViewById(R.id.gridViewCategory);
                        gridView.setAdapter(new CustomCategoryGridAdapter(CategoryListActivity.this, categoryDomainList[0]));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Toast.makeText(ProductListActivity.this,"Call api success"+productDomainList[0].get(4).getImages().get(0).getLink(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryDomain>> call, Throwable t) {
                Toast.makeText(CategoryListActivity.this,"Call api error"+t.toString(),Toast.LENGTH_SHORT).show();
                Log.d("zzz",t.toString());
            }
        });
        return categoryDomainList[0];
    }
}