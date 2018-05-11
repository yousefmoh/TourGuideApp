package com.example.dexter.tourguideapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dexter on 2/17/2018.
 */

public class NotesModel {


    String notes;

    String id;


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}