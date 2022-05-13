package com.example.food.feature.adminhome;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Adapter.AdminOrderAdapter;
import com.example.food.Domain.Order;
import com.example.food.R;
import com.example.food.databinding.FragmentAdminOrderBinding;
import com.example.food.util.AppUtils;
import com.example.food.viewmodel.OrderViewModel;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;


public class AdminOrderFragment extends Fragment implements AdminOrderAdapter.ClickItem, OrderDetailsFragment.ClickButton {

    FragmentAdminOrderBinding binding;
    AdminOrderAdapter orderAdapter;
    OrderViewModel orderViewModel;
    String datePattern = "dd-MM-yyyy";
    String[] orderTimeFilter;
    String[] orderStateFilter;
    String startDate;
    String endDate;
    String orderState;
    BottomAppBar bottomAppBar;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setControls();
        setEvents();

        setData();
    }

    private void setData() {
        startDate = AppUtils.formatDate(new Date(), datePattern);
        endDate = AppUtils.formatDate(new Date(), datePattern);
        orderState = "chua duyet";
        orderViewModel.callOrdersByStateAndCreateAtBetween(
                orderState,
                startDate,
                endDate);
        orderViewModel.getData().observe(requireActivity(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                orderAdapter.submitList(orders);
            }
        });
    }

    private void setEvents() {
        binding.chipGroupOrderTimeFilter.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.chip_today_order_time_filter:
                        startDate = AppUtils.formatDate(new Date(), datePattern);
                        endDate = AppUtils.formatDate(new Date(), datePattern);
                        break;
                    case R.id.chip_week_order_time_filter:
                        startDate = AppUtils.getFirstDayOfWeekNow();
                        endDate = AppUtils.getLastDayOfWeekNow();
                        break;
                    case R.id.chip_month_time_order_filter:
                        startDate = AppUtils.getFirstDayOfMonthNow();
                        endDate = AppUtils.getLastDayOfMonthNow();

                        break;

                }
//                Log.d("AAA","start date:" + startDate + "-endDate:" + endDate);
                orderViewModel.callOrdersByStateAndCreateAtBetween(
                        orderState,
                        startDate,
                        endDate);
            }
        });

        binding.chipGroupOrderStateFilter.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.chip_unapproved_state_order:
                        orderState = AppUtils.orderState[0];// Đã giao
                        break;
                    case R.id.chip_approved_state_order:
                        orderState = AppUtils.orderState[1];// Đang giao
                        break;
                    case R.id.chip_shiped_state_order:
                        orderState = AppUtils.orderState[2];// Đang giao
                        break;

                }
                orderViewModel.callOrdersByStateAndCreateAtBetween(
                        orderState,
                        startDate,
                        endDate);
            }
        });

        binding.recyclerViewOrdersState.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * Callback method to be invoked when the RecyclerView has been scrolled. This will be
             * called after the scroll has completed.
             * <p>
             * This callback will also be called if visible item range changes after a layout
             * calculation. In that case, dx and dy will be 0.
             *
             * @param recyclerView The RecyclerView which scrolled.
             * @param dx           The amount of horizontal scroll.
             * @param dy           The amount of vertical scroll.
             */
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy < 0){
                    bottomAppBar.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.VISIBLE);
                }else{
                    bottomAppBar.setVisibility(View.GONE);
                    fab.setVisibility(View.GONE);
                }
            }
        });

    }

    private void setControls() {

        bottomAppBar = requireActivity().findViewById(R.id.bottom_bar);
        fab = requireActivity().findViewById(R.id.fab_cart);


        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        orderAdapter = new AdminOrderAdapter(Order.itemCallback, this);
        binding.recyclerViewOrdersState.setLayoutManager(
                new LinearLayoutManager(requireContext(),
                        RecyclerView.VERTICAL, false));
        binding.recyclerViewOrdersState.addItemDecoration(
                new DividerItemDecoration(binding.recyclerViewOrdersState.getContext(),
                        DividerItemDecoration.VERTICAL));
        binding.recyclerViewOrdersState.setAdapter(orderAdapter);

        // get filter
        orderTimeFilter = getResources().getStringArray(R.array.ORDER_TIME_FILTER);
        orderStateFilter = getResources().getStringArray(R.array.ORDER_STATE_FILTER);

        setChipFilter();


    }

    private void setChipFilter() {

    }

    private void getFilter(){

    }

    @Override
    public void showDetailsOrder(Order order) {
        OrderDetailsFragment orderDetailsFragment = OrderDetailsFragment.newInstance(order, this);
        orderDetailsFragment.setCancelable(false);
        orderDetailsFragment.show(requireActivity().getSupportFragmentManager(), orderDetailsFragment.getTag());
    }


    @Override
    public void clickButtonAccept(int idOrder, String state) {
        Toast.makeText(requireContext(), "Click accept button order", Toast.LENGTH_SHORT).show();
        orderViewModel.callUpdateStateOrder(idOrder, state);
    }
}