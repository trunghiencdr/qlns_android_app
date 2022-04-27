package com.example.food.feature.account;

import androidx.lifecycle.ViewModel;

import com.example.food.dto.UserRequestForUpdate;
import com.example.food.model.User;
import com.example.food.network.RetroInstance;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class AccountViewModel extends ViewModel {

    private UserRepository userRepository;


    public AccountViewModel(){
        userRepository = RetroInstance.getRetrofitClient().create(UserRepository.class);
    }

    public Observable<Response<User>> updateInforUser(int id, RequestBody userRequestForUpdate, MultipartBody.Part file){
        return userRepository.updateUser(id, userRequestForUpdate, file);
    }
}
