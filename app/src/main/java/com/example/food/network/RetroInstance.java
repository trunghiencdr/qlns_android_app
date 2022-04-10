package com.example.food.network;

import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {

    public static String BASE_URL = "http://192.168.1.10:8080/api/";
//    public static String BASE_URL = "http://192.168.1.15:8080/api/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient(){
        if(retrofit==null){
//            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            clientBuilder.addInterceptor(loggingInterceptor);

//                    .client(clientBuilder.build())
             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            System.out.println("retrofit build:"+ retrofit.toString());
        }

        return retrofit;
    }
}
