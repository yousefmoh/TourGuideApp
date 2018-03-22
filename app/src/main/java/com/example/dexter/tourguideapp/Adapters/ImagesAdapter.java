package com.example.dexter.tourguideapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dexter.tourguideapp.Models.SampleModel.places;
import com.example.dexter.tourguideapp.PlaceInformationActivity;
import com.example.dexter.tourguideapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dexter on 2/17/2018.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> urls;
    String pos="0";

    public ImagesAdapter(ArrayList<String> places, Context context) {
        this.urls = places;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallary_card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context).load(urls.get(position)).into(holder.placeImage);
holder.placeImage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    }
});
    }

    @Override
    public int getItemCount() {

        return urls.size();
    }


    public class ViewHolder   extends RecyclerView.ViewHolder{


        private ImageView placeImage;
        public ViewHolder(View view) {
            super(view);

            placeImage=(ImageView)view.findViewById(R.id.imageViewPlace);



        }
    }

}


