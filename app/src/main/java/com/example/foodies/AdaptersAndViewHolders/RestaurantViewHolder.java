package com.example.foodies.AdaptersAndViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {

    TextView nameTv,descriptionTv,ratingTv;
    ImageView image,star1,star2,star3,star4,star5;

    public RestaurantViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.restaurant_row_name);
        descriptionTv = itemView.findViewById(R.id.restaurant_row_description);
        image = itemView.findViewById(R.id.restaurant_row_img);
        ratingTv = itemView.findViewById(R.id.restaurant_list_row_rating_tv);
        image = itemView.findViewById(R.id.restaurant_row_img);
        star1 = itemView.findViewById(R.id.restaurant_list_row_star1_img_view);
        star2 = itemView.findViewById(R.id.restaurant_list_row_star2_img_view);
        star3 = itemView.findViewById(R.id.restaurant_list_star3_img_view);
        star4 = itemView.findViewById(R.id.restaurant_list_row_star4_img_view);
        star5 = itemView.findViewById(R.id.restaurant_list_row_star5_img_view);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition();
                listener.onItemClick(v,pos);
            }
        });

    }
}
