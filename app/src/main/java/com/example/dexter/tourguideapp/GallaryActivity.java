package com.example.dexter.tourguideapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.dexter.tourguideapp.Adapters.PicassoImageLoader;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dexter on 3/22/2018.
 */

public class GallaryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<String> Images;
    private  ScrollGalleryView scrollGalleryView;
    List<MediaInfo> infos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallary_layout);

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


        infos = new ArrayList<>(Images.size());//
    //    for (int i=0;i<Images.size();i++)infos.add(MediaInfo.mediaLoader(new PicassoImageLoader(Images.get(i))));
       for (String url : Images) infos.add(MediaInfo.mediaLoader(new PicassoImageLoader(url)));
        SetGallary();


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


}
