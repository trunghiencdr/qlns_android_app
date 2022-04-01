package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.food.Adapter.CategoryAdapter;
import com.example.food.Adapter.CustomProductGridAdapter;
import com.example.food.Api.ApiService;
import com.example.food.Domain.CategoryDomain;
import com.example.food.Domain.ProductDomain;
import com.example.food.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        List<ProductDomain> listProductDomain = getListData();
    }
    private List<ProductDomain> getListData() {
        final ArrayList<ProductDomain>[] productDomainList = new ArrayList[]{new ArrayList<>()};
        ApiService.apiService.getListProductDomain().enqueue(new Callback<ArrayList<ProductDomain>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductDomain>> call, Response<ArrayList<ProductDomain>> response) {
                try {
                    if (response != null) {
                        productDomainList[0] =response.body();
                        final GridView gridView = findViewById(R.id.gridViewProduct);
                        gridView.setAdapter(new CustomProductGridAdapter(ProductListActivity.this, productDomainList[0]));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(ProductListActivity.this,"Call api success"+productDomainList[0].get(4).getImages().get(0).getLink(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<ProductDomain>> call, Throwable t) {
                Toast.makeText(ProductListActivity.this,"Call api error"+t.toString(),Toast.LENGTH_SHORT).show();
                Log.d("zzz",t.toString());
            }
        });
        return productDomainList[0];
    }
}