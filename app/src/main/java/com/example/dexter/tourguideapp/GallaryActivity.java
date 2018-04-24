package com.example.dexter.tourguideapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.dexter.tourguideapp.Adapters.PicassoImageLoader;
import com.example.dexter.tourguideapp.Models.Images;
import com.example.dexter.tourguideapp.Services.RequestInterface;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dexter on 3/22/2018.
 */

public class GallaryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> Images=new ArrayList<>();
    ArrayList<Images> data=new ArrayList<>();
    private  ScrollGalleryView scrollGalleryView;
    List<MediaInfo> infos;
    String PlaceId="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallary_layout);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                PlaceId=null;
            } else {
                PlaceId=getIntent().getStringExtra("PlaceId");
            }
        } else {
            PlaceId=getIntent().getStringExtra("PlaceId");
        }

        loadJSON();




    }
    private Bitmap toBitmap(int image) {
        return ((BitmapDrawable) getResources().getDrawable(image)).getBitmap();
    }

    private  void  SetGallary()
    {
        scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);
        scrollGalleryView
                .setThumbnailSize(100)
                .setZoom(true)
                .setFragmentManager(getSupportFragmentManager())
                .addMedia(infos);

    }


    private void loadJSON( ){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://snap-project.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);

        Call<List<Images>> call = request.getImages(Integer.parseInt(PlaceId));
        call.enqueue(new Callback<List<Images>>() {
            @Override
            public void onResponse(Call<List<Images>> call, Response<List<Images>> response) {
                List<Images> jsonResponse = response.body();
                data = new ArrayList<>(jsonResponse);
                setData();


            }

            @Override
            public void onFailure(Call<List<Images>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
    private  void  setData()
    {

        for (int i =0;i<data.size();i++) {
            Images.add(data.get(i).getImage());
        }

        infos = new ArrayList<>(Images.size());//
        for (String url : Images) infos.add(MediaInfo.mediaLoader(new PicassoImageLoader(url)));

        SetGallary();

    }


}
