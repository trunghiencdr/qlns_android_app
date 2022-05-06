package com.example.food.feature.account;

import com.example.food.dto.UserDTO;
import com.example.food.dto.UserRequestForUpdate;
import com.example.food.model.RequestSignup;
import com.example.food.model.User;
import com.example.food.util.ResponseMessage;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserRepository {

    @GET
    Observable<Response<User>> getUserByUserName(@Path("username") String username);

    @Multipart
    @PUT("v1/Users/update/{id}")
    Observable<Response<User>> updateUser(
            @Path("id") int id,
            @Part("user") RequestBody userRequestForUpdate,
            @Part MultipartBody.Part file
            );
    @Multipart
    @PUT("v1/Users/updateNotImage/{id}")
    Observable<Response<User>> updateUserNotImage(
            @Path("id") int id,
            @Part("user") RequestBody userRequestForUpdate
    );

    @POST("auth/signin")
    Observable<UserDTO> signin(@Body User user);

    @POST("auth/signup")
    Observable<UserDTO> signup(@Body RequestSignup user);


    @GET("auth/Users")
    Observable<ResponseMessage> getUsers();

    @GET("v1/Users/id/{id}")
    Single<Response<UserDTO>> getUserById(@Path("id") int id);


}
