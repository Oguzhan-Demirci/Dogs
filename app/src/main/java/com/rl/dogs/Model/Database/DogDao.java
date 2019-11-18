package com.rl.dogs.Model.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Dog dog);


    @Query("UPDATE table_dogs SET picture = :picture WHERE breed = :breed AND subBreed IS NULL")
    void insertPhoto(String breed, String picture);

    @Query("UPDATE table_dogs SET picture = :picture WHERE breed = :breed AND subBreed = :subBreed")
    void insertPhotoOfSubBreed(String breed, String subBreed, String picture);

    @Update
    void update(Dog dog);

    @Delete
    void delete(Dog dog);

    @Query("DELETE FROM table_dogs")
    void deleteAllDogs();

    @Query("SELECT * FROM table_dogs")
    LiveData<List<Dog>> getAllDogs();

    @Query("SELECT subBreed FROM table_dogs WHERE breed = :breed")
    LiveData<List<String>> getSubBreedsOfBreed(String breed);

    @Query("SELECT * FROM table_dogs WHERE breed = :breed AND subBreed IS NULL")
    LiveData<Dog> getPhotos(String breed);

    @Query("SELECT * FROM table_dogs WHERE breed = :breed AND subBreed = :subBreed")
    LiveData<Dog> getPhotosOfSubBreed(String breed, String subBreed);
}
