package com.example.foodies.AdaptersAndViewHolders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.FriendRequestsFragment;
import com.example.foodies.FriendRequestsFragmentDirections;
import com.example.foodies.R;
import com.example.foodies.model.Model;
import com.example.foodies.model.User;

import java.util.List;

public class FriendRequestViewHolder extends RecyclerView.ViewHolder{
    TextView nameTv,emilTv;
    ImageView image;
    Button confirmBtn,ignoreBtn;
    boolean flagConfirm,flagIgnore;
    User user;

    public FriendRequestViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.friend_request_row_name_tv);
        emilTv = itemView.findViewById(R.id.friend_request_row_mail_tv);
        image = itemView.findViewById(R.id.friend_request_row_img);
        confirmBtn = itemView.findViewById(R.id.friend_request_row_confirm_btn);
        ignoreBtn = itemView.findViewById(R.id.friend_request_row_ignore_btn);

        flagConfirm=false;
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flagConfirm){
                    flagConfirm = true;
                    Model.instance.friendRequestConfirmed(Model.instance.getSignedUser().getFriendRequestList().get(getAdapterPosition()).getId());
                    confirmBtn.setText("cancel");

                }else{
                    flagConfirm=false;
                    Model.instance.cancelFriendsihp(Model.instance.getSignedUser().getFriendRequestList().get(getAdapterPosition()));
                    confirmBtn.setText("confirm");
                }
            }
        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                listener.onItemClick(v,pos);
            }
        });

    }
}
