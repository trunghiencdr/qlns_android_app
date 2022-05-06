package com.example.food.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.food.dto.UserDTO;
import com.example.food.feature.account.UserRepository;
import com.example.food.model.RequestSignup;
import com.example.food.model.User;
import com.example.food.network.RetroInstance;

import java.io.IOException;
import java.lang.annotation.Annotation;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
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

    public Observable<Response<User>> apiUpdateUserNotImage(int id,RequestBody userRequestForUpdate){
        return userRepository.updateUserNotImage(id,userRequestForUpdate)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public MutableLiveData<User> getuserMultable(){
        return user;
    }

    @SuppressLint("CheckResult")
    public void getUser(int id){
       userRepository.getUserById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<Response<UserDTO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onSuccess(Response<UserDTO> userDTOResponse) {
                        if(userDTOResponse.code()==200){
                            Log.d("call api", "call user success");
                            user.setValue(userDTOResponse.body().getUser());
                        }else {
                            try {
                                Log.d("error api", userDTOResponse.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();

                            Converter<ResponseBody, Error> errorConverter =
                                    RetroInstance.getRetrofitClient().responseBodyConverter(Error.class, new Annotation[0]);
                            // Convert the error body into our Error type.
                            try {
                                Error error = errorConverter.convert(body);
                                Log.i("","ERROR: " + error.getMessage());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        Log.e("", "call get user by id id userviewModel" + e.getMessage());
                    }
                });
    }
}
