package com.example.foodies.AdaptersAndViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.model.User;

import java.util.List;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestViewHolder> {
    List<User> userList;
    OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public FriendRequestAdapter(List<User> userList){
        this.userList=userList;
    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_row,parent,false);
        FriendRequestViewHolder holder = new FriendRequestViewHolder(view,listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTv.setText(user.getFirstName()+" "+user.getLastName());
        holder.emilTv.setText(user.getEmail());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
