package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Adapter.OrderListAdapter;
import com.example.food.Api.Api;
import com.example.food.Domain.Order;
import com.example.food.Listener.OrdersResponseListener;
import com.example.food.R;
import com.example.food.databinding.ActivityOrderedListBinding;
import com.example.food.model.User;
import com.example.food.util.AppUtils;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

public class OrderedListActivity extends AppCompatActivity {
    Api api;
    ActivityOrderedListBinding binding;
    OrderListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_list);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_ordered_list);

        binding.rvListOrdered.setHasFixedSize(true);
        binding.rvListOrdered.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        api = new Api(this);
        User user = AppUtils.getAccount(getSharedPreferences(AppUtils.ACCOUNT, Context.MODE_PRIVATE));
        api.getOrdersByUser(ordersResponseListener,user.getId());
    }

    private  final OrdersResponseListener ordersResponseListener =new OrdersResponseListener() {
        @Override
        public void didFetch(ArrayList<Order> response, String message) {
            if(response!=null){
                response.get(0).getId();

                adapter = new OrderListAdapter(response);
                binding.rvListOrdered.setAdapter(adapter);

            }
        }

        @Override
        public void didError(String message) {

        }
    };
}