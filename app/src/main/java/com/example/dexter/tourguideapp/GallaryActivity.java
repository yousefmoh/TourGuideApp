package com.example.dexter.tourguideapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.dexter.tourguideapp.Adapters.DataAdapter;
import com.example.dexter.tourguideapp.Adapters.ImagesAdapter;
import com.example.dexter.tourguideapp.Models.SampleModel;

import java.util.ArrayList;

/**
 * Created by dexter on 3/22/2018.
 */

public class GallaryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> Images;
    private ImagesAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallarylayout);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {

                Images=null;
            } else {

                Images = (ArrayList<String>) getIntent().getSerializableExtra("ImagesList");


            }
        } else {

            Images = (ArrayList<String>) getIntent().getSerializableExtra("ImagesList");


        }

        initViews();
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.gallery_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ImagesAdapter(Images,getApplicationContext());
        recyclerView.setAdapter(adapter);




    }
}
