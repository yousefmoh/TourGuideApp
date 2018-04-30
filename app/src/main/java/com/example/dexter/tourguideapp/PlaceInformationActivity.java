package com.example.dexter.tourguideapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dexter.tourguideapp.Services.RequestInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dexter on 3/2/2018.
 */

public class PlaceInformationActivity extends AppCompatActivity {
    ImageView placeimage;
    TextView placeDescription,placeName,placeAddress,placePhone;
    Button WriteExp,Btn;
    FloatingActionButton mapFap,expFap;
    ArrayList<String> images;
    String url,description,Latitude,Longitude,PlaceId,name,address,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placelayout);
        placeimage=(ImageView)findViewById(R.id.imagePlace);
        placeDescription=(TextView) findViewById(R.id.descriptionPlace);
        placeName=(TextView) findViewById(R.id.place_nameTxt);
        placeAddress=(TextView) findViewById(R.id.address_txt);
        placePhone=(TextView) findViewById(R.id.phone_txt);
        mapFap=(FloatingActionButton)findViewById(R.id.mapFap);
        expFap=(FloatingActionButton)findViewById(R.id.expFap);
        WriteExp=(Button)findViewById(R.id.WriteExp);
        Btn=(Button)findViewById(R.id.AddPhoto);








        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        WriteExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());


                final EditText expEdittext = new EditText(view.getContext());
                final EditText nameEditText = new EditText(view.getContext());
                expEdittext.setHint("Please Write your Exprience");
                nameEditText.setHint("Please Write your Name");


                final LinearLayout Layout=new LinearLayout(view.getContext());
                Layout.setOrientation(LinearLayout.VERTICAL);

                Layout.addView(nameEditText);
                Layout.addView(expEdittext);

                alert.setMessage("Enter Your Experience");

                alert.setView(Layout);

                alert.setPositiveButton("Write ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String experienceText = expEdittext.getText().toString();
                        String nameText = nameEditText.getText().toString();
                        InsertExp(nameText,experienceText,PlaceId);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.show();


            }
        });



        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                url= null;
                PlaceId=null;
                description=null;
                Latitude=null;
                name=null;
                address=null;
                Longitude=null;
                images=null;
            } else {

                url= extras.getString("ImageUrl");
                description= extras.getString("Description");
                PlaceId= extras.getString("PlaceId");
                Latitude=extras.getString("Latitude");
                Longitude=extras.getString("Longitude");
                name=extras.getString("name");
                address=extras.getString("address");
                phone=extras.getString("phone");



            }
        } else {
            url= (String) savedInstanceState.getSerializable("ImageUrl");
            description= (String) savedInstanceState.getSerializable("Description");
            Latitude= (String) savedInstanceState.getSerializable("Latitude");
            Longitude= (String) savedInstanceState.getSerializable("Longitude");
            name= (String) savedInstanceState.getSerializable("name");
            address= (String) savedInstanceState.getSerializable("address");
            phone= (String) savedInstanceState.getSerializable("phone");


        }



        Picasso.with(this).load(url).into(placeimage);

        placeDescription.setText(description);
        placeAddress.setText(address);
        placeName.setText(name);
        placePhone.setText(phone);
        placeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GallaryActivity.class);
                intent.putExtra("PlaceId", PlaceId);
                view.getContext().startActivity(intent);
            }
        });
        mapFap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent intent = new Intent(view.getContext(), MapPolylineActivity.class);
                  intent.putExtra("Latitude", Latitude);
                  intent.putExtra("Longitude", Longitude);

                view.getContext().startActivity(intent);

            }
        });

        expFap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),ExperiencesActivity.class);
                intent.putExtra("PlaceId", PlaceId);

                startActivity(intent);
            }
        });



    }



    public  void  InsertExp(String name , String exp,String placeId)
    {


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://snap-project.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);

        Call<String> call=request.insertExp(name,exp,placeId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

}
