package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SectionIndexer;

public class NewUserFragment extends Fragment {

       ImageView upload;
       ImageButton signUp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);
        //    ImageButton signUp =View.findViewById(R.id.NewUzer_sign_btn);
        //   signUp.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_newUserFragment_to_homeFragment));
        upload = view.findViewById(R.id.NewUser_upload_img_view);
        signUp = view.findViewById(R.id.NewUser_sign_btn);

        return view;
    }
}



