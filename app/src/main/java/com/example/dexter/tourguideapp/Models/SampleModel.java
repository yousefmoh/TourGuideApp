package com.example.dexter.tourguideapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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

    public  class  images {
        String image;

        String id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
    public class places {


       private double Distance;




        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;

        @SerializedName("id")
        private  String id;

        @SerializedName("image")
        private  String ImageUrl;

        @SerializedName("latitude")
        private  String latitude;

        @SerializedName("longitude")
        private  String longitude;

        @SerializedName("phone")
        private  String phone;

        @SerializedName("address")
        private  String Address;


        public double getDistance() {
            return Distance;
        }

        public void setDistance(double distance) {
            Distance = distance;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        @SerializedName("images")
        private List<images> Images ;

        public List<images> getImages() {
            return Images;
        }

        public void setImages(List<images> images) {
            Images = images;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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