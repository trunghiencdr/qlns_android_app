package com.example.food.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.food.Domain.CategoryDomain;
import com.example.food.network.CategoryAPIService;
import com.example.food.network.RetroInstance;

import java.util.List;

import lombok.NoArgsConstructor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListViewModel extends ViewModel {

    private MutableLiveData<List<CategoryDomain>> categories;

    public CategoryListViewModel(){
        categories = new MutableLiveData<>();
    }

    public MutableLiveData<List<CategoryDomain>> getCategoriesObserver(){
        return categories;
    }

    public void makeApiCall(){
        CategoryAPIService api = RetroInstance.getRetrofitClient().create(CategoryAPIService.class);
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
    }
}
