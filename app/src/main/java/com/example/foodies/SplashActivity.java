package com.example.foodies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import com.example.foodies.feed.MainActivity;
import com.example.foodies.login.LoginActivity;
import com.example.foodies.model.Model;
import com.example.foodies.model.User;
import com.google.firebase.FirebaseApp;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseApp.initializeApp(this);

        Model.instance.executor.execute(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(Model.instance.isSignedIn()){
                Model.instance.mainThread.post(()->{
                    Model.instance.setCurrentUser(new Model.setCurrentUserListener() {
                        @Override
                        public void onComplete(User user) {
                            toFeedActivity();
                        }
                    });

                });
            }else{
                Model.instance.mainThread.post(()-> {
                    toLoginActivity();
                });
            }
        });
    }

    private void toLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void toFeedActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}