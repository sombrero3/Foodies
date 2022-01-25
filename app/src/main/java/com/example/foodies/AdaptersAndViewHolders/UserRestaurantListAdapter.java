package com.example.foodies.AdaptersAndViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.model.Model;
import com.example.foodies.model.Restaurant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.model.Model;
import com.example.foodies.model.Restaurant;
import com.example.foodies.model.User;

import java.util.List;

public class UserRestaurantListAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {
        OnItemClickListener listener;
        List<Restaurant> restaurantList;
        User user;
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        public UserRestaurantListAdapter(List<Restaurant> restaurantList,User user){
            this.user=user;
            this.restaurantList=restaurantList;
        }

        @NonNull
        @Override
        public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_row,parent,false);
            RestaurantViewHolder holder = new RestaurantViewHolder(view,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position ) {
            Restaurant restaurant = restaurantList.get(position);
            holder.nameTv.setText(restaurant.getName());
            holder.descriptionTv.setText("Friend and 20 other friend visited this text should be dynamic");
            String rating = Model.instance.getRestaurantRatingGivenByAUser(user,restaurantList.get(position).getId());
            Model.instance.setStarByRating(rating, holder.star1, holder.star2, holder.star3, holder.star4, holder.star5, holder.ratingTv);
        }

        @Override
        public int getItemCount() {
            return restaurantList.size();
        }



}
