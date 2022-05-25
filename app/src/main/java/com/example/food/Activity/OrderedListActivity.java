package com.example.food.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Adapter.OrderListAdapter;
import com.example.food.Api.Api;
import com.example.food.Domain.Order;
import com.example.food.Listener.OrdersResponseListener;
import com.example.food.R;
import com.example.food.databinding.ActivityOrderedListBinding;
import com.example.food.feature.adminhome.OrderDetailsFragment;
import com.example.food.feature.discountdetails.DiscountDetailsFragmentDirections;
import com.example.food.model.User;
import com.example.food.util.AppUtils;
import com.example.food.viewmodel.OrderViewModel;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import io.reactivex.internal.operators.observable.ObservableReduceSeedSingle;

public class OrderedListActivity extends AppCompatActivity implements OrderListAdapter.ClickOrderedListener, OrderDetailsFragment.ClickButton {
    Api api;
    ActivityOrderedListBinding binding;
    OrderListAdapter adapter;
    OrderViewModel orderViewModel;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_list);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_ordered_list);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        binding.rvListOrdered.setHasFixedSize(true);
        binding.rvListOrdered.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new OrderListAdapter(new ArrayList<>(), this);
        binding.rvListOrdered.setAdapter(adapter);
        api = new Api(this);
        User user = AppUtils.getAccount(getSharedPreferences(AppUtils.ACCOUNT, Context.MODE_PRIVATE));

        alertDialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.CustomProgressBarDialog).build();
        alertDialog.show();
        api.getOrdersByUser(ordersResponseListener,user.getId());
        binding.btnBackListOrdered.setOnClickListener(view -> navigationToHome(view));
    }

    private void navigationToHome(View view) {
        finish();
    }

    private  final OrdersResponseListener ordersResponseListener =new OrdersResponseListener() {
        @Override
        public void didFetch(ArrayList<Order> response, String message) {
            if(response!=null){
                alertDialog.dismiss();
                //response.get(0).getId();
                if(response.size()==0) binding.textViewEmptyList.setVisibility(View.VISIBLE);
                else{
                    binding.textViewEmptyList.setVisibility(View.GONE);
                    adapter.setData(response);

                }


            }
        }

        @Override
        public void didError(String message) {

        }
    };

    @Override
    public void onItemClicked(Order order) {
        // SHOW DETAIL FRAGMENT
        OrderDetailsFragment orderDetailsFragment = OrderDetailsFragment.newInstance(order, this);
        orderDetailsFragment.setCancelable(false);
        orderDetailsFragment.show(getSupportFragmentManager(), orderDetailsFragment.getTag());
    }

    @Override
    public void clickButtonAccept(int idOrder, String state) {
        // change huy order
        orderViewModel.callUpdateStateOrder2(idOrder, "Đã hủy")
        .subscribe(responseObjectResponse -> {
            if(responseObjectResponse.code()==200){
                adapter.changeItem(responseObjectResponse.body().getData());
            }else{

            }
        });
    }
}