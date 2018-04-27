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

import com.example.dexter.tourguideapp.Models.ExperiencesModel;
import com.example.dexter.tourguideapp.Models.SampleModel.places;
import com.example.dexter.tourguideapp.PlaceInformationActivity;
import com.example.dexter.tourguideapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dexter on 2/17/2018.
 */

public class ExperiencesAdapter extends RecyclerView.Adapter<ExperiencesAdapter.ViewHolder> {

    private Context context;

    private ArrayList<ExperiencesModel> experiences;
    String pos="0";

    public ExperiencesAdapter(ArrayList<ExperiencesModel> experiences, Context context) {
        this.experiences = experiences;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.experiences_card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {



        holder.name.setText(experiences.get(position).getName());
        holder.exp.setText(experiences.get(position).getExperiences());


    }

    @Override
    public int getItemCount() {

        return experiences.size();
    }


    public class ViewHolder   extends RecyclerView.ViewHolder{

        private TextView name,exp;
        private CardView cardView;
        private ImageView placeImage;
        public ViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.username);
            exp = (TextView)view.findViewById(R.id.experiences_text);



        }
    }

}


