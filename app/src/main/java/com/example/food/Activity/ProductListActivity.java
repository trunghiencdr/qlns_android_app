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
//        RequestQueue queue = Volley.newRequestQueue(ProductListActivity.this);
        final ArrayList<ProductDomain>[] productDomainList = new ArrayList[]{new ArrayList<>()};
//        String url="http://192.168.1.10:8080/api/v1/Products";
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    if (response != null) {
//                        for (int i=0;i<response.length();i++){
//                            productDomainList.add((ProductDomain)response.get(i));
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
//                Toast.makeText(ProductListActivity.this,"Something wrong"+error.toString(),Toast.LENGTH_SHORT).show();
//                Log.d("aaa",error.toString());
//            }
//        })
//        {
////            @Override
////            public Map<String, String> getHeaders() throws AuthFailureError {
////                HashMap<String, String> headers = new HashMap<String, String>();
////                headers.put("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0ODYzMTU3NywiZXhwIjoxNjQ4NzE3OTc3fQ.BqBLetK9eKm9a1AGTpJ6h2LpthSx1wcl3GGSxJ80P1Dq71hxFMKMZ6ndd8B-bnnoEXIcM2wOHsZin6nLg8lADw");
////                return headers;
////            }
////
////            @Override
////            public byte[] getBody() {
////                HashMap<String, String> body = new HashMap<String, String>();
////
////                return body.toString().getBytes();
////            }
//        };
//        queue.add(request);
//        List<ProductDomain> list = new ArrayList<ProductDomain>();
//        ProductDomain gridItem = new ProductDomain("a", "rau",1,"cai");
//        ProductDomain gridItem1 = new ProductDomain("b", "cai",2,"cai");
//        ProductDomain gridItem2 = new ProductDomain("c", "dau",3,"cai");
//        ProductDomain gridItem3 = new ProductDomain("d", "gao",4,"cai");
//        list.add(gridItem);
//        list.add(gridItem1);
//        list.add(gridItem2);
//        list.add(gridItem3);

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