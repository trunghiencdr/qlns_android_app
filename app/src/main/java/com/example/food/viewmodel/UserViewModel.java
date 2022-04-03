package com.example.food.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.food.dto.UserDTO;
import com.example.food.model.RequestSignup;
import com.example.food.model.User;
import com.example.food.network.RetroInstance;
import com.example.food.service.UserService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends ViewModel {
    private Observable<User> user;
    private UserService userService;

    public UserViewModel(){
        userService = RetroInstance.getRetrofitClient().create(UserService.class);
    }

//    public MutableLiveData<List<User>> getUserResponseObserver(){
//        return this.userList;
//    }


    public Observable<UserDTO> makeApiCallSignIn(String username, String password){

        System.out.println("retrofit:"+userService.toString());
//        Observable<Optional<Response>> call = api.signin()
       return userService.signin(new User(username, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }
    public Observable<UserDTO> makeApiCallSignUp(String username, String name, String password){

        System.out.println("retrofit:"+userService.toString());
//        Observable<Optional<Response>> call = api.signin()
        return userService.signup(new RequestSignup(username, name, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }


//    public Observable<UserDTO> makeApiCallSignUp(String username, String password, String confirmPassword){
//        return userService.signup(new User(username, confirmPassword))
//                .
//    }


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
