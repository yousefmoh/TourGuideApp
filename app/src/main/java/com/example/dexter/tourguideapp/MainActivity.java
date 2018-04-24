package com.example.dexter.tourguideapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
    private Toolbar toolbar;
    private DataAdapter adapter;
    SearchManager searchManager;
    private ProgressDialog progressDialog;
    LocationManager locationManager;
    String provider;
    SearchView searchView;
    private   int Id=0;//
    private  int create=0;
    private  boolean FirstTime=true;
    private  boolean onResume=false;

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Getting Locations :) ");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        toolbar=(Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
       // toolbar.inflateMenu(R.menu.main);

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
        SharedPreferences prefs = getSharedPreferences("LocationN", MODE_PRIVATE);

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


         SharedPreferences prefs = getSharedPreferences("LocationN", MODE_PRIVATE);

         Id=prefs.getInt("CurrentLocation",3);

         //Toast.makeText(this,Id+"",Toast.LENGTH_SHORT).show();

         if(Id==2){
             Toast.makeText(this,"Ramallah",Toast.LENGTH_SHORT).show();
         }
         else if(Id==3)
         {
             Toast.makeText(this,"Nablus",Toast.LENGTH_SHORT).show();
         }

         initViews(Id);
        /* prefs=getSharedPreferences("LocationN", MODE_PRIVATE);
         Id=prefs.getInt("CurrentLocation",3);
         Toast.makeText(this,Id+"",Toast.LENGTH_SHORT).show();
*/

         //   progressDialog.dismiss(); // Display Progress Dialog

     }




    @Override
    protected void onResume() {
        super.onResume();
        if(onResume) {
            setRecycleView();
            onResume = false;
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onResume=true;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                //Toast.makeText(getApplicationContext(),s+"",Toast.LENGTH_SHORT).show();
               GetPlacesJson(Id,s);
               // if (data!=null)
                //Toast.makeText(getApplicationContext(),data.get(0).getName(),Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //GetPlacesJson(Id,s);

               // Toast.makeText(getApplicationContext(),s+"",Toast.LENGTH_SHORT).show();
                return false;
            }



        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                 loadJSON(Id);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                //Toast.makeText(this,"ll",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews(int id){
       // recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
       // recyclerView.setHasFixedSize(true);
       // RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
      // recyclerView.setLayoutManager(layoutManager);
        loadJSON(id);
      //  GetPlacesJson(2,"c");



    }
   void setRecycleView(){
       recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
       recyclerView.setHasFixedSize(true);
       RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
       recyclerView.setLayoutManager(layoutManager);
       recyclerView.setAdapter(adapter);


   }



    private void loadJSON(int id){
        progressDialog.show(); // Display Progress Dialog

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
                setRecycleView();
              //  recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }



            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage()+"",Toast.LENGTH_SHORT).show();

                Log.d("Error",t.getMessage());
            }
        });


    }



    private void GetPlacesJson(int id,String Key){
        progressDialog.show(); // Display Progress Dialog

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://snap-project.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);

        Call<JSONResponse> call = request.getPlaces(id,Key);

        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();


                data = new ArrayList<>(Arrays.asList(jsonResponse.getPlaces()));


                adapter = new DataAdapter(data,getApplicationContext());
                setRecycleView();
             //   recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }



            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage()+"",Toast.LENGTH_SHORT).show();

                Log.d("Error",t.getMessage());
            }
        });


    }




    public void  CheckShared()
{

    SharedPreferences prefs = getSharedPreferences("LocationN", MODE_PRIVATE);

    int x=prefs.getInt("CurrentLocation",3);
    Toast.makeText(this,x+"  city id",Toast.LENGTH_SHORT).show();

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

                startService(new Intent(this,LocationService.class));

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
                        .setTitle("LocationN Permission")
                        .setMessage("Please allow LocationN Permission ! ")
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
