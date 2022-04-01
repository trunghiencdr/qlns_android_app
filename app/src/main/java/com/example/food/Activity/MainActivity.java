package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food.Adapter.CategoryAdapter;
import com.example.food.Adapter.PopularAdapter;
import com.example.food.Api.ApiService;
import com.example.food.Domain.CategoryDomain;
import com.example.food.Domain.ProductDomain;
import com.example.food.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
private RecyclerView.Adapter adapter,adapter2;
private RecyclerView recyclerViewCategoryList,recyclerViewPopularList;
TextView textViewSeeAllCategory,textViewSeeAllProduct;
LinearLayout btnSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setControl();
        setEvent();
        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
    }

    private void setEvent() {
        textViewSeeAllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProductListActivity.class));
            }
        });
        textViewSeeAllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CategoryListActivity.class));
            }
        });
    }

    private void setControl() {
        textViewSeeAllCategory=findViewById(R.id.textViewSeeAllCategory);
        textViewSeeAllProduct=findViewById(R.id.textViewSeeAllProduct);
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton=findViewById(R.id.cart_btn);
        LinearLayout homeBtn=findViewById(R.id.homeBtn);
        btnSetting = findViewById(R.id.settingBtn);

        btnSetting.setOnClickListener(view -> startActivity(
                new Intent(MainActivity.this, SigninActivity.class)));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });

    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList=findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        final ArrayList<ProductDomain>[] productDomainList = new ArrayList[]{new ArrayList<>()};
        ApiService.apiService.getListProductDomain().enqueue(new Callback<ArrayList<ProductDomain>>() {
            @Override
            public void onResponse(Call<ArrayList<ProductDomain>> call, Response<ArrayList<ProductDomain>> response) {
                try {
                    if (response != null) {
                        productDomainList[0] =response.body();
                        adapter2 = new PopularAdapter(productDomainList[0]);
                        recyclerViewPopularList.setAdapter(adapter2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this,"Call api success"+productDomainList[0].get(4).getImages().get(0).getLink(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<ProductDomain>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Call api error"+t.toString(),Toast.LENGTH_SHORT).show();
                Log.d("zzz",t.toString());
            }
        });



    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList=findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
        ArrayList<CategoryDomain> categoryList=getListCategory();



    }
    private ArrayList<CategoryDomain> getListCategory(){
        //================
//        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        final ArrayList<CategoryDomain>[] categoryList = new ArrayList[]{new ArrayList<>()};
//        String url="http://192.168.1.10:8080/api/v1/Categories";
//
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    if (response != null) {
//                        //Log.d("aaa","1");
//                        for (int i = 0; i < response.length(); i++) {
//                            categoryList.add(new CategoryDomain(response.getJSONObject(i).getString("id").toString(), response.getJSONObject(i).getString("name").toString()));
//                        }
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this,"Something wrong"+error.toString(),Toast.LENGTH_SHORT).show();
//                Log.d("aaa",error.toString());
//            }
//        });

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0ODYzMTU3NywiZXhwIjoxNjQ4NzE3OTc3fQ.BqBLetK9eKm9a1AGTpJ6h2LpthSx1wcl3GGSxJ80P1Dq71hxFMKMZ6ndd8B-bnnoEXIcM2wOHsZin6nLg8lADw");
//                return headers;
//            }
//
//            @Override
//            public byte[] getBody() {
//                HashMap<String, String> body = new HashMap<String, String>();
//
//                return body.toString().getBytes();
//            }

//        queue.add(request);
        ApiService.apiService.getListCategoryDomain().enqueue(new Callback<ArrayList<CategoryDomain>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryDomain>> call, Response<ArrayList<CategoryDomain>> response) {
                try {
                    if (response != null) {
                        categoryList[0] =response.body();
                        adapter=new CategoryAdapter(categoryList[0]);
                        recyclerViewCategoryList.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this,"Call api success"+categoryList[0].size()+categoryList[0].get(0).getName(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<CategoryDomain>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Call api error",Toast.LENGTH_SHORT).show();
            }
        });
        return categoryList[0];
    }
}