package com.example.foodies.AdaptersAndViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodies.R;
import com.example.foodies.model.FriendshipStatus;
import com.example.foodies.model.Model;
import com.example.foodies.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.LinkedList;
import java.util.List;

public class FriendRequestViewHolder extends RecyclerView.ViewHolder{
    TextView nameTv,emilTv;
    ImageView image;
    Button confirmBtn,ignoreBtn;
    boolean flagConfirm,flagIgnore;
    User signedUser;
    List<User> friendRequestsList;

    public FriendRequestViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.friend_request_row_name_tv);
        emilTv = itemView.findViewById(R.id.friend_request_row_mail_tv);
        image = itemView.findViewById(R.id.friend_request_row_img);
        confirmBtn = itemView.findViewById(R.id.friend_request_row_confirm_btn);
        ignoreBtn = itemView.findViewById(R.id.friend_request_row_ignore_btn);

        signedUser = Model.instance.getSignedUser();
        friendRequestsList = new LinkedList<>();
        Model.instance.getFriendsRequests(signedUser.getId(),new Model.GetFriendsRequestsListener() {
            @Override
            public void onComplete(List<User> users) {
                friendRequestsList.clear();
                friendRequestsList.addAll(users);
            }
        });

        flagConfirm = false;
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(confirmBtn.isEnabled()) {
                    if (!flagConfirm) {
                        flagConfirm = true;
                        confirmBtn.setEnabled(false);
                        confirmBtn.setText("cancel");
                        ignoreBtn.setEnabled(false);
                        try {
                            Model.instance.friendRequestConfirmed(friendRequestsList.get(getAdapterPosition()).getId(), new Model.FriendRequestConfirmedListener() {
                                @Override
                                public void onComplete() {
                                    confirmBtn.setEnabled(true);
                                }
                            });
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    } else {
                        flagConfirm = false;
                        try {
                            Model.instance.friendRequestUnConfirmed(friendRequestsList.get(getAdapterPosition()), new Model.FriendRequestUnConfirmed() {
                                @Override
                                public void onComplete() {
                                    confirmBtn.setText("confirm");
                                    ignoreBtn.setEnabled(true);
                                }
                            });
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        flagIgnore=false;
        ignoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ignoreBtn.isEnabled()) {
                    if (!flagIgnore) {
                        flagIgnore = true;
                        Model.instance.friendRequestIgnored(friendRequestsList.get(getAdapterPosition()));
                        ignoreBtn.setText("UnIgnore");
                        confirmBtn.setEnabled(false);
                    } else {
                        flagIgnore = false;
                        Model.instance.friendRequestCancelIgnore(friendRequestsList.get(getAdapterPosition()));
                        ignoreBtn.setText("Ignore");
                        confirmBtn.setEnabled(true);
                    }
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
