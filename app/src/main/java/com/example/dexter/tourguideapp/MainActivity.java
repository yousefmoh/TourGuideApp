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
import java.util.List;

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
    private   int Id=0;//
    private  int create=0;
    private  boolean FirstTime=true;

    /**
     * permissions request code
     */
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    /**
     * Permissions that need to be explicitly requested from end user.
     */
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,



    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        setContentView(R.layout.activity_main);


        start();




       // boolean t= checkLocationPermission();
        //SharedPreferences prefs = getSharedPreferences("Creation", MODE_PRIVATE);
       // FirstTime=prefs.getBoolean("FirstTime",true);
       // Toast.makeText(this, FirstTime+ "", Toast.LENGTH_SHORT).show();

      //  if (FirstTime==false)
       // {

       //      start();

      //  }

       // start();

     /*   if (t)
        {
            start();
        }
*/

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

         Toast.makeText(this,Id+"",Toast.LENGTH_SHORT).show();

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

/*
        super.onResume();
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences("Location", MODE_PRIVATE);
        Id=prefs.getInt("CurrentLocation",3);

         Toast.makeText(this,"On Resume"+create+"",Toast.LENGTH_SHORT).show();

        if(create==0) {
            initViews(Id);
        }
*/
    }

/*
 @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(this,"Allow",Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getSharedPreferences("Creation", MODE_PRIVATE).edit();
                        editor.putBoolean("FirstTime", false);
                        editor.apply();


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
*/
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
                .baseUrl("http://snap-project.com/")
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

                recyclerView.setAdapter(adapter);
            }



            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage()+"",Toast.LENGTH_SHORT).show();

                Log.d("Error",t.getMessage());
            }
        });


    }








    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
// check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
// request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
// exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
// all permissions were granted
                break;
        }
    }










    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Location Permission")
                        .setMessage("Please allow Location Permission ! ")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                              //  start();

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
