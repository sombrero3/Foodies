package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class NewReviewFragment extends Fragment {

       TextView addLoc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_review, container, false);
        addLoc = view.findViewById(R.id.NewReview_addLoc_tv);
        addLoc.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_newReviewFragment3_to_mapFragment3));

       return view;
    }
}