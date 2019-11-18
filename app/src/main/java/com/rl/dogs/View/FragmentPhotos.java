package com.rl.dogs.View;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.rl.dogs.Model.Database.Dog;
import com.rl.dogs.R;
import com.rl.dogs.ViewModel.DogViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentPhotos extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String breed = getArguments().getString("breed");
        String subBreed = getArguments().getString("subBreed");
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        PhotoAdapter photoAdapter = new PhotoAdapter();
        recyclerView.setAdapter(photoAdapter);

        if (subBreed != null){
            DogViewModel dogViewModel = ViewModelProviders.of(this).get(DogViewModel.class);
            dogViewModel.getPhotosOfSubBreed(breed, subBreed).observe(this, new Observer<Dog>() {
                @Override
                public void onChanged(Dog dog) {
                    if (dog.getPicture() == null) {
                        if (subBreed == null)
                            dogViewModel.getPhotoByBreedFromNet(breed);
                        else
                            dogViewModel.getPhotoBySubBreedFromNet(breed, subBreed);
                    } else {
                        List<String> photos = new ArrayList<>();
                        photos = Arrays.asList(dog.getPicture().split(";"));
                        photoAdapter.setPhotoURLs(photos);
                    }
                }
            });
        } else {
            DogViewModel dogViewModel = ViewModelProviders.of(this).get(DogViewModel.class);
            dogViewModel.getPhotos(breed, subBreed).observe(this, new Observer<Dog>() {
                @Override
                public void onChanged(Dog dog) {
                    if (dog.getPicture() == null) {
                        if (subBreed == null)
                            dogViewModel.getPhotoByBreedFromNet(breed);
                        else
                            dogViewModel.getPhotoBySubBreedFromNet(breed, subBreed);
                    } else {
                        List<String> photos = new ArrayList<>();
                        photos = Arrays.asList(dog.getPicture().split(";"));
                        photoAdapter.setPhotoURLs(photos);
                    }
                }
            });
        }
        /*dvm.getAllDogs().observe(this, new Observer<List<Dog>>() {
            @Override
            public void onChanged(List<Dog> dogs) {
                ArrayList<String> photos = new ArrayList<>();
                for (Dog dog: dogs){
                    if (dog.getPicture() != null)
                        photos.add(dog.getPicture());
                }
                if (photos.get(0) != null)
                    Toast.makeText(getContext(), photos.get(0), Toast.LENGTH_LONG).show();
            }
        });*/
        return rootView;
    }
}
