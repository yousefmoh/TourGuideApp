package com.example.dexter.tourguideapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dexter.tourguideapp.Adapters.DataAdapter;
import com.example.dexter.tourguideapp.Adapters.ExperiencesAdapter;
import com.example.dexter.tourguideapp.Models.ExperiencesModel;
import com.example.dexter.tourguideapp.Models.SampleModel.places;
import com.example.dexter.tourguideapp.Services.JSONResponse;
import com.example.dexter.tourguideapp.Services.LocationService;
import com.example.dexter.tourguideapp.Services.RequestInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExperiencesActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private RecyclerView recyclerView;
    private ArrayList<ExperiencesModel> data=new ArrayList<>();
    private Toolbar toolbar;
    private ExperiencesAdapter adapter;
    String PlaceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiences_layout);
        toolbar=(Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                PlaceId=null;

            } else {


                PlaceId= extras.getString("PlaceId");


            }
        } else {
            PlaceId= (String) savedInstanceState.getSerializable("PlaceId");


        }


       //GetExtra(savedInstanceState);
        loadJSON(Integer.parseInt(PlaceId));


        //adapter=new ExperiencesAdapter(data,this);
        //setRecycleView();




    }


    public  void  GetExtra(Bundle bundle)
    {
        if (bundle == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                PlaceId=null;

            } else {


                PlaceId= extras.getString("PlaceId");


            }
        } else {
            PlaceId= (String) bundle.getSerializable("PlaceId");


        }
    }






    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();


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



   void setRecycleView(){
       recyclerView = (RecyclerView)findViewById(R.id.experiences_recycler_view);
       recyclerView.setHasFixedSize(true);
       RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
       recyclerView.setLayoutManager(layoutManager);
       recyclerView.setAdapter(adapter);


   }



    private void loadJSON(int id){
        //progressDialog.show(); // Display Progress Dialog

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://snap-project.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestInterface request = retrofit.create(RequestInterface.class);

        Call<List<ExperiencesModel>> call = request.getExperiences(id);

      call.enqueue(new Callback<List<ExperiencesModel>>() {
          @Override
          public void onResponse(Call<List<ExperiencesModel>> call, Response<List<ExperiencesModel>> response) {

              List<ExperiencesModel> jsonResponse = response.body();

               data = new ArrayList<>(jsonResponse);


             adapter = new ExperiencesAdapter(data,getApplicationContext());
                        setRecycleView();
              //      progressDialog.dismiss();

          }

          @Override
          public void onFailure(Call<List<ExperiencesModel>> call, Throwable t) {

          }
      });

    }



















}
