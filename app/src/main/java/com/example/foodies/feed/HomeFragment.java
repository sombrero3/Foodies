package com.example.foodies.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.foodies.R;
import com.example.foodies.model.Model;
import com.example.foodies.model.User;

public class HomeFragment extends Fragment {

   TextView home,myReviews,myFriends,signInBtn,signUpBtn,myProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_home, container, false);
       home = view.findViewById(R.id.home_home_tv);
       myReviews = view.findViewById(R.id.home_my_reviews_tv);
       myFriends = view.findViewById(R.id.home_my_friends_tv);
       myProfile = view.findViewById(R.id.home_my_profile_tv);
       signInBtn = view.findViewById(R.id.home_signin_tv);
       signUpBtn = view.findViewById(R.id.home_signup_tv);

       if(Model.instance.isSignedFlag()) {
//           User user = Model.instance.getSignedUser();
//           home.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_homeRestaurantListRvFragment));
//           myReviews.setOnClickListener((v) -> {
//               Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToUserRestaurantListRvFragment(user.getId()));
//           });
//           myFriends.setOnClickListener((v) -> {
//               Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToUserListRvFragment(user.getId()));
//           });
//           myProfile.setOnClickListener((v)->{
//               Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToUserProfileFragment(user.getId()));
//           });
       }
       signInBtn.setOnClickListener((v)->{
           //Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToRegisteredUserFragment());
       });
       signUpBtn.setOnClickListener((v)->{
           //Navigation.findNavController(v).navigate(HomeFragmentDirections.actionHomeFragmentToNewUserFragment());
       });

        return view;
    }



}