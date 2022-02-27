package com.example.foodies.AdaptersAndViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder>{
    OnItemClickListener listener;
    List<User> userList;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public UserAdapter(List<User> userList){
        this.userList=userList;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_row,parent,false);
        UserViewHolder holder = new UserViewHolder(view,listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

