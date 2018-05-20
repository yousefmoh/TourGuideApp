package com.example.dexter.tourguideapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.IpCons;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.example.dexter.tourguideapp.Adapters.GallaryAdapter;
import com.example.dexter.tourguideapp.Adapters.NotesAdapter;
import com.example.dexter.tourguideapp.Models.ImagesModel;
import com.example.dexter.tourguideapp.Models.ResponseModel;
import com.example.dexter.tourguideapp.Services.RequestInterface;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dexter on 3/2/2018.
 */

public class PlaceInformationActivity extends AppCompatActivity {
    PhotoView placeimage;
    TextView placeDescription, placeName, placeAddress, placePhone;
    Button WriteExp, AddPhotoBtn;
    FloatingActionButton mapFap, expFap;
    ArrayList<String> images;


    String url, description, Latitude, Longitude, PlaceId, name, address, phone;
    String _path;
    private ProgressDialog progressDialog,LoadDataDailog;

    RecyclerView gallery_recycler_view;
    ArrayList<ImagesModel> gallarydData=new ArrayList<>();
    GallaryAdapter gallaryAdapter;//=new GallaryAdapter()


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placelayout);

        LoadDataDailog = new ProgressDialog(this);
        LoadDataDailog.setTitle("Loading Data :) ");
        LoadDataDailog.setMessage("Please Wait...");
        LoadDataDailog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        LoadDataDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        LoadDataDailog.show();



        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File :) ");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

     //   placeimage = (PhotoView) findViewById(R.id.imagePlace);


        placeDescription = (TextView) findViewById(R.id.descriptionPlace);
        placeName = (TextView) findViewById(R.id.place_nameTxt);
        placeAddress = (TextView) findViewById(R.id.address_txt);
        placePhone = (TextView) findViewById(R.id.phone_txt);
        mapFap = (FloatingActionButton) findViewById(R.id.mapFap);
        expFap = (FloatingActionButton) findViewById(R.id.expFap);
        WriteExp = (Button) findViewById(R.id.WriteExp);
        AddPhotoBtn = (Button) findViewById(R.id.AddPhoto);
        gallery_recycler_view=(RecyclerView)findViewById(R.id.gallery_recycler_view);
       // ImagePicker.create(this) // Activity or Fragment
         //       .start();

        AddPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startWithIntent();
                start();
                /*
                new ChooserDialog().with(view.getContext())
                        .withFilter(false, false, "jpg", "jpeg", "png")
                        .withStartFile(_path)
                        .withResources(R.string.title_choose, R.string.title_choose, R.string.dialog_cancel)
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {
                                Toast.makeText(PlaceInformationActivity.this, path, Toast.LENGTH_SHORT).show();
                                uploadFile(path, getRandomName());
                            }
                        })
                        .build()
                        .show();*/

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
                final LinearLayout Layout = new LinearLayout(view.getContext());
                Layout.setOrientation(LinearLayout.VERTICAL);
                Layout.addView(nameEditText);
                Layout.addView(expEdittext);
                alert.setMessage("Enter Your Experience");
                alert.setView(Layout);
                alert.setPositiveButton("Write ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String experienceText = expEdittext.getText().toString();
                        String nameText = nameEditText.getText().toString();
                        if(nameText.trim().isEmpty())
                            nameText="Anonymous";
                        InsertExp(nameText, experienceText, PlaceId);
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
            if (extras == null) {
                url = null;
                PlaceId = null;
                description = null;
                Latitude = null;
                name = null;
                address = null;
                Longitude = null;
                images = null;
            } else {

                url = extras.getString("ImageUrl");
                description = extras.getString("Description");
                PlaceId = extras.getString("PlaceId");
                Latitude = extras.getString("Latitude");
                Longitude = extras.getString("Longitude");
                name = extras.getString("name");
                address = extras.getString("address");
                phone = extras.getString("phone");


            }
        } else {
            url = (String) savedInstanceState.getSerializable("ImageUrl");
            description = (String) savedInstanceState.getSerializable("Description");
            Latitude = (String) savedInstanceState.getSerializable("Latitude");
            Longitude = (String) savedInstanceState.getSerializable("Longitude");
            name = (String) savedInstanceState.getSerializable("name");
            address = (String) savedInstanceState.getSerializable("address");
            phone = (String) savedInstanceState.getSerializable("phone");


        }


      // Picasso.with(this).load(url).into(placeimage);
      // final PhotoView photoView = findViewById(R.id.imagePlace);

      //  Picasso.with(this)
         //       .load(url)
           //     .into(photoView);
        placeDescription.setText(description);
        placeAddress.setText(address);
        placeName.setText(name);
        placePhone.setText(phone);
        loadGallary();


        placePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                //callIntent.setData(Uri.parse(phone));
                callIntent.setData(Uri.parse("tel:"+phone));
                if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

            }
        });

/*
        placeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());

                final PhotoView photoView = new PhotoView(view.getContext());

                Picasso.with(view.getContext())
                        .load(url)
                        .into(photoView);

                final RelativeLayout Layout = new RelativeLayout(view.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
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


                /*
                Intent intent = new Intent(view.getContext(), GallaryActivity.class);
                intent.putExtra("PlaceId", PlaceId);
                view.getContext().startActivity(intent);///
            }
        });

        */
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






    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            uploadFile(image.getPath(), getRandomName());

       //     Toast.makeText(this,image.getPath()+"",Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void startWithIntent() {
        startActivityForResult(ImagePicker.create(this)
                .single()
                .returnMode(ReturnMode.ALL)
                .getIntent(this), IpCons.MODE_SINGLE);
    }


    public void start() {
        ImagePicker imagePicker = ImagePicker.create(this);
        imagePicker.limit(1) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera")   // captured image directory name ("Camera" folder by default)
                .imageFullDirectory(Environment.getExternalStorageDirectory().getPath()) // can be full path
                .start(); // start image picker activity with request code
    }

    protected String getRandomName() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    private String  uploadFile(String path, final String filename)
    {
        progressDialog.show(); // Display Progress Dialog

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = new OkHttpClient();

        Map<String, RequestBody> map = new HashMap<>();
        File file = new File(path);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        final String extension = file.getName().split("\\.")[1];

        map.put("file\"; filename=\"" + filename+ "."+extension + "\"", requestBody);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://snap-project.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);

        Call<ResponseModel> call = request.uploadFile(map,Integer.parseInt(PlaceId));
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel responsem=response.body();
                Toast.makeText(getApplicationContext(), responsem.getMessage()+"",Toast.LENGTH_SHORT).show();

                progressDialog.dismiss(); // Display Progress Dialog

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage()+"",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss(); // Display Progress Dialog


            }
        });


        return  "";

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


    private void loadGallary( ){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://snap-project.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RequestInterface request = retrofit.create(RequestInterface.class);

        Call<List<ImagesModel>> call = request.getImages(Integer.parseInt(PlaceId));
        call.enqueue(new Callback<List<ImagesModel>>() {
            @Override
            public void onResponse(Call<List<ImagesModel>> call, Response<List<ImagesModel>> response) {
                List<ImagesModel> jsonResponse = response.body();
                gallarydData = new ArrayList<>(jsonResponse);
                SetGallaryRecycleView();
             //   setData();


            }

            @Override
            public void onFailure(Call<List<ImagesModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    public  void  SetGallaryRecycleView()
    {


        gallaryAdapter = new GallaryAdapter(gallarydData,getApplicationContext(),this);

        gallery_recycler_view.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        gallery_recycler_view.setLayoutManager(layoutManager);
        gallery_recycler_view.setAdapter(gallaryAdapter);
        LoadDataDailog.dismiss();


    }


}
