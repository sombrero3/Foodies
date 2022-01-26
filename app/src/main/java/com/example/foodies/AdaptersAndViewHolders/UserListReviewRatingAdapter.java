package com.example.foodies.AdaptersAndViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.model.Model;
import com.example.foodies.model.Review;

import java.util.List;

public class UserListReviewRatingAdapter extends RecyclerView.Adapter<UserWithRatingViewHolder>{
    OnItemClickListener listener;
    List<Review> reviewList;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public UserListReviewRatingAdapter(List<Review> reviewList){
        this.reviewList=reviewList;
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
            Review rev = reviewList.get(position);
            holder.nameTv.setText(Model.instance.getUserById(rev.getUserId()).getFirstName()+ " " +position);
            Model.instance.setStarByRating(rev.getRating(), holder.star1, holder.star2, holder.star3, holder.star4, holder.star5, holder.ratingTv);
        }

        @Override
        public int getItemCount() {
            return reviewList.size();
        }


}
