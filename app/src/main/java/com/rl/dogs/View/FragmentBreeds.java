package com.rl.dogs.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.rl.dogs.Model.Database.Dog;
import com.rl.dogs.ViewModel.DogViewModel;
import com.rl.dogs.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FragmentBreeds extends Fragment {

    private ExpandableListView.OnGroupClickListener onGroupClickListener;
    private ExpandableListView.OnChildClickListener onChildClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_breeds, container, false);
        ExpandableListView elv = rootView.findViewById(R.id.expandable_list_view);

        onGroupClickListener = new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.getExpandableListAdapter().getChildrenCount(groupPosition) > 0){
                    parent.expandGroup(groupPosition);
                    return true;
                }
                String breed = parent.getItemAtPosition(groupPosition).toString();
                Bundle bundle = new Bundle();
                bundle.putString("breed", breed);

                Navigation
                        .findNavController(getActivity().findViewById(R.id.main_container))
                        .navigate(R.id.action_fragmentBreeds2_to_fragmentPhotos3, bundle);

                return false;
            }
        };

        onChildClickListener = new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String breed = parent.getItemAtPosition(groupPosition).toString();
                String subBreed = parent.getExpandableListAdapter().getChild(groupPosition, childPosition).toString();

                Bundle bundle = new Bundle();
                bundle.putString("breed", breed);
                bundle.putString("subBreed", subBreed);

                Navigation
                        .findNavController(getActivity().findViewById(R.id.main_container))
                        .navigate(R.id.action_fragmentBreeds2_to_fragmentPhotos3, bundle);

                return false;
            }
        };

        DogViewModel dogViewModel = ViewModelProviders.of(this).get(DogViewModel.class);
        dogViewModel.getAllDogs().observe(this, new Observer<List<Dog>>() {
            @Override
            public void onChanged(List<Dog> dogs) {
                if (dogs.size() < 1)
                    dogViewModel.getAllDogsFromNet();
                else {
                    elv.setAdapter(new BreedAdapter(getContext(), getBreedList(dogs), getBreedMap(dogs)));
                    elv.setOnGroupClickListener(onGroupClickListener);
                    elv.setOnChildClickListener(onChildClickListener);
                }
            }
        });

        return rootView;
    }

    private List<String> getBreedList(List<Dog> dogs){
        Set<String> breedSet = new HashSet<>();

        for (Dog dog : dogs)
            breedSet.add(dog.getBreed());

        List<String> breedList = new ArrayList<>();
        breedList.addAll(breedSet);

        return breedList;
    }

    private Map<String, ArrayList<String>> getBreedMap(List<Dog> dogs){


        Map<String, ArrayList<String>> breedMap = new HashMap<>();
        Set<String> breedSet = new HashSet<>();

        for (Dog dog : dogs)
            breedSet.add(dog.getBreed());

        for (String breed: breedSet){
            ArrayList<String> subBreedList = new ArrayList<>();
            for (Dog dog: dogs){
                if (breed.equals(dog.getBreed()))
                    subBreedList.add(dog.getSubBreed());
            }
            breedMap.put(breed, subBreedList);
        }

        return breedMap;
    }

}
