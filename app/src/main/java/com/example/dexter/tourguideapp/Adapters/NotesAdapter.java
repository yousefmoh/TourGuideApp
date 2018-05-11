package com.example.dexter.tourguideapp.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dexter.tourguideapp.Models.ExperiencesModel;
import com.example.dexter.tourguideapp.Models.NotesModel;
import com.example.dexter.tourguideapp.Models.UserModel;
import com.example.dexter.tourguideapp.R;

import java.util.ArrayList;

/**
 * Created by Dexter on 2/17/2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private Context context;

    private ArrayList<UserModel> notes;
    String pos="0";

    public NotesAdapter(ArrayList<UserModel> notes, Context context) {
        this.notes = notes;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {



       // holder.name.setText(notes.get(position).getName());
        holder.note.setText(notes.get(position).getNote());


    }

    @Override
    public int getItemCount() {

        return notes.size();
    }


    public class ViewHolder   extends RecyclerView.ViewHolder{

        private TextView name,note;
        private CardView cardView;
        private ImageView placeImage;
        public ViewHolder(View view) {
            super(view);
            note = (TextView)view.findViewById(R.id.note_text);



        }
    }

}


