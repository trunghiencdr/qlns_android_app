package com.example.food.feature.map;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.food.Api.Api;
import com.example.food.network.RetroInstance;
import com.example.food.util.AppUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MapViewModel extends AndroidViewModel {

    MutableLiveData<String> titlePlace;
    MutableLiveData<Float> lat;
    MutableLiveData<Float> lng;
    MutableLiveData<String> message;
    MutableLiveData<Boolean> clickLocation;
    int count = 0;


    public MapViewModel(@NonNull Application application) {
        super(application);
        titlePlace = new MutableLiveData<>();
        lat = new MutableLiveData<>();
        lng = new MutableLiveData<>();
        message = new MutableLiveData<>();
        clickLocation = new MutableLiveData<>();

    }

    public MutableLiveData<String> getTitlePlace() {
        return titlePlace;
    }

    public MutableLiveData<Float> getLat() {
        return lat;
    }

    public MutableLiveData<Float> getLng() {
        return lng;
    }
    public MutableLiveData<Boolean> getClickLocation(){return clickLocation;}

    @SuppressLint("CheckResult")
    public void callGetPlaceFromGeocode(String at, String lang, String apikey){
        Api.getRetrofit(
                "https://revgeocode.search.hereapi.com").create(MapRepository.class)
                .getPlaceFromGeocode(at, lang, apikey)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(responsePlaceResponse -> {
            if(responsePlaceResponse.code()==200){
                titlePlace.setValue(responsePlaceResponse.body().items.get(0).title);
                if(count!=0) clickLocation.setValue(true);
                count++;
            }else {
                Log.d("AAA", responsePlaceResponse.errorBody().string());
//                message.setValue();
            }
        }, throwable -> message.setValue(AppUtils.getErrorMessage(throwable.getMessage())));
    }

    @SuppressLint("CheckResult")
    public void callGetGeoCodeFromPlace(String q,  String apikey){
        Api.getRetrofit(
                "https://geocode.search.hereapi.com").create(MapRepository.class)
                .getGeoCode(q, apikey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responsePlaceResponse -> {
                    if(responsePlaceResponse.code()==200){
                        lat.setValue(responsePlaceResponse.body().items.get(0).position.lat);
                        lng.setValue(responsePlaceResponse.body().items.get(0).position.lng);
                    }else {
                        message.setValue(AppUtils.getErrorMessage(responsePlaceResponse.errorBody().string()));
                    }
                }, throwable -> message.setValue(AppUtils.getErrorMessage(throwable.getMessage())));
    }
}
