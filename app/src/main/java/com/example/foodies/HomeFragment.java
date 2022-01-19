package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HomeFragment extends Fragment {

   TextView home,myReviews,myFriends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
       home = view.findViewById(R.id.home_home_tv);
       myReviews = view.findViewById(R.id.home_my_reviews_tv);
       myFriends = view.findViewById(R.id.home_my_friends_tv);



       home.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_homeRestaurantListRvFragment));
       myReviews.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_userRestaurantListRvFragment));
       myFriends.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_userListRvFragment));

        return view;
    }
}