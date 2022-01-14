package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyReviewsFragment extends Fragment{

    Button add,search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_reviews, container, false);
        search = view.findViewById(R.id.MyReviews_search_img_btn);
           add = view.findViewById(R.id.MyReviews_add_img_view);

        return view;
    }
}