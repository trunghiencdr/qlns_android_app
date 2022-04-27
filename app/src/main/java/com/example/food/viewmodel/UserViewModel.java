package com.example.food.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.food.dto.UserDTO;
import com.example.food.feature.account.UserRepository;
import com.example.food.model.RequestSignup;
import com.example.food.model.User;
import com.example.food.network.RetroInstance;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    MutableLiveData<User> user;

    public UserViewModel(Application application){
        super(application);
        userRepository = RetroInstance.getRetrofitClient().create(UserRepository.class);
        user = new MutableLiveData<>();
    }

    public Observable<UserDTO> makeApiCallSignIn(String username, String password){
       return userRepository.signin(new User(username, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }
    public Observable<UserDTO> makeApiCallSignUp(String username, String name, String password){
        return userRepository.signup(new RequestSignup(username, name, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<Response<User>> apiUpdateUser(int id,RequestBody userRequestForUpdate, MultipartBody.Part file){
        return userRepository.updateUser(id,userRequestForUpdate, file)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public MutableLiveData<User> getuserMultable(){
        return user;
    }

    public DisposableObserver<Response<UserDTO>> getUser(int id){
        return userRepository.getUserById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Response<UserDTO>>() {
                    @Override
                    public void onNext(Response<UserDTO> userDTOResponse) {
                        if(userDTOResponse.code()==200){
                            user.setValue(userDTOResponse.body().getUser());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e("", "call get user by id id userviewModel" + e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }
}
