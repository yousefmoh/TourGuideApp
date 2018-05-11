package com.example.dexter.tourguideapp;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dexter.tourguideapp.Adapters.AppDatabase;
import com.example.dexter.tourguideapp.Adapters.ExperiencesAdapter;
import com.example.dexter.tourguideapp.Adapters.NotesAdapter;
import com.example.dexter.tourguideapp.Models.NotesModel;
import com.example.dexter.tourguideapp.Models.UserModel;

import java.util.ArrayList;
import java.util.Arrays;

public class NotesActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private RecyclerView recyclerView;
    private ArrayList<UserModel> data=new ArrayList<>();
    private Toolbar toolbar;
    private NotesAdapter adapter;
    String PlaceId;
    private AppDatabase db;
    private FloatingActionButton addnote;
    int k=0;
    TextView notesText;
    String notes="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_layout);

         addnote=(FloatingActionButton)findViewById(R.id.addnote);


         db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "UsersNotesDb").allowMainThreadQueries().build();


        SetRecycleView();
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {



                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());


                final EditText noteEdittext = new EditText(view.getContext());
                noteEdittext.setHint("Please Add a note .");


                final LinearLayout Layout = new LinearLayout(view.getContext());
                Layout.setOrientation(LinearLayout.VERTICAL);

                Layout.addView(noteEdittext);

                alert.setMessage("Enter Your Experience");

                alert.setView(Layout);

                alert.setPositiveButton("Write ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String NoteText = noteEdittext.getText().toString();

                        UserModel userModel =new UserModel();
                         userModel.setNote(NoteText);
                        db.userDao().insertAll(userModel);

                        SetRecycleView();
                        Toast.makeText(view.getContext(), db.userDao().getAll().get(0).getNote(), Toast.LENGTH_SHORT).show();

                        // InsertExp(nameText, experienceText, PlaceId);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

                alert.show();














            }
        });





    }

    public  void  SetRecycleView()
    {

        data = new ArrayList<>(db.userDao().getAll());

        adapter = new NotesAdapter(data,getApplicationContext());

        recyclerView = (RecyclerView)findViewById(R.id.notes_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }


    public  void  GetExtra(Bundle bundle)
    {
        if (bundle == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                PlaceId=null;

            } else {


                PlaceId= extras.getString("PlaceId");


            }
        } else {
            PlaceId= (String) bundle.getSerializable("PlaceId");


        }
    }






    @Override
    protected void onResume() {
        super.onResume();



    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                //Toast.makeText(this,"ll",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

























}
