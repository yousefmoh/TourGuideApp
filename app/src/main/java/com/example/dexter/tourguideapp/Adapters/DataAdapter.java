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

import com.example.dexter.tourguideapp.MainActivity;
import com.example.dexter.tourguideapp.PlaceInformationActivity;
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
    String pos="0";

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
    public void onBindViewHolder(ViewHolder holder, final int position) {



        holder.name.setText(places.get(position).getName());
        holder.place_id.setText(places.get(position).getId());
        Picasso.with(context).load(places.get(position).getImageUrl()).into(holder.placeImage);

         holder.cardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(view.getContext(), PlaceInformationActivity.class);

                 Toast.makeText(context,places.get(position).getId()+"",Toast.LENGTH_SHORT).show();

                 intent.putExtra("ImageUrl",places.get(position).getImageUrl());
                 intent.putExtra("Description",places.get(position).getDescription());
                 intent.putExtra("Latitude",places.get(position).getLatitude());
                 intent.putExtra("Longitude",places.get(position).getLongitude());

                 view.getContext().startActivity(intent);

             }
         });
     //   Toast.makeText(context,places.get(position).getName()+"",Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {

        return places.size();
    }


    public class ViewHolder   extends RecyclerView.ViewHolder{

        private TextView name,place_id;
        private CardView cardView;
        private ImageView placeImage;
        public ViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.place_name);
            place_id = (TextView)view.findViewById(R.id.place_id);
            placeImage=(ImageView)view.findViewById(R.id.imageViewPlace);
            cardView=(CardView)view.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //Intent intent = new Intent(view.getContext(), PlaceInformationActivity.class);

                    //Toast.makeText(view.getContext(),pos+"",Toast.LENGTH_SHORT).show();

              //      Toast.makeText(view.getContext(),place_id.getText()+"",Toast.LENGTH_SHORT).show();

                    //view.getContext().startActivity(intent);
                }
            });


        }
    }

}


