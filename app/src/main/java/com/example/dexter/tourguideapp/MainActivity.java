package com.example.dexter.tourguideapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dexter.tourguideapp.Adapters.AppDatabase;
import com.example.dexter.tourguideapp.Adapters.DataAdapter;
import com.example.dexter.tourguideapp.Models.SampleModel.places;
import com.example.dexter.tourguideapp.Services.LocationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.Manifest;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent start = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(start);
                finish();
            }
        }, 5000);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);


       return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
      return  true;
    }




}
