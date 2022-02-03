package com.example.foodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.foodies.model.Model;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navCtl;
    Handler h = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        //----to use user validation put the next two lines in comment----//
//        Model.instance.setSignedUser(Model.instance.getUserById("1"));
//        Model.instance.setSignedFlag(true);
        //----------------------------------------------------------------//

        NavHost navHost = (NavHost) getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navCtl = navHost.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navCtl);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(!super.onOptionsItemSelected(item)){
            Bundle args = new Bundle();
            switch (item.getItemId()){
                case android.R.id.home:
                    navCtl.navigateUp();
                    return true;
                case R.id.main_menu_home:
                    Toast.makeText(this,"Home",Toast.LENGTH_SHORT).show();
                    navCtl.navigate(R.id.action_global_homeRestaurantListRvFragment);
                    return true;
                case R.id.main_menu_profile:
                    Toast.makeText(this,"My Profile",Toast.LENGTH_SHORT).show();
                    args.putString("userId", Model.instance.getSignedUser().getId());
                    navCtl.navigate(R.id.action_global_userProfileFragment,args);
                    return true;
                case R.id.main_menu_add_review:
                    Toast.makeText(this,"New Review",Toast.LENGTH_SHORT).show();
                    args.putString("edit_space_reviewId", "");
                    navCtl.navigate(R.id.action_global_newReviewFragment,args);
                    return true;
                case R.id.main_menu_my_reviews:
                    Toast.makeText(this,"My Reviews",Toast.LENGTH_SHORT).show();
                    args.putString("userId", Model.instance.getSignedUser().getId());
                    navCtl.navigate(R.id.action_global_userRestaurantListRvFragment,args);
                    return true;
                case R.id.main_menu_my_friends:
                    Toast.makeText(this,"My Friends",Toast.LENGTH_SHORT).show();
                    args.putString("userId", Model.instance.getSignedUser().getId());
                    navCtl.navigate(R.id.action_global_userListRvFragment,args);
                    return true;
                case R.id.main_menu_add_friend:
                    Toast.makeText(this,"New Friend",Toast.LENGTH_SHORT).show();
                    navCtl.navigate(R.id.action_global_addFriendFragment);
                    return true;
                case R.id.main_menu_friend_requests:
                    Toast.makeText(this,"My Friend Requests",Toast.LENGTH_SHORT).show();
                    navCtl.navigate(R.id.action_global_friendRequestsFragment);
                    return true;
                case R.id.main_menu_logout:
                    Toast.makeText(this,"Logging Out",Toast.LENGTH_SHORT).show();
                    //navCtl.navigate(R.id.action_global_friendRequestsFragment);
                    return true;
            }
        }
        else{
        return true;

        }
        return false;
    }




}
