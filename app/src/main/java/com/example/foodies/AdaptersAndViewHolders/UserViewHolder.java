package com.example.foodies.AdaptersAndViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;

public class UserViewHolder extends RecyclerView.ViewHolder{
    TextView nameEt;
    TextView restaurantEt;
    TextView reviewsEt;

    public UserViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        nameEt = itemView.findViewById(R.id.user_row_name_tv);
        restaurantEt = itemView.findViewById(R.id.user_row_resto_tv);
        reviewsEt = itemView.findViewById(R.id.user_row_reviews_tv);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                listener.onItemClick(v,pos);
            }
        });

    }
}
