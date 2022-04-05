package com.example.food.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.food.Domain.CategoryDomain;
import com.example.food.dto.CategoryResponse;
import com.example.food.feature.category.CategoryService;
import com.example.food.network.RetroInstance;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListViewModel extends ViewModel {

    private MutableLiveData<List<CategoryDomain>> categories;
    private CategoryService api;
    private CategoryDomain categoryDomain;

    public CategoryListViewModel(){
        api = RetroInstance.getRetrofitClient().create(CategoryService.class);
    }

    public MutableLiveData<List<CategoryDomain>> getCategoriesObserver(){
        return categories;
    }

    public MutableLiveData<List<CategoryDomain>> makeApiCall(){

        Call<List<CategoryDomain>> call = api.getCategoryDomains();
        call.enqueue(new Callback<List<CategoryDomain>>() {
            @Override
            public void onResponse(Call<List<CategoryDomain>> call, Response<List<CategoryDomain>> response) {
                System.out.println("body:" + response.body());
                try{
                    categories.setValue(response.body());
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("size of categories" + categories.getValue().size());
            }

            @Override
            public void onFailure(Call<List<CategoryDomain>> call, Throwable t) {
                System.out.println("throw call api:" + t.getMessage());
                categories.postValue(null);
            }
        });
        return categories;
    }

    public Observable<Response<CategoryResponse>> callGetCategory(int id){
        return api.getCategoryById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
