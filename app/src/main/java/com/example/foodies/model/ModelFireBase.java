package com.example.foodies.model;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class ModelFireBase {
    FirebaseUser user;
    DatabaseReference usersReference;
    FirebaseAuth currentUser;
    String currentUserId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getAllUsers(/*Model.GetAllUsersListener listener*/){

    }
    public void addUser( User user, Model.AddUserListener listener){
        Map<String,Object> json = user.toJason();

// Add a new document with a generated ID
        db.collection("users")
                .document(user.getEmail())
                .set(json)
                .addOnSuccessListener(unused -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());

    }


    /**
    * Authentication
     * */
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public boolean isSignedIn(){
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return (currentUser != null);
    }

    public void signIn(String email, String password, String firstName, String lastName, ProgressBar progressBar) {
        Log.d("TAG", "signIn: "+email +" "+password+" "+mAuth );
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("TAG", "onComplete: succeed"+email +" "+password+" "+mAuth.getCurrentUser() );
                        }else{
                            Toast.makeText(progressBar.getContext(),"Failed To Registered 2",Toast.LENGTH_LONG).show();

                            Log.d("TAG", "onComplete failed: "+email +" "+password+" "+mAuth+" "+task.getException().toString() );
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void setCurrentUser(Model.setCurrentUserListener listener) {
        currentUser = FirebaseAuth.getInstance();
        String userUid = currentUser.getCurrentUser().getUid();
        usersReference = FirebaseDatabase.getInstance().getReference("Users");
        usersReference.child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                //if(user!=null) {
                listener.onComplete(user);
            //}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(MainActivity.this,"Something wrong happened",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getUserById(String id, Model.getUserByIdListener listener) {
        usersReference = FirebaseDatabase.getInstance().getReference("Users");
        usersReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user!=null) {
                    listener.onComplete(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(MainActivity.this,"Something wrong happened",Toast.LENGTH_LONG).show();
            }
        });
    }
//    public void signIn(String email, String password, String firstName, String lastName, ProgressBar progressBar) {
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
//                                        Toast.makeText(progressBar.getContext(), "Successfully Registered",Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.VISIBLE);
//                                    }else{
//                                        Toast.makeText(progressBar.getContext(),"Failed To Registered",Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
//                        }else{
//                            Toast.makeText(progressBar.getContext(),"Failed To Registered 2",Toast.LENGTH_LONG).show();
//
//                            Log.d("TAG", "onComplete: "+email +" "+password+" "+mAuth );
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });
//    }

}
