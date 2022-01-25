package com.example.foodies.AdaptersAndViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.model.User;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListViewHolder>{
    OnItemClickListener listener;
    List<User> userList;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public UserListAdapter(List<User> userList){
        this.userList=userList;
    }
    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_row,parent,false);
        UserListViewHolder holder = new UserListViewHolder(view,listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameEt.setText(user.getFirstName()+" "+user.getLastName());
        holder.restaurantEt.setText("Visited "+ user.getTotalRestaurantsVisited() +" restaurants total");
        holder.reviewsEt.setText("Has total of " + user.getReviewList().size()+ " reviews");
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

