package com.example.food.Api;

import android.content.Context;
import android.util.Log;

import com.example.food.Domain.Cart;
import com.example.food.Domain.Category;
import com.example.food.Domain.Order;
import com.example.food.Domain.Product;
import com.example.food.Domain.Response.CartResponse;
import com.example.food.Domain.Response.OrderDetailResponse;
import com.example.food.Domain.Response.OrderResponse;
import com.example.food.Listener.CartResponseListener;
import com.example.food.Listener.CategoryResponseListener;
import com.example.food.Listener.DeleteCartResponseListener;
import com.example.food.Listener.InsertCartResponseListener;
import com.example.food.Listener.InsertOrderDetailResponseListener;
import com.example.food.Listener.InsertOrderResponseListener;
import com.example.food.Listener.ProductResponseListener;
import com.example.food.dto.CartDTO;
import com.example.food.dto.OrderDetailDTO;
import com.example.food.dto.OrdersDTO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class Api {
    Context context;

    public Api(Context context) {
        this.context = context;
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.1.10:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void getCategories(CategoryResponseListener listener) {
        CallAllCategory callCategories = retrofit.create(CallAllCategory.class);
        Call<ArrayList<Category>> call = callCategories.getListCategoryDomain();
        call.enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getProducts(ProductResponseListener listener) {
        CallAllProduct callProducts = retrofit.create(CallAllProduct.class);
        Call<ArrayList<Product>> call = callProducts.getListProductDomain();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getCartsByUserId(CartResponseListener listener,int id){
        CallListCartDomainFollowUserId callCarts =retrofit.create(CallListCartDomainFollowUserId.class);
        Call<ArrayList<Cart>> call = callCarts.getListCartDomainFollowUserId(id);
        call.enqueue(new Callback<ArrayList<Cart>>() {
            @Override
            public void onResponse(Call<ArrayList<Cart>> call, Response<ArrayList<Cart>> response) {
                if (!response.isSuccessful()) {
                    Log.d("cart1",response.message());
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<ArrayList<Cart>> call, Throwable t) {
                Log.d("cart2",t.toString());
                listener.didError(t.getMessage());
            }
        });
    }

    public void getListProductByCategoryId(ProductResponseListener listener,int id){
        CallListProductByCategoryId callProducts =retrofit.create(CallListProductByCategoryId.class);
        Call<ArrayList<Product>> call = callProducts.getListProductByCategoryId(id);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void insertCart(InsertCartResponseListener listener,CartDTO cartDTO){
        CallInsertCart callInsertCart=retrofit.create(CallInsertCart.class);
        Call<CartResponse> call =callInsertCart.insertCart(cartDTO);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void updateCart(InsertCartResponseListener listener,CartDTO cartDTO){
        CallUpdateCart callUpdateCart=retrofit.create(CallUpdateCart.class);
        Call<CartResponse> call =callUpdateCart.updateCart(cartDTO);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void deleteCartByUserId(DeleteCartResponseListener listener, int userId){
        CallDeleteCartByUserId callDeleteCartByUserId=retrofit.create(CallDeleteCartByUserId.class);
        Call<String> call =callDeleteCartByUserId.deleteCartByUserId(userId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }

    public void deleteCartByUserIdAndProductId(DeleteCartResponseListener listener, int userId,int productId){
        CallDeleteCartByUserIdAndProductId callDeleteCartByUserIdAndProductId=retrofit.create(CallDeleteCartByUserIdAndProductId.class);
        Call<String> call =callDeleteCartByUserIdAndProductId.deleteCartByUserId(userId,productId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });

    }

    public void insertOrder(InsertOrderResponseListener listener, OrdersDTO ordersDTO){
        CallInsertOrder callInsertOrder=retrofit.create(CallInsertOrder.class);
        Call<OrderResponse> call =callInsertOrder.insertOrder(ordersDTO);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void insertOrderDetails(InsertOrderDetailResponseListener listener, OrderDetailDTO orderDetailDTO){
        CallInsertOrderDetail callInsertOrderDetail=retrofit.create(CallInsertOrderDetail.class);
        Call<OrderDetailResponse> call =callInsertOrderDetail.insertOrderDetails(orderDetailDTO);
        call.enqueue(new Callback<OrderDetailResponse>() {
            @Override
            public void onResponse(Call<OrderDetailResponse> call, Response<OrderDetailResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<OrderDetailResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallAllCategory{
        @GET("api/v1/Categories")
        Call<ArrayList<Category>> getListCategoryDomain();
    }

    private interface CallAllProduct{
        @GET("api/v1/Products")
        Call<ArrayList<Product>> getListProductDomain();
    }

    private  interface  CallListCartDomainFollowUserId{
        @GET("api/v1/Carts/user/{id}")
        Call<ArrayList<Cart>> getListCartDomainFollowUserId(@Path(value = "id") int id);
    }

    private  interface  CallListProductByCategoryId{
        @GET("api/v1/Products/category/{id}")
        Call<ArrayList<Product>> getListProductByCategoryId(@Path(value = "id") int id);
    }

    private interface CallInsertCart {
        @POST("api/v1/Carts/insert")
        Call<CartResponse> insertCart(@Body CartDTO cartDTO);
    }

    private interface  CallUpdateCart{
        @PUT("api/v1/Carts")
        Call<CartResponse> updateCart(@Body CartDTO cartDTO);
    }

    private interface CallDeleteCartByUserId{
        @DELETE("api/v1/Carts/user/{id}")
        Call<String> deleteCartByUserId(@Path(value = "id") int id);
    }

    private interface CallDeleteCartByUserIdAndProductId{
        @DELETE("api/v1/Carts/user/{userId}/product/{productId}")
        Call<String> deleteCartByUserId(@Path(value = "userId") int id,
                                        @Path(value = "productId") int productId);
    }

    private interface CallInsertOrder {
        @POST("api/v1/Orders/insert")
        Call<OrderResponse> insertOrder(@Body OrdersDTO ordersDTO);
    }

    private interface CallInsertOrderDetail {
        @POST("api/v1/OrderDetails/insert")
        Call<OrderDetailResponse> insertOrderDetails(@Body OrderDetailDTO orderDetailDTO);
    }




}
