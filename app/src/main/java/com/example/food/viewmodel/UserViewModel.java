package com.example.food.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.food.dto.UserDTO;
import com.example.food.model.User;
import com.example.food.network.RetroInstance;
import com.example.food.service.UserService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends ViewModel {
    private Observable<User> user;
    private UserService userService;

    public UserViewModel(){
    }

//    public MutableLiveData<List<User>> getUserResponseObserver(){
//        return this.userList;
//    }


    public Observable<UserDTO> makeApiCallSignIn(String username, String password){
        userService = RetroInstance.getRetrofitClient().create(UserService.class);
        System.out.println("retrofit:"+userService.toString());
//        Observable<Optional<Response>> call = api.signin()
       return userService.signin(new User(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public void makeAPICallGetUsers(){
        userService = RetroInstance.getRetrofitClient().create(UserService.class);
//        Observable<Optional<Response>> call = api.signin()
//        userService.getUsers()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(response -> {
//
//                            System.out.println(response.getStatus());
//                            System.out.println(response.getData());
//
//
//                        }, throwable -> {
//                            System.out.println(throwable.getLocalizedMessage());
//                        },
//                        ()-> System.out.println("on complete")
//                );
    }



}
