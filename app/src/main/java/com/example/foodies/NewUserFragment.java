package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class NewUserFragment extends Fragment {
     ImageView uploadIv;
     TextView invalidNameTv,invalidMailTv,invalidPassTv;
     Button signUpBtn;
     EditText emailEt,nameEt,passwordEt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);
        signUpBtn = view.findViewById(R.id.new_user_sign_up_btn);
        uploadIv = view.findViewById(R.id.new_user_upload_img_iv);
        emailEt = view.findViewById(R.id.new_user_email_et);
        nameEt = view.findViewById(R.id.new_user_name_et);
        passwordEt = view.findViewById(R.id.new_user_password_et);
        invalidNameTv = view.findViewById(R.id.new_user_invalid_email_tv);
        invalidMailTv = view.findViewById(R.id.new_user_invalid_email_tv);
        invalidPassTv = view.findViewById(R.id.new_user_invalid_password_tv);

        invalidNameTv.setVisibility(View.INVISIBLE);
        invalidMailTv.setVisibility(View.INVISIBLE);
        invalidPassTv.setVisibility(View.INVISIBLE);

        //---to activate user validation cancel all comments below-----//
//        signUpBtn.setOnClickListener((v)->{
//            boolean f = true;
//            while(f) {
//                String name = nameEt.getEditableText().toString();
//                String email = emailEt.getEditableText().toString();
//                String password = passwordEt.getEditableText().toString();
//                boolean flag = true;
//                if (name.equals("") || name.charAt(0) == ' ') {
//                    // must fill name field
//                    flag = false;
//                    invalidNameTv.setVisibility(View.VISIBLE);
//
//                }
//                if (email.charAt(0) == ' ') {
//                    //email can't start with space
//                    flag = false;
//                    invalidMailTv.setVisibility(View.VISIBLE);
//                }
//                if (password.equals("") || password.charAt(0) == ' ') {
//                    // invalid password
//                    flag = false;
//                    invalidPassTv.setVisibility(View.VISIBLE);
//                }
//                if (flag) {
//                    f= false;
//                    User user = new User(name, password, email);
//                    Model.instance.addUser(user);
//                    Model.instance.setSignedUser(user);
//                    Model.instance.setSignedFlag(true);
//                    Navigation.findNavController(v).navigate(NewUserFragmentDirections.actionNewUserFragmentToHomeFragment());
//                }
//            }
//        });
         //-------------------------------------------------------------//


        return view;
    }
}
//



