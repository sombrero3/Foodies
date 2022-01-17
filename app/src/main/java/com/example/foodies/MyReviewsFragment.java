package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class MyReviewsFragment extends Fragment {

    ImageButton menuMyR,plus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_reviews, container, false);

        menuMyR = view.findViewById(R.id.MyReview_menu_img_view);
        plus = view.findViewById(R.id.MyReviews_plus_img_view);

        plus.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_myReviewsFragment3_to_newReviewFragment3));
        menuMyR.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_myReviewsFragment3_to_homeFragment2));

   return view;
    }
}