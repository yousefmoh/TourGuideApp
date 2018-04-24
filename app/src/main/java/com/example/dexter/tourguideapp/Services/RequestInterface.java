package com.example.dexter.tourguideapp.Services;

import com.example.dexter.tourguideapp.Models.Images;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dexter on 2/17/2018.
 */

public interface RequestInterface {




    //@GET("/tourguideapis/city_tours_places.php/{id}")
   // Call<JSONResponse> getJSON(@Query("id") int id);


    @GET("/tourguideapis/tours.php/{id}")//
    Call<JSONResponse> getJSON(@Query("id") int id);



    @GET("/tourguideapis/search.php/")//tourguideapis/search.php?key=old&id=3
    Call<JSONResponse> getPlaces(@Query("id") int id,@Query("key")String key);

    @GET("/tourguideapis/images.php/{id}")//tourguideapis/images.php?id=1
    Call<List<Images>> getImages(@Query("id") int id);


}
