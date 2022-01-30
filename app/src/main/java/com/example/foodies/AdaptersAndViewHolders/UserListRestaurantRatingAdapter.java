package com.example.foodies.AdaptersAndViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.model.Model;
import com.example.foodies.model.User;

import java.util.List;

public class UserListRestaurantRatingAdapter extends RecyclerView.Adapter<UserWithRatingViewHolder>{

    OnItemClickListener listener;
    List<User> userList;
    String restaurantId;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public UserListRestaurantRatingAdapter(List<User> userList,String restaurantId){
        this.restaurantId=restaurantId;
        this.userList=userList;
    }
    @NonNull
    @Override
    public UserWithRatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_review_list_row,parent,false);
        UserWithRatingViewHolder holder = new UserWithRatingViewHolder(view,listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserWithRatingViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTv.setText(user.getFirstName()+" "+user.getLastName());
        String rating = Model.instance.getRestaurantRatingGivenByAUser(user,restaurantId);
        Model.instance.setStarByRating(rating, holder.star1, holder.star2, holder.star3, holder.star4, holder.star5, holder.ratingTv);
    }

    @Override
    public int getItemCount() {
            return userList.size();
        }

}
