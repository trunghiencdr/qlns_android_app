package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food.Adapter.CardListAdapter;
import com.example.food.Api.Api;
import com.example.food.Domain.Cart;
import com.example.food.Domain.Order;
import com.example.food.Domain.Product;
import com.example.food.Domain.Response.CartResponse;
import com.example.food.Domain.Response.OrderResponse;
import com.example.food.Listener.CartResponseListener;
import com.example.food.Listener.InsertCartResponseListener;
import com.example.food.Listener.InsertOrderResponseListener;
import com.example.food.R;
import com.example.food.dto.CartDTO;
import com.example.food.model.User;
import com.example.food.util.AppUtils;

import java.util.ArrayList;
import java.util.Date;

public class CartListActivity extends AppCompatActivity {
    RecyclerView recycleViewCart;
    private RecyclerView.Adapter adapterCart;
    Api api;
    TextView ItemTotalFeeTxt,DeliveryFeeTxt,AllTxt,btnCheckOut;
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

        setEvent();


    }

    private void setEvent() {
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = AppUtils.getAccount(getSharedPreferences(AppUtils.ACCOUNT, Context.MODE_PRIVATE));
                Order order = new Order(0,user,null,null,"Chua duyet");
                api = new Api(CartListActivity.this);
                api.insertOrder(insertOrderResponseListener,order);
            }
        });
    }

    private void setControl() {
        recycleViewCart=findViewById(R.id.recycleViewCart);
        ItemTotalFeeTxt=findViewById(R.id.ItemTotalFeeTxt);
        DeliveryFeeTxt=findViewById(R.id.DeliveryFeeTxt);
        btnCheckOut=findViewById(R.id.btnCheckOut);
    }
    private final InsertOrderResponseListener insertOrderResponseListener=new InsertOrderResponseListener() {
        @Override
        public void didFetch(OrderResponse response, String message) {
            Toast.makeText(CartListActivity.this, "Call api success"+message.toString(),Toast.LENGTH_SHORT).show();
            Log.d("success",message.toString());
        }

        @Override
        public void didError(String message) {
            Toast.makeText(CartListActivity.this,"Call api error"+message.toString(),Toast.LENGTH_SHORT).show();
            Log.d("zzz",message.toString());
        }
    };
//        @Override
//        public void didFetch(CartResponse response, String message) {
//            Toast.makeText(CartListActivity.this, "Call api success"+message.toString(),Toast.LENGTH_SHORT).show();
//            Log.d("success",message.toString());
//        }
//
//        @Override
//        public void didError(String message) {
//            Toast.makeText(CartListActivity.this,"Call api error"+message.toString(),Toast.LENGTH_SHORT).show();
//            Log.d("zzz",message.toString());
//        }


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