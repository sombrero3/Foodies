package com.example.foodies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {
    NavController navCtl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHost navHost = (NavHost) getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navCtl = navHost.getNavController();

        NavigationUI.setupActionBarWithNavController(this, navCtl);
        // NewUserFragment newUserFragment = (NewUserFragment) getSupportFragmentManager().findFragmentById(R.id.NewUser_container_frag_);

    }

    //@Override
 //   public boolean onCreateOptionsMenu(Menu menu) {
   //      super.onCreateOptionsMenu(menu);
   //      getMenuInflater().inflate(R.menu.main_menu,menu);
     //    return  true;
  //  }
}
