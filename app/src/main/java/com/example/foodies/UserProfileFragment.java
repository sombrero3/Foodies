package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodies.AdaptersAndViewHolders.FavoriteDishAdapter;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.AdaptersAndViewHolders.UserAdapter;
import com.example.foodies.model.Dish;
import com.example.foodies.model.Model;
import com.example.foodies.model.Review;
import com.example.foodies.model.User;

import java.util.List;

public class UserProfileFragment extends Fragment {
    TextView nameTv,totalReviewsTv,totalRestaurantsTv;
    Button addFriendBtn,allReviewsBtn;
    RecyclerView reviewsRv,friendsRv;
    List<User> friendsList;
    List<Review> reviewList;
    boolean flagRequest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        String userId = UserProfileFragmentArgs.fromBundle(getArguments()).getUserId();
        User user = Model.instance.getUserById(userId);

        friendsList = user.getFriendsList();
        friendsList.remove(Model.instance.getSignedUser());


        reviewList = Model.instance.getUserHighestRatingReviewsByUserId(user.getId());

        reviewsRv = view.findViewById(R.id.user_profile_favorit_dishes_rv);
        reviewsRv.setHasFixedSize(true);
        RecyclerView.LayoutManager horizontalLayout = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        reviewsRv.setLayoutManager(horizontalLayout);
        FavoriteDishAdapter favoriteDishAdapter = new FavoriteDishAdapter(reviewList);
        reviewsRv.setAdapter(favoriteDishAdapter);

        friendsRv = view.findViewById(R.id.user_profile_friends_rv);
        friendsRv.setHasFixedSize(true);
        RecyclerView.LayoutManager horizontalLayout2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        friendsRv.setLayoutManager(horizontalLayout2);
        UserAdapter userAdapter = new UserAdapter(friendsList);
        friendsRv.setAdapter(userAdapter);

        nameTv = view.findViewById(R.id.user_profile_name_tv);
        totalRestaurantsTv =view.findViewById(R.id.user_profile_total_restaurants_num_tv);
        totalReviewsTv = view.findViewById(R.id.user_profile_total_reviews_num_tv);
        addFriendBtn = view.findViewById(R.id.user_profile_add_friend_btn);
        allReviewsBtn = view.findViewById(R.id.user_profile_all_reviews_btn);

        favoriteDishAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Dish dish = Model.instance.getDishById(reviewList.get(position).getDishId());
                String dishName = dish.getName();
                String price = dish.getPrice();
                Log.d("TAG","dish clicked: " + dishName + " price: "+price );
                Navigation.findNavController(v).navigate(UserProfileFragmentDirections.actionUserProfileFragmentToReviewFragment2(reviewList.get(position).getId()));
            }
        });

        userAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String userName = friendsList.get(position).getLastName();
                Log.d("TAG","user's row clicked: " + userName);
                Navigation.findNavController(v).navigate(UserProfileFragmentDirections.actionUserProfileFragmentSelf(friendsList.get(position).getId()));
            }
        });

        nameTv.setText(user.getFirstName()+ " "+ user.getLastName());
        totalReviewsTv.setText("Posted total of "+user.getTotalReviews()+" reviews");
        totalRestaurantsTv.setText("Posted reviews on "+user.getTotalRestaurantsVisited()+" restaurants");

        String signedUserId = Model.instance.getSignedUser().getId();

        if(userId.equals(signedUserId)) {
            addFriendBtn.setOnClickListener((v) -> {
                Navigation.findNavController(v).navigate(UserProfileFragmentDirections.actionUserProfileFragmentToAddFriendFragment());
            });
        }else if(!Model.instance.getSignedUser().getFriendsList().contains(Model.instance.getUserById(userId))){
            addFriendBtn.setText("Send friend request");
            flagRequest =false;
            addFriendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!flagRequest) {
                        addFriendBtn.setText("cancel friend request");
                        flagRequest = true;
                    }else{
                        addFriendBtn.setText("Send friend request");
                        flagRequest = false;
                    }
                }
            });
        }

        if(signedUserId.equals(userId)){
            allReviewsBtn.setText("My reviews");
        }else{
            allReviewsBtn.setText("Check out all "+user.getFirstName()+"'s reviews");
        }

        if(Model.instance.getSignedUser().getFriendsList().contains(user)){
            addFriendBtn.setVisibility(View.INVISIBLE);
            addFriendBtn.setClickable(false);
        }
        allReviewsBtn.setOnClickListener((v)-> {
            Navigation.findNavController(v).navigate(UserProfileFragmentDirections.actionUserProfileFragmentToUserRestaurantListRvFragment(userId));
        });


        return view;
    }
}