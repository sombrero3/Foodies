package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SectionIndexer;

public class NewUserFragment extends Fragment {

   // ImageView upload;
  // Button signUp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);
     // upload = view.findViewById(R.id.NewUser_upload_img_view);
     // signUp = view.findViewById(R.id.NewUser_sign_btn);

        return view;
    }
}

