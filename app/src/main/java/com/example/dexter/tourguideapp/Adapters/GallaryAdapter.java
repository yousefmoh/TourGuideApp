package com.example.dexter.tourguideapp.Adapters;

import android.app.Activity;
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

import com.example.dexter.tourguideapp.Models.ImagesModel;
import com.example.dexter.tourguideapp.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Dexter on 2/17/2018.
 */

public class GallaryAdapter extends RecyclerView.Adapter<GallaryAdapter.ViewHolder> {

    private Context context;
    private  Activity activity;
    private ArrayList<ImagesModel> images;
    String pos="0";

    public GallaryAdapter(ArrayList<ImagesModel> notes, Context context, Activity activity) {
        this.images = notes;
        this.context=context;
        this.activity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallary_card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {



        Picasso.with(context).load(images.get(position).getImage()).into(holder.placeImage);
        holder.placeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(context,images.get(position).getImage(),Toast.LENGTH_SHORT).show();
                ShowImage(images.get(position).getImage());
            }
        });

    }

    @Override
    public int getItemCount() {

        return images.size();
    }


    public class ViewHolder   extends RecyclerView.ViewHolder{

        private TextView name,note;
        private CardView cardView;
        private ImageView placeImage;
        public ViewHolder(View view) {
            super(view);
            placeImage = (ImageView) view.findViewById(R.id.imageViewPlace);



        }
    }


    private void ShowImage(String path)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);

        final PhotoView photoView = new PhotoView(activity);

        Picasso.with(activity)
                .load(path)
                .into(photoView);

        final RelativeLayout Layout = new RelativeLayout(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Layout.setLayoutParams(params);
        photoView.setScaleType(ImageView.ScaleType.FIT_XY);

        photoView.setLayoutParams(params);
        photoView.getLayoutParams().height = params.height;
        photoView.getLayoutParams().width = params.width;

        // Layout.getLayoutParams().height = 400;
        //Layout.getLayoutParams().width = 400;

        Layout.addView(photoView);
        alert.setView(Layout);


        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show().getWindow().setLayout(params.width,params.height);;




    }

}


