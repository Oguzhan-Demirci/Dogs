package com.rl.dogs.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rl.dogs.Model.Database.Dog;
import com.rl.dogs.Model.DogRepository;

import java.util.List;

public class DogViewModel extends AndroidViewModel {

    private DogRepository dogRepository;
    private LiveData<List<Dog>> allDogs;
    private boolean ifFragmentStartNeeded = true;

    public DogViewModel(@NonNull Application application) {
        super(application);
        dogRepository = new DogRepository(application);
        allDogs = dogRepository.getAllDogs();
    }

    public void setIfFragmentStartNeeded(boolean needed){
        this.ifFragmentStartNeeded = needed;
    }

    public boolean isFragmentStartNeeded(){
        return ifFragmentStartNeeded;
    }

    public void insert(Dog dog){
        dogRepository.insert(dog);
    }

    public void update(Dog dog){
        dogRepository.update(dog);
    }

    public void delete(Dog dog){
        dogRepository.delete(dog);
    }

    public void deleteAllDogs(){
        dogRepository.deleteAllDogs();
    }

    public LiveData<List<Dog>> getAllDogs(){
        return allDogs;
    }

    public void getAllDogsFromNet(){
        dogRepository.getAllDogsFromNet();
    }

    public LiveData<List<String>> getSubBreedsOfBreed(String breed){
        return dogRepository.getSubBreedsOfBreed(breed);
    }

    public void getPhotoByBreedFromNet(String breed){
        dogRepository.getPhotoByBreedFromNet(breed);
    }

    public void getPhotoBySubBreedFromNet(String breed, String subBreed){
        dogRepository.getPhotoBySubBreedFromNet(breed, subBreed);
    }

    public LiveData<Dog> getPhotos(String breed, String subBreed){
        return dogRepository.getPhotos(breed, subBreed);
    }

    public LiveData<Dog> getPhotosOfSubBreed(String breed, String subBreed){
        return dogRepository.getPhotosOfSubBreed(breed, subBreed);
    }

    public void insertPhoto(String breed, String picture){
        dogRepository.insertPhoto(breed, picture);
    }
}
