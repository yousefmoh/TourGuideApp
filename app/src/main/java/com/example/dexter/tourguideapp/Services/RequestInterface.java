package com.example.dexter.tourguideapp.Services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dexter on 2/17/2018.
 */

public interface RequestInterface {




    @GET("/tours/city_tours_places/{id}")
    Call<JSONResponse> getJSON(@Query("id") int id);

    


}
