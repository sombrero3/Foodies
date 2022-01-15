package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyReviewsFragment extends Fragment{

    ImageView add,search,menu,location;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_reviews, container, false);
          search = view.findViewById(R.id.MyReviews_search_img_view);
             add = view.findViewById(R.id.MyReviews_add_img_view);
           menu = view.findViewById(R.id.NewReview_menu_img_view);
            location = view.findViewById(R.id.MyReviews_location_img_view);

        return view;
    }
}