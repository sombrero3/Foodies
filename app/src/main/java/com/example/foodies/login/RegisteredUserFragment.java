package com.example.foodies.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foodies.R;
import com.example.foodies.feed.MainActivity;
import com.example.foodies.model.Model;

import java.util.regex.Pattern;


public class RegisteredUserFragment extends Fragment {
    Button loginBtn, signUpBtn;
    TextView forgotTv, invalidDetaisTv;
    EditText emailEt, passwordEt;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_registered_user, container, false);
       loginBtn = view.findViewById(R.id.RegisteredUser_signIn_btn);
       signUpBtn = view.findViewById(R.id.registered_user_signup_btn);
       forgotTv = view.findViewById(R.id.registered_user_forgot_tv);
       invalidDetaisTv = view.findViewById(R.id.registered_user_ivalid_details_tv);
       emailEt = view.findViewById(R.id.registered_user_email_et);
       passwordEt = view.findViewById(R.id.registered_user_password_et);
       progressBar = view.findViewById(R.id.registered_user_prog);

       progressBar.setVisibility(View.INVISIBLE);
       invalidDetaisTv.setVisibility(View.INVISIBLE);

        signUpBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(RegisteredUserFragmentDirections.actionRegisteredUserFragmentToNewUserFragment());
        });

       loginBtn.setOnClickListener((v)->{
           registeration();



//           if(Model.instance.confirmUserLogin(email,password)){
//
//               toFeedActivity();
//           }else{
//               invalidDetaisTv.setVisibility(View.VISIBLE);
//               signUpBtn.setVisibility(View.VISIBLE);
//           }

       });

       return view;
    }

    private void registeration() {
        String email = emailEt.getEditableText().toString();
        String password =passwordEt.getEditableText().toString();
        if(email.isEmpty()){
            emailEt.setError("Email address is required");
            emailEt.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEt.setError("Please provide valid email");
            emailEt.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordEt.setError("Password is required");
            passwordEt.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
    }

    private void toFeedActivity() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}