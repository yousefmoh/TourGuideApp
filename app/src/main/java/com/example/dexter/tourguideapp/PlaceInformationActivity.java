package com.example.dexter.tourguideapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by dexter on 3/2/2018.
 */

public class PlaceInformationActivity extends AppCompatActivity {
    ImageView placeimage;
    TextView placeDescription;
    FloatingActionButton mapFap;
    String url,description,Latitude,Longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placelayout);
        placeimage=(ImageView)findViewById(R.id.imagePlace);
        placeDescription=(TextView) findViewById(R.id.descriptionPlace);
        mapFap=(FloatingActionButton)findViewById(R.id.mapFap);




        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                url= null;
                description=null;
                Latitude=null;
                Longitude=null;
            } else {
                url= extras.getString("ImageUrl");
                description= extras.getString("Description");
                Latitude=extras.getString("Latitude");
                Longitude=extras.getString("Longitude");


            }
        } else {
            url= (String) savedInstanceState.getSerializable("ImageUrl");
            description= (String) savedInstanceState.getSerializable("Description");
            Latitude= (String) savedInstanceState.getSerializable("Latitude");
            Longitude= (String) savedInstanceState.getSerializable("Longitude");


        }




        Picasso.with(this).load(url).into(placeimage);
        placeDescription.setText(description);


        mapFap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent intent = new Intent(view.getContext(), MapPolylineActivity.class);
                  view.getContext().startActivity(intent);

            }
        });


    }


}