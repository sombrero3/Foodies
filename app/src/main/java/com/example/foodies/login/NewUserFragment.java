package com.example.foodies.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foodies.R;
import com.example.foodies.model.Model;
import com.google.firebase.auth.FirebaseAuth;

public class NewUserFragment extends Fragment {

    ImageView upload;
    Button signUp;
    EditText firstNameEt,lastNameEt,passwordEt,emailEt;
    TextView signIn;
    ProgressBar progressBar;

    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);
        signUp = view.findViewById(R.id.new_user_sign_up_btn);
        signIn = view.findViewById(R.id.new_user_sign_in_tv);
        upload = view.findViewById(R.id.new_user_upload_img_iv);
        firstNameEt = view.findViewById(R.id.new_user_first_name_et);
        lastNameEt = view.findViewById(R.id.new_user_last_name_et);
        passwordEt = view.findViewById(R.id.new_user_password_et);
        emailEt = view.findViewById(R.id.new_user_email_et);
        progressBar = view.findViewById(R.id.new_user_prog);
        progressBar.setVisibility(View.INVISIBLE);
        signUp.setOnClickListener((v)->{
            registration();
        });

        signIn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(NewUserFragmentDirections.actionNewUserFragmentToRegisteredUserFragment());
        });

        return view;
    }

    private void registration() {
        String email = emailEt.getEditableText().toString().trim();
        String password =passwordEt.getEditableText().toString().trim();
        String firstName = firstNameEt.getEditableText().toString().trim();
        String lastName = lastNameEt.getEditableText().toString().trim();
        if(firstName.isEmpty()){
            firstNameEt.setError("First name is required");
            firstNameEt.requestFocus();
            return;
        }
        if(lastName.isEmpty()){
            lastNameEt.setError("Last name is required");
            lastNameEt.requestFocus();
            return;
        }
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
        if(password.length()<6){
            passwordEt.setError("Password must contain 6 or more characters");
            passwordEt.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        Model.instance.signUp(email,password,firstName,lastName,progressBar);
//        mAuth = FirebaseAuth.getInstance();
//        mAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            User user = new User(email,password,firstName,lastName);
//                            FirebaseDatabase.getInstance().getReference("Users")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful()){
//                                        Toast.makeText(getActivity(),"Successfully Registered",Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.INVISIBLE);
//                                    }else{
//                                        Toast.makeText(getActivity(),"Failed To Registered",Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
//                        }else{
//                            Toast.makeText(getActivity(),"Failed To Registered",Toast.LENGTH_LONG).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });
    }
}