package com.example.dexter.tourguideapp.Services;

import com.example.dexter.tourguideapp.Models.ExperiencesModel;
import com.example.dexter.tourguideapp.Models.ImagesModel;
import com.example.dexter.tourguideapp.Models.ResponseModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by Dexter on 2/17/2018.
 */

public interface RequestInterface {


    @GET("/tourguideapis/tours.php/{id}")//
    Call<JSONResponse> getJSON(@Query("id") int id);



    @GET("/tourguideapis/alltours.php")//
    Call<JSONResponse> getAllLocations();



    @Multipart
    @POST("/tourguideapis/uploadfile.php")
    Call<ResponseModel> uploadFile(
            @PartMap Map<String, RequestBody> map,@Query("id")int id
    );


    @GET("/tourguideapis/searchalllocations.php/")//
    Call<JSONResponse> searchAllPlaces(@Query("key")String key);



    @GET("/tourguideapis/tourguide_experiences.php/")//
    Call<List<ExperiencesModel>> getExperiences(@Query("id") int id);


    @GET("/tourguideapis/search.php/")//tourguideapis/search.php?key=old&id=3
    Call<JSONResponse> getPlaces(@Query("id") int id,@Query("key")String key);



    @GET("/tourguideapis/images.php/{id}")//tourguideapis/images.php?id=1
    Call<List<ImagesModel>> getImages(@Query("id") int id);



    @FormUrlEncoded
    @POST("/tourguideapis/insertexp.php")
    Call<String> insertExp(  @Field("name") String name,
                             @Field("experiences") String experiences,
                             @Field("id") String id
    );


}
