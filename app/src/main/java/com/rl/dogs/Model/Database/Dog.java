package com.rl.dogs.Model.Database;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "table_dogs")
public class Dog {

    @PrimaryKey(autoGenerate = true)
    private int _id;
    @ColumnInfo()
    private String breed;
    private String subBreed;
    private String picture;

    public Dog(String breed, String subBreed, String picture){
        this.breed = breed;
        this.subBreed = subBreed;
        this.picture = picture;
    }

    public void set_id(int id){
        this._id = id;
    }

    public String getBreed(){
        return breed;
    }

    public String getSubBreed(){
        return subBreed;
    }

    public String getPicture(){
        return picture;
    }

    public int get_id() {
        return _id;
    }
}
