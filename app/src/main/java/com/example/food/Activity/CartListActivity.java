package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.food.Adapter.CardListAdapter;
import com.example.food.Api.Api;
import com.example.food.Domain.Cart;
import com.example.food.Domain.Product;
import com.example.food.Listener.CartResponseListener;
import com.example.food.R;
import com.example.food.model.User;
import com.example.food.util.AppUtils;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity {
    RecyclerView recycleViewCart;
    private RecyclerView.Adapter adapterCart;
    Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycleViewCart=findViewById(R.id.recycleViewCart);
        recycleViewCart.setLayoutManager(linearLayoutManager);
        api = new Api(CartListActivity.this);
        User user = AppUtils.getAccount(getSharedPreferences(AppUtils.ACCOUNT, Context.MODE_PRIVATE));
        api.getCartsByUserId(cartResponseListener,Integer.parseInt(user.getId().toString()));

    }
    private final CartResponseListener cartResponseListener = new CartResponseListener() {
        @Override
        public void didFetch(ArrayList<Cart> response, String message) {
            if (response != null) {
                ArrayList<Product> products = new ArrayList<>();
                for (int i=0;i<response.size();i++){
                    products.add(response.get(i).getProductDomain());
                    Log.d("cart", products.toString());
                }
                adapterCart = new CardListAdapter(products);
                recycleViewCart.setAdapter(adapterCart);
            }
        }

        @Override
        public void didError(String message) {
            Log.d("cart",message);
        }
    };
}