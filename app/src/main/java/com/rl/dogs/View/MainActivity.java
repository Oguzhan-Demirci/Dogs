package com.rl.dogs.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.rl.dogs.R;
import com.rl.dogs.ViewModel.DogViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DogViewModel dogViewModel = ViewModelProviders.of(this).get(DogViewModel.class);
        if (dogViewModel.isFragmentStartNeeded())
            Navigation.findNavController(findViewById(R.id.main_container)).navigate(R.id.fragmentStart2);
        else
            Navigation.findNavController(findViewById(R.id.main_container)).navigate(R.id.fragmentBreeds2);
    }
}
