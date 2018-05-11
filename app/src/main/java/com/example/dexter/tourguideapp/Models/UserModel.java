package com.example.dexter.tourguideapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by dexter on 5/4/2018.
 */

@Entity
public class UserModel {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "note")
    private String note;

   // @ColumnInfo(name = "last_name")
   // private String lastName;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

// Getters and setters are ignored for brevity,
    // but they're required for Room to work.
}
