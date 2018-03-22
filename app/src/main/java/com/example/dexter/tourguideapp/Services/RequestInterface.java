package com.example.dexter.tourguideapp.Services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dexter on 2/17/2018.
 */

public interface RequestInterface {




    //@GET("/tourguideapis/city_tours_places.php/{id}")
   // Call<JSONResponse> getJSON(@Query("id") int id);


    @GET("/tourguideapis/tours.php/{id}")
    Call<JSONResponse> getJSON(@Query("id") int id);



}
