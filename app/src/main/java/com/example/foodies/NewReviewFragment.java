package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewReviewFragment extends Fragment{

        ImageView menu;
        Button uploadImages,postReview;
        TextView addLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_review, container, false);
        addLocation = view.findViewById(R.id.NewReview_addLoc_tv);
        uploadImages = view.findViewById(R.id.NewReview_upload_btn);
        postReview = view.findViewById(R.id.NewReview_postRev_btn);
        menu = view.findViewById(R.id.NewReview_menu_img_view);

        return view;
    }
}