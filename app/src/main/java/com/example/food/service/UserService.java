package com.example.food.service;


import com.example.food.dto.UserDTO;
import com.example.food.model.RequestSignup;
import com.example.food.model.User;
import com.example.food.util.ResponseMessage;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {

    @Headers( {
            "Accept-Encoding: gzip,deflate",
            "Content-Type: Application/Json;charset=UTF-8",
            "Accept: Application/Json",
            "User-Agent: Retrofit 2.9.0"
    } )
    @POST("auth/signin")
    public Observable<UserDTO> signin(@Body User user);

    @POST("auth/signup")
    public Observable<UserDTO> signup(@Body RequestSignup user);


    @GET("auth/Users")
    public Observable<ResponseMessage> getUsers();
}
