package com.example.dexter.tourguideapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dexter.tourguideapp.R;
import com.example.dexter.tourguideapp.Models.SampleModel.*;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dexter on 2/17/2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private Context context;

    private ArrayList<places> places;

    public DataAdapter(ArrayList<places> places, Context context) {
        this.places = places;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {



        holder.name.setText(places.get(position).getName());
        holder.description.setText(places.get(position).getDescription());
        Picasso.with(context).load(places.get(position).getImageUrl()).into(holder.placeImage);

     //   Toast.makeText(context,places.get(position).getName()+"",Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {

        return places.size();
    }


    public class ViewHolder   extends RecyclerView.ViewHolder{

        private TextView name,description;
        private ImageView placeImage;
        public ViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.place_name);
            description = (TextView)view.findViewById(R.id.place_description);
            placeImage=(ImageView)view.findViewById(R.id.imageViewPlace);

        }
    }

}


