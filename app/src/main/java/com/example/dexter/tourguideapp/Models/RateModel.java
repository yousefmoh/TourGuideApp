package com.example.dexter.tourguideapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dexter on 2/17/2018.
 */

public class RateModel {


        @SerializedName("count")
        private int count;

        @SerializedName("sum")
        private float sum;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}