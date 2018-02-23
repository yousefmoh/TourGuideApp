package com.example.dexter.tourguideapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.Manifest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private RecyclerView recyclerView;
    private ArrayList<places> data;
    private DataAdapter adapter;
    LocationManager locationManager;
    String provider;
    private   int Id=0;//Default Nablus
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        checkLocationPermission();
        start();

        /*
        startService(new Intent(this,LocationService.class));
        SharedPreferences prefs = getSharedPreferences("Location", MODE_PRIVATE);

         Id=prefs.getInt("CurrentLocation",0);
        if(Id==2){


            Toast.makeText(this,"Ramallah",Toast.LENGTH_SHORT).show();

        }
        else if(Id==3)
        {
            Toast.makeText(this,"Nablus",Toast.LENGTH_SHORT).show();

        }
        
        initViews(Id);*/
    }

     public  void start () {

         startService(new Intent(this,LocationService.class));
         SharedPreferences prefs = getSharedPreferences("Location", MODE_PRIVATE);

         Id=prefs.getInt("CurrentLocation",3);
         if(Id==2){


             Toast.makeText(this,"Ramallah",Toast.LENGTH_SHORT).show();

         }
         else if(Id==3)
         {
             Toast.makeText(this,"Nablus",Toast.LENGTH_SHORT).show();

         }

         initViews(Id);

     }


    @Override
    protected void onResume() {

        super.onResume();
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences("Location", MODE_PRIVATE);

        Id=prefs.getInt("CurrentLocation",3);

        Toast.makeText(this,"On Resume",Toast.LENGTH_SHORT).show();

        initViews(Id);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(this,"Allow",Toast.LENGTH_SHORT).show();

                        start();


                    }

                } else {

                    Toast.makeText(this,"Denied",Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
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

                //192.168.42.185
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







    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission")
                        .setMessage("Please allow ")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


}
