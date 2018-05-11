package com.example.dexter.tourguideapp.Adapters;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.dexter.tourguideapp.Models.UserModel;
import com.example.dexter.tourguideapp.Services.UserDao;

/**
 * Created by dexter on 5/4/2018.
 */

@Database(entities = {UserModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}