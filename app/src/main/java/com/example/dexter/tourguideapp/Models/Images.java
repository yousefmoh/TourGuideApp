package com.example.dexter.tourguideapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dexter on 4/14/2018.
 */


public  class Images {
    @SerializedName("image")

    String image;
    @SerializedName("id")

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
