package com.example.dexter.tourguideapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Dexter on 2/17/2018.
 */

public class SampleModel {

    private ArrayList<places> places;

    public ArrayList<places> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<places> places) {
        this.places = places;
    }

    // private String api;


    public class places {

        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;
        @SerializedName("image")
        private  String ImageUrl;

        public String getImageUrl() {
            return ImageUrl;
        }

        public void setImageUrl(String imageUrl) {
            ImageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }
}