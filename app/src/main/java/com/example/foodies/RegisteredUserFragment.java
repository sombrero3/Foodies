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


public class RegisteredUserFragment extends Fragment {

      Button signIn;
      TextView forgot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_registered_user, container, false);
      signIn = view.findViewById(R.id.registered_user_sign_btn);
      forgot = view.findViewById(R.id.registered_user_forgot_tv);


      signIn.setOnClickListener((v)->{
          Navigation.findNavController(v).navigate(RegisteredUserFragmentDirections.actionRegisteredUserFragmentToHomeFragment());
      });

   return view;
    }
}