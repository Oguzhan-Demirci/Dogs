package com.rl.dogs.Model.Net;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IDogApi {

    @GET("breeds/list/all")
    Call<BreedListResponse> getAllBreeds();

    @GET("breed/{breed}/list")
    Call<List<String>> getSubBreedsByBreed(@Path("breed") String breed);

    @GET("breed/{breed}/images")
    Call<BreedImageResponse> getImagesByBreed(@Path("breed") String breed);

    @GET("breed/{breed}/{subBreed}/images")
    Call<BreedImageResponse> getImagesBySubBreed(@Path("breed") String breed, @Path("subBreed") String subBreed);
}
