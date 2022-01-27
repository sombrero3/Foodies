package com.example.foodies.AdaptersAndViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;

public class FriendRequestViewHolder extends RecyclerView.ViewHolder{
    TextView nameTv,emilTv;
    ImageView image;
    Button confirmBtn,ignoreBtn;

    public FriendRequestViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.friend_request_row_name_tv);
        emilTv = itemView.findViewById(R.id.friend_request_row_mail_tv);
        image = itemView.findViewById(R.id.friend_request_row_img);
        confirmBtn = itemView.findViewById(R.id.friend_request_row_confirm_btn);
        ignoreBtn = itemView.findViewById(R.id.friend_request_row_ignore_btn);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                listener.onItemClick(v,pos);
            }
        });

    }
}
