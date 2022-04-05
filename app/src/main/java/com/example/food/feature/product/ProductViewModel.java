package com.example.food.feature.product;



import androidx.lifecycle.ViewModel;

import com.example.food.Domain.Product;
import com.example.food.network.RetroInstance;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {

   private ProductRepository productRepository;

   public ProductViewModel(){
       productRepository = RetroInstance.getRetrofitClient().create(ProductRepository.class);
   }

    public Observable<Response<List<Product>>> getTop10Products(){
        return productRepository.getTop10Products()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
