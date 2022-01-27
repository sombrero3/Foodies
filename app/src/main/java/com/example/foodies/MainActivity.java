package com.example.foodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.foodies.model.Model;

public class MainActivity extends AppCompatActivity {
    NavController navCtl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    navCtl.navigate(R.id.action_global_homeRestaurantListRvFragment);
                    return true;
                case R.id.main_menu_profile:
                    args.putString("userId", Model.instance.getSignedUser().getId());
                    navCtl.navigate(R.id.action_global_userProfileFragment,args);
                    return true;
                case R.id.main_menu_add_review:
                    args.putString("edit_space_reviewId", "");
                    navCtl.navigate(R.id.action_global_newReviewFragment,args);
                    return true;
                case R.id.main_menu_my_reviews:
                    args.putString("userId", Model.instance.getSignedUser().getId());
                    navCtl.navigate(R.id.action_global_userRestaurantListRvFragment);
                    return true;
                case R.id.main_menu_my_friends:
                    args.putString("userId", Model.instance.getSignedUser().getId());
                    navCtl.navigate(R.id.action_global_userListRvFragment,args);
                    return true;
                case R.id.main_menu_add_friend:
                    navCtl.navigate(R.id.action_global_addFriendFragment);
                    return true;
                case R.id.main_menu_friend_requests:
                    navCtl.navigate(R.id.action_global_friendRequestsFragment);
                    return true;
            }
        }
        else{
        return true;

        }
        return false;
    }
}
