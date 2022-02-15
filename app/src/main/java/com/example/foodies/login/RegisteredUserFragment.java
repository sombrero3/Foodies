package com.example.foodies.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.foodies.R;
import com.example.foodies.feed.MainActivity;
import com.example.foodies.model.Model;
import com.example.foodies.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisteredUserFragment extends Fragment {
    Button loginBtn, signUpBtn;
    TextView forgotTv, invalidDetaisTv;
    EditText emailEt, passwordEt;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
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

        mAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(RegisteredUserFragmentDirections.actionRegisteredUserFragmentToNewUserFragment());
        });

       loginBtn.setOnClickListener((v)->{
           login();
       });

       forgotTv.setOnClickListener((v) -> {
           Navigation.findNavController(v).navigate(RegisteredUserFragmentDirections.actionRegisteredUserFragmentToForgotPasswordFragment());
       });
       return view;
    }

    private void login() {
        String email = emailEt.getEditableText().toString();
        String password =passwordEt.getEditableText().toString();
        if(email.isEmpty()){
            emailEt.setError("Email address is required");
            emailEt.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEt.setError("Please provide a valid email");
            emailEt.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordEt.setError("Password is required");
            passwordEt.requestFocus();
            return;
        }
        if(password.length()<6){
            passwordEt.setError("Password must contain 6 or more characters");
            passwordEt.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
//                    ///------in comment: verification with email-----///
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    if(user.isEmailVerified()) {
                        toFeedActivity();
//                    }else{

//                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(getActivity(),"Please check your email to verify your account!!",Toast.LENGTH_LONG).show();
//                                }else{
//                        Toast.makeText(getActivity(),"Something went wrong please try again!!",Toast.LENGTH_LONG).show();//                       progressBar.setVisibility(View.GONE);
//                                }
//                                progressBar.setVisibility(View.GONE);
//                            }
//                        });
//                    }
                }else{
                    Toast.makeText(getActivity(),"Failed To Login",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    private void toFeedActivity() {
        Model.instance.setCurrentUser(new Model.setCurrentUserListener() {
            @Override
            public void onComplete(User user) {
                progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}