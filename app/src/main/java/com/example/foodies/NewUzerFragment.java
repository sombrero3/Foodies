package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

    public class NewUzerFragment extends Fragment{

        ImageButton upload;
        Button signUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_uzer, container, false);
         upload = view.findViewById(R.id.NewUzer_upload_img_view);
         signUp = view.findViewById(R.id.NewUzer_sign_btn);

        return view;
    }
}