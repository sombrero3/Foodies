package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class UserNameFragment extends Fragment {

    Button testUse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_name, container, false);

        testUse = view.findViewById(R.id.UserName_test_btn);
        testUse.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_userName_to_userReviewsOnRestaurantFragment3));

        return view;
    }
}