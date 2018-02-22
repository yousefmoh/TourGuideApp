package com.example.dexter.tourguideapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.example.dexter.tourguideapp.Adapters.DataAdapter;
import com.example.dexter.tourguideapp.Models.SampleModel.places;
import com.example.dexter.tourguideapp.Services.JSONResponse;
import com.example.dexter.tourguideapp.Services.LocationService;
import com.example.dexter.tourguideapp.Services.RequestInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<places> data;
    private DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this,LocationService.class));
        SharedPreferences prefs = getSharedPreferences("Location", MODE_PRIVATE);

        int Id=prefs.getInt("CurrentLocation",0);
        if(Id==2){


            Toast.makeText(this,"Ramallah",Toast.LENGTH_SHORT).show();

        }
        else if(Id==3)
        {
            Toast.makeText(this,"Nablus",Toast.LENGTH_SHORT).show();

        }
        
        initViews(Id);
    }






    private void initViews(int id){
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        loadJSON(id);
    }




    private void loadJSON(int id){

        Gson gson = new GsonBuilder()
                .setLenient()

                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.5:8080/")//simplifiedcoding.net/demos/marvel
                //http://192.168.0.103/tours/city_tours_places.php?id=2
                //http://192.168.0.103/tours/tours_places.php

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);

        Call<JSONResponse> call = request.getJSON(id);

        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();


                data = new ArrayList<>(Arrays.asList(jsonResponse.getPlaces()));


                adapter = new DataAdapter(data,getApplicationContext());
//                Toast.makeText(getApplicationContext(),data.get(0).getName()+"",Toast.LENGTH_SHORT).show();

                recyclerView.setAdapter(adapter);
            }



            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage()+"",Toast.LENGTH_SHORT).show();

                Log.d("Error",t.getMessage());
            }
        });

//        Toast.makeText(getApplicationContext(),call+"",Toast.LENGTH_SHORT).show();

    }








}
