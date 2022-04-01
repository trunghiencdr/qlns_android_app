package com.example.food.service;


import com.example.food.dto.UserDTO;
import com.example.food.model.User;
import com.example.food.util.ResponseMessage;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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


    @GET("auth/Users")
    public Observable<ResponseMessage> getUsers();
}
