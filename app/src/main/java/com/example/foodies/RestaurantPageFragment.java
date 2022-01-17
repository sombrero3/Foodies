package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RestaurantPageFragment extends Fragment {

    Button testRes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_page, container, false);

        testRes = view.findViewById(R.id.RestaurantPage_test_btn);
        testRes.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_restaurantPageFragment3_to_userReviewsOnRestaurantFragment3));
        return view;
    }
}