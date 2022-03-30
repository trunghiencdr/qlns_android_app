package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.food.Adapter.CategoryAdapter;
import com.example.food.Adapter.PopularAdapter;
import com.example.food.Domain.CategoryDomain;
import com.example.food.Domain.FoodDomain;
import com.example.food.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
private RecyclerView.Adapter adapter,adapter2;
private RecyclerView recyclerViewCategoryList,recyclerViewPopularList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton=findViewById(R.id.cart_btn);
        LinearLayout homeBtn=findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CardListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CardListActivity.class));
            }
        });
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewPopularList=findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> popularList=new ArrayList<>();
        popularList.add(new FoodDomain("Pe Pizza","pizza1","",9.75));
        popularList.add(new FoodDomain("Berger","burger","",7.75));
        popularList.add(new FoodDomain("Ve Pizza","pizza2","",8.75));

        adapter2 = new PopularAdapter(popularList);
        recyclerViewPopularList.setAdapter(adapter2);

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList=findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
//================
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        ArrayList<CategoryDomain> categoryList=new ArrayList<>();
        String url="http://192.168.1.10:8080/api/v1/Categories";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {


                    if (response != null) {
                        for (int i=0;i<response.length();i++){
                            categoryList.add(new CategoryDomain(response.getJSONObject(i).getString("id").toString(),response.getJSONObject(i).getString("name").toString()));
                        }
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Something wrong"+error.toString(),Toast.LENGTH_SHORT).show();
                Log.d("aaa",error.toString());
            }
        })
        {
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
        };
        queue.add(request);
        //=======================
//        ArrayList<CategoryDomain> categoryList=new ArrayList<>();
//        categoryList.add(new CategoryDomain("Pizza","cat_1"));
//        categoryList.add(new CategoryDomain("Burgur","cat_2"));
//        categoryList.add(new CategoryDomain("Hotdog","cat_3"));
//        categoryList.add(new CategoryDomain("Drink","cat_4"));
//        categoryList.add(new CategoryDomain("Donut","cat_5"));

        adapter=new CategoryAdapter(categoryList);
        recyclerViewCategoryList.setAdapter(adapter);

    }
}