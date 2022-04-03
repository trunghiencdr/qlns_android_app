package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.food.Adapter.CardListAdapter;
import com.example.food.Adapter.PopularAdapter;
import com.example.food.Api.ApiService;
import com.example.food.Domain.CartDomain;
import com.example.food.Domain.ProductDomain;
import com.example.food.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartListActivity extends AppCompatActivity {
    RecyclerView recycleViewCart;
    private RecyclerView.Adapter adapterCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        recyclerViewCart();

    }
    private void recyclerViewCart() {
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recycleViewCart=findViewById(R.id.recycleViewCart);
        recycleViewCart.setLayoutManager(linearLayoutManager);

        final ArrayList<CartDomain>[] cartDomainList = new ArrayList[]{new ArrayList<>()};
        ApiService.apiService.getListCartDomainFollowUserId(22).
                enqueue(new Callback<ArrayList<CartDomain>>() {
            @Override
            public void onResponse(Call<ArrayList<CartDomain>> call, Response<ArrayList<CartDomain>> response) {
                try {
                    if (response != null) {
                        cartDomainList[0] =response.body();
                        Log.d("cart",response.body().toString());
                        ArrayList<ProductDomain> productDomains= new ArrayList<>();
                        for (int i=0;i<cartDomainList[0].size();i++){
                            productDomains.add(cartDomainList[0].get(i).getProductDomain());
                            Log.d("cart",productDomains.toString());
                        }
                        adapterCart = new CardListAdapter(productDomains);
                        recycleViewCart.setAdapter(adapterCart);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CartDomain>> call, Throwable t) {

            }
        });


    }
}