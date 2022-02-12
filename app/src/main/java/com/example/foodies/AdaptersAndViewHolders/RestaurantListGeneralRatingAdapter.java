package com.example.foodies.AdaptersAndViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.model.Model;
import com.example.foodies.model.Restaurant;

import java.util.List;

public class RestaurantListGeneralRatingAdapter extends RecyclerView.Adapter<RestaurantWithRatingViewHolder> {
    OnItemClickListener listener;
    List<Restaurant> restaurantList;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public RestaurantListGeneralRatingAdapter(List<Restaurant> restaurantList){
        this.restaurantList=restaurantList;
    }

    @NonNull
    @Override
    public RestaurantWithRatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_row,parent,false);
        RestaurantWithRatingViewHolder holder = new RestaurantWithRatingViewHolder(view,listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantWithRatingViewHolder holder, int position ) {
        Restaurant restaurant = restaurantList.get(position);
        holder.nameTv.setText(restaurant.getName());
        String friend = Model.instance.getAFriendNameWhoVisitedARestaurant(restaurant.getId());
        int numOfFriendsVisited = (Model.instance.getNumOfFriendVisitedRestaurant(restaurant.getId())-1);
        if(friend==null || numOfFriendsVisited<0){
            holder.descriptionTv.setText("No friend's reviews found");
        }else if (numOfFriendsVisited==0){
            holder.descriptionTv.setText(friend+" visited "+restaurant.getName());
        }else{
            holder.descriptionTv.setText(friend+" and "+numOfFriendsVisited+" visited "+restaurant.getName());
        }
        Model.instance.setStarByRating(restaurant.getRating(), holder.star1, holder.star2, holder.star3, holder.star4, holder.star5, holder.ratingTv);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

}
