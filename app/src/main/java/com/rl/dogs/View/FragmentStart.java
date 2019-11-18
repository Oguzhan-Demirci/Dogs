package com.rl.dogs.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.rl.dogs.R;
import com.rl.dogs.ViewModel.DogViewModel;

public class FragmentStart extends Fragment {

    Handler handler;
    Runnable runnable;
    int progress = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_start, container, false);
        showProgressDialog(getContext());
        return rootView;
    }

    public void showProgressDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                progress += 20;
                if (progress <= 100) {
                    progressDialog.setProgress(progress);
                    handler.postDelayed(this, 1000);
                } else {
                    handler.removeCallbacks(this);
                    progressDialog.hide();
                    DogViewModel dogViewModel = ViewModelProviders.of(getActivity()).get(DogViewModel.class);
                    dogViewModel.setIfFragmentStartNeeded(false);
                    Navigation
                            .findNavController(getActivity().findViewById(R.id.main_container))
                            .navigate(R.id.action_fragmentStart2_to_fragmentBreeds2);
                }
            }

        };
        handler.post(runnable);
    }
}
