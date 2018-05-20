package com.example.dexter.tourguideapp.Services;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dexter.tourguideapp.Models.UserModel;

import java.util.List;

/**
 * Created by dexter on 5/4/2018.
 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM UserModel")
    List<UserModel> getAll();

    @Query("SELECT * FROM UserModel WHERE uid IN (:userIds)")
    List<UserModel> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM UserModel WHERE note LIKE :note LIMIT 1")
    UserModel findByNote(String note);

    @Insert
    void insertAll(UserModel... userModels);

    @Delete
    void delete(UserModel userModel);

    @Query("Delete FROM UserModel")
    void  DeleteAll();




}
