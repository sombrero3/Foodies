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
import android.widget.TextView;

import com.example.foodies.AdaptersAndViewHolders.DishListAdapter;
import com.example.foodies.AdaptersAndViewHolders.FavoriteDishAdapter;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.AdaptersAndViewHolders.UserListAdapter;
import com.example.foodies.model.Dish;
import com.example.foodies.model.Model;
import com.example.foodies.model.Review;
import com.example.foodies.model.User;

import java.util.List;

public class UserProfileFragment extends Fragment {
    TextView nameTv,totalReviewsTv,totalRestaurantsTv;
    RecyclerView reviewsRv,friendsRv;
    List<User> friendsList;
    List<Review> reviewList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        User user = Model.instance.getSignedUser();
        friendsList = user.getFriendsList(); // temp, need to get userId by nav Args
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
        UserListAdapter userAdapter = new UserListAdapter(friendsList);
        friendsRv.setAdapter(userAdapter);

        favoriteDishAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Dish dish = Model.instance.getDishById(reviewList.get(position).getDishId());
                String dishName = dish.getName();
                String price = dish.getPrice();
                Log.d("TAG","dish clicked: " + dishName + " price: "+price );
              //  Navigation.findNavController(v).navigate(UserReviewsOnRestaurantRvFragmentDirections.actionUserReviewsOnRestaurantRvFragmentToReviewFragment(review.getId()));
            }
        });

        userAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String userName = friendsList.get(position).getLastName();
                Log.d("TAG","user's row clicked: " + userName);
                //Navigation.findNavController(v).navigate(UserListRvFragmentDirections.actionUserListRvFragmentToUserRestaurantListRvFragment(userList.get(position).getId()));
            }
        });

        return view;
    }
}