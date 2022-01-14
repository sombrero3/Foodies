package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment{
     Button home,myReviews,myFriends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        myReviews = view.findViewById(R.id.Home_myreviews_btn);
        myFriends = view.findViewById(R.id.Home_myfreinds_btn);
             home = view.findViewById(R.id.Home_home_btn);

        return view;
    }
}