package com.example.foodies.AdaptersAndViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.model.User;

public class UserViewHolder extends RecyclerView.ViewHolder{

    TextView nameTv;
    TextView restaurantTv;
    TextView reviewsTv;

    public UserViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.user_row_name_tv);
        restaurantTv = itemView.findViewById(R.id.user_row_resto_tv);
        reviewsTv = itemView.findViewById(R.id.user_row_reviews_tv);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                listener.onItemClick(v,pos);
            }
        });

    }
    public void bind(User user){
        nameTv.setText(user.getFirstName()+" "+user.getLastName());
        restaurantTv.setText("Visited "+ user.getTotalRestaurantsVisited() +" restaurants total");
        reviewsTv.setText("Has total of " + user.getTotalReviews()+ " reviews");
    }

}
