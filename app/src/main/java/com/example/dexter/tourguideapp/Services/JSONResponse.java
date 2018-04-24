package com.example.dexter.tourguideapp.Services;


import com.example.dexter.tourguideapp.Models.SampleModel.places;

import java.util.ArrayList;

/**
 * Created by Dexter on 2/17/2018.
 */

public class JSONResponse {


    private  places [] places;


    public places[] getPlaces() {
        return places;
    }

    public void setPlaces(places[] places) {
        this.places = places;
    }
    /*
    private SampleModel [] samples;

    public SampleModel[] getSamples() {
        return samples;
    }

    public void setSamples(SampleModel[] samples) {
        this.samples = samples;
    }*/
}
