package com.example.dexter.tourguideapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dexter on 2/17/2018.
 */

public class ExperiencesModel {

    @SerializedName("name")
     String name;

    @SerializedName("experiences")
    String experiences;

    @SerializedName("id")
    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExperiences() {
        return experiences;
    }

    public void setExperiences(String experiences) {
        this.experiences = experiences;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}