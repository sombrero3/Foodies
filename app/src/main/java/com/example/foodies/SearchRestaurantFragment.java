package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SearchRestaurantFragment extends Fragment {

       Button testSea;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_restaurant, container, false);

        testSea =view.findViewById(R.id.SearchRestaurant_test_btn);
        testSea.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_searchRestaurantFragment3_to_restaurantPageFragment3));

        return view;
    }
}