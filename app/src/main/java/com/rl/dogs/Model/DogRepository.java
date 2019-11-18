package com.rl.dogs.Model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.rl.dogs.Model.Net.BreedImageResponse;
import com.rl.dogs.Model.Net.BreedListResponse;
import com.rl.dogs.Model.Net.DogApi;
import com.rl.dogs.Model.Net.IDogApi;
import com.rl.dogs.Model.Database.Dog;
import com.rl.dogs.Model.Database.DogDao;
import com.rl.dogs.Model.Database.DogDatabase;

import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogRepository {

    private static final String TAG = "DogRepository";

    private DogDao dogDao;
    private LiveData<List<Dog>> allDogs;

    private IDogApi iDogApi;

    public DogRepository(Application application){
        DogDatabase dogDatabase = DogDatabase.getInstance(application);
        this.dogDao = dogDatabase.dogDao();
        allDogs = dogDao.getAllDogs();
    }

    public void getAllDogsFromNet(){
        iDogApi = DogApi.getInstance().create(IDogApi.class);
        Call<BreedListResponse> breedCall = iDogApi.getAllBreeds();
        breedCall.enqueue(new Callback<BreedListResponse>() {
            @Override
            public void onResponse(Call<BreedListResponse> call, Response<BreedListResponse> response) {
                if (response.body().getStatus().equals("success")){
                    Set<String> breedSet = response.body().getMessage().keySet();
                    for (String breed : breedSet){
                        if (response.body().getMessage().get(breed).length == 0){
                            Dog dog = new Dog(breed, null, null);
                            insert(dog);
                        } else {
                            String[] subBreeds = response.body().getMessage().get(breed);
                            for (String subBreed : subBreeds){
                                Dog dog = new Dog(breed, subBreed, null);
                                insert(dog);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BreedListResponse> call, Throwable t) {
                Log.d(TAG, "call for fetching breed list failed.");
                t.getStackTrace();
            }
        });
    }

    public void getPhotoByBreedFromNet(String breed){
        iDogApi = DogApi.getInstance().create(IDogApi.class);
        Call<BreedImageResponse> breedCall = iDogApi.getImagesByBreed(breed);
        breedCall.enqueue(new Callback<BreedImageResponse>() {
            @Override
            public void onResponse(Call<BreedImageResponse> call, Response<BreedImageResponse> response) {
                String allImages = "";
                for (String part : response.body().getMessage()){
                    allImages += part + ";";
                }
                String finalAllImages = allImages;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        insertPhoto(breed, finalAllImages);
                    }
                }).start();
            }

            @Override
            public void onFailure(Call<BreedImageResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void getPhotoBySubBreedFromNet(String breed, String subBreed){
        iDogApi = DogApi.getInstance().create(IDogApi.class);
        Call<BreedImageResponse> breedCall = iDogApi.getImagesBySubBreed(breed, subBreed);
        breedCall.enqueue(new Callback<BreedImageResponse>() {
            @Override
            public void onResponse(Call<BreedImageResponse> call, Response<BreedImageResponse> response) {
                String allImages = "";
                for (String part : response.body().getMessage()){
                    allImages += part + ";";
                }
                String finalAllImages = allImages;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        insertPhotoOfSubBreed(breed, subBreed, finalAllImages);
                    }
                }).start();
            }

            @Override
            public void onFailure(Call<BreedImageResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void insertPhoto(String breed,  String picture){
        dogDao.insertPhoto(breed, picture);
    }

    public void insertPhotoOfSubBreed(String breed, String subBreed,  String picture){
        dogDao.insertPhotoOfSubBreed(breed, subBreed, picture);
    }

    public LiveData<Dog> getPhotos(String breed, String subBreed){
        return dogDao.getPhotos(breed);
    }

    public LiveData<Dog> getPhotosOfSubBreed(String breed, String subBreed){
        return dogDao.getPhotosOfSubBreed(breed, subBreed);
    }

    private Observer<String> getInsertImageObserver(String breed, String subBreed){
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                dogDao.insert(new Dog(breed, subBreed, s));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i(TAG, "images are inserted to database");
            }
        };
    }

    public void insert(Dog dog){
        new InsertDogAsyncTask(dogDao).execute(dog);
    }

    public void update(Dog dog){
        new UpdateDogAsyncTask(dogDao).execute(dog);
    }

    public void delete(Dog dog){
        new DeleteDogAsyncTask(dogDao).execute(dog);
    }

    public void deleteAllDogs(){
        new DeleteAllDogsAsyncTask(dogDao).execute();
    }

    public LiveData<List<Dog>> getAllDogs(){
        return allDogs;
    }

    public LiveData<List<String>> getSubBreedsOfBreed(String breed){
        return dogDao.getSubBreedsOfBreed(breed);
    }

    private static class InsertDogAsyncTask extends AsyncTask<Dog, Void, Void>{

        private DogDao dogDao;

        public InsertDogAsyncTask(DogDao dogDao){
            this.dogDao = dogDao;
        }

        @Override
        protected Void doInBackground(Dog... dogs) {
            dogDao.insert(dogs[0]);
            return null;
        }
    }

    private static class UpdateDogAsyncTask extends AsyncTask<Dog, Void, Void>{

        private DogDao dogDao;

        public UpdateDogAsyncTask(DogDao dogDao){
            this.dogDao = dogDao;
        }

        @Override
        protected Void doInBackground(Dog... dogs) {
            dogDao.update(dogs[0]);
            return null;
        }
    }

    private static class DeleteDogAsyncTask extends AsyncTask<Dog, Void, Void>{

        private DogDao dogDao;

        public DeleteDogAsyncTask(DogDao dogDao){
            this.dogDao = dogDao;
        }

        @Override
        protected Void doInBackground(Dog... dogs) {
            dogDao.delete(dogs[0]);
            return null;
        }
    }

    private static class DeleteAllDogsAsyncTask extends AsyncTask<Void, Void, Void>{

        private DogDao dogDao;

        public DeleteAllDogsAsyncTask(DogDao dogDao){
            this.dogDao = dogDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dogDao.deleteAllDogs();
            return null;
        }
    }
}
