package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HomeFragment extends Fragment {

   //TextView home,myReviews,myFriends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
      // home = view.findViewById(R.id.Home_home_tv);
     //  myReviews = view.findViewById(R.id.Home_myReviews_tv);
      // myFriends = view.findViewById(R.id.Home_myFriends_tv);

        return view;
    }
}