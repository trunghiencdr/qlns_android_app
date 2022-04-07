package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
    TextView ItemTotalFeeTxt,DeliveryFeeTxt,AllTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        setControl();
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycleViewCart.setLayoutManager(linearLayoutManager);
        api = new Api(CartListActivity.this);
        User user = AppUtils.getAccount(getSharedPreferences(AppUtils.ACCOUNT, Context.MODE_PRIVATE));
        api.getCartsByUserId(cartResponseListener,Integer.parseInt(user.getId().toString()));



    }

    private void setControl() {
        recycleViewCart=findViewById(R.id.recycleViewCart);
        ItemTotalFeeTxt=findViewById(R.id.ItemTotalFeeTxt);
        DeliveryFeeTxt=findViewById(R.id.DeliveryFeeTxt);
    }

    private final CartResponseListener cartResponseListener = new CartResponseListener() {
        @Override
        public void didFetch(ArrayList<Cart> response, String message) {
            if (response != null) {
                adapterCart = new CardListAdapter(response);
                recycleViewCart.setAdapter(adapterCart);

                float itemTotalFee=0;
                int deliveryFee=0;
                int sl=0;
                for (int i = 0; i < response.size(); i++) {
                    itemTotalFee+=response.get(i).getProductDomain().getPrice()*response.get(i).getQuantity();
                    sl+=response.get(i).getQuantity();
                }
                ItemTotalFeeTxt.setText(itemTotalFee+"");
                DeliveryFeeTxt.setText((sl*2)+"");
                float all=itemTotalFee+deliveryFee;
//                AllTxt.setText(all+"");
            }
        }

        @Override
        public void didError(String message) {
            Log.d("cart",message);
        }
    };
}