package com.example.food.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.food.Domain.Order;
import com.example.food.network.RetroInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderViewModel extends AndroidViewModel {

    MutableLiveData<List<Order>> data;
    OrderRepository repository;
    Application application;
    public OrderViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        data = new MutableLiveData<>();
        repository = RetroInstance.getRetrofitClient().create(OrderRepository.class);
    }

    public MutableLiveData<List<Order>> getData() {
        return data;
    }

    public void setData(MutableLiveData<List<Order>> data) {
        this.data = data;
    }

    @SuppressLint("CheckResult")
    public void callOrdersListByState(String state){
        repository.getOrdersByState(state)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderResponseResponse -> {
                    if(orderResponseResponse.code()==200){
                        data.setValue(orderResponseResponse.body().getData());
                    }else{
                        Toast.makeText(application, orderResponseResponse.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> Toast.makeText(application, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }

    @SuppressLint("CheckResult")
    public void callOrdersByStateAndCreateAtBetween(String state, String startDate, String endDate){
        repository.getOrdersByStateAndCreateAtBetween(state, startDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderResponseResponse -> {
                    if(orderResponseResponse.code()==200){
                        data.setValue(orderResponseResponse.body().getData());
                    }else{
                        Toast.makeText(application, orderResponseResponse.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> Toast.makeText(application, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
    }
}
