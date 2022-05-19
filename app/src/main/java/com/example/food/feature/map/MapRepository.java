package com.example.food.feature.map;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface MapRepository {
    @GET("/v1/revgeocode?")
    Single<Response<ResponsePlace>> getPlaceFromGeocode(@Query("at") String at,
                                                        @Query("lang") String lang,
                                                        @Query("apikey") String apikey);
    @GET("/v1/geocode?")
    Single<Response<ResponsePlace>> getGeoCode(@Query("q") String q,
                                                        @Query("apikey") String apikey);

}
