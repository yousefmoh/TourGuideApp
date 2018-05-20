package com.example.dexter.tourguideapp.Adapters;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dexter.tourguideapp.Models.ExperiencesModel;
import com.example.dexter.tourguideapp.Models.NotesModel;
import com.example.dexter.tourguideapp.Models.UserModel;
import com.example.dexter.tourguideapp.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dexter on 2/17/2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private Context context;
    private AppDatabase db;
    private  Activity activity;
    private ArrayList<UserModel> notes;
    String pos="0";

    public NotesAdapter(ArrayList<UserModel> notes, Context context, AppDatabase db, Activity activity) {
        this.notes = notes;
        this.context=context;
        this.db=db;
        this.activity=activity;
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
        holder.note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(context,notes.get(position).getUid()+"",Toast.LENGTH_SHORT).show();
                DeleteNote(position);
            }
        });


    }

    public void removeAt(int position) {
        notes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, notes.size());
    }

    @Override
    public int getItemCount() {

        return notes.size();
    }

    private void DeleteNote(final int pos)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);





        alert.setMessage("Are You Sure that you want to delete the note !? ");
        alert.setPositiveButton("Delete ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                db.userDao().delete(notes.get(pos));
                removeAt( pos);

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();




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


