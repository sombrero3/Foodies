package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class NewUserFragment extends Fragment {

    ImageView upload;
    Button signUp;
    EditText email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_user, container, false);
        signUp = view.findViewById(R.id.new_user_sign_up_btn);
        upload = view.findViewById(R.id.new_user_upload_img_iv);
        email = view.findViewById(R.id.new_user_email_et);

        signUp.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(NewUserFragmentDirections.actionNewUserFragmentToHomeFragment());
        });

        return view;
    }
}