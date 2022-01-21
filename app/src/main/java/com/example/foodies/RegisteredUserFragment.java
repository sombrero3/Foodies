package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodies.model.Model;


public class RegisteredUserFragment extends Fragment {
    Button signInBtn, signUpBtn;
    TextView forgotTv, invalidDetaisTv;
    EditText nameEt, passwordEt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_registered_user, container, false);
       signInBtn = view.findViewById(R.id.RegisteredUser_signIn_btn);
       signUpBtn = view.findViewById(R.id.registered_user_signup_btn);
       forgotTv = view.findViewById(R.id.registered_user_forgot_tv);
       invalidDetaisTv = view.findViewById(R.id.registered_user_ivalid_details_tv);
       nameEt = view.findViewById(R.id.registered_user_name_et);
       passwordEt = view.findViewById(R.id.registered_user_password_et);


       //---to activate user validation cancel all comments below-----//
//       signUpBtn.setVisibility(View.INVISIBLE);
//       signUpBtn.setClickable(false);
//       invalidDetaisTv.setVisibility(View.INVISIBLE);
//       signUpBtn.setOnClickListener((v)->{
//           Navigation.findNavController(v).navigate(RegisteredUserFragmentDirections.actionRegisteredUserFragmentToNewUserFragment());
//       });
//       signInBtn.setOnClickListener((v)->{
//           if(Model.instance.confirmUserLogin(nameEt.getEditableText().toString(),passwordEt.getEditableText().toString())){
//
//               Navigation.findNavController(v).navigate(RegisteredUserFragmentDirections.actionRegisteredUserFragmentToHomeFragment());
//           }else{
//               invalidDetaisTv.setVisibility(View.VISIBLE);
//               signUpBtn.setVisibility(View.VISIBLE);
//               signUpBtn.setClickable(true);
//           }
//       });
        //-------------------------------------------------------------//

       return view;
    }
}