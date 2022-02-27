package com.example.foodies.AdaptersAndViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.model.DishReview;
import com.example.foodies.model.Model;

import java.util.List;

public class UserListReviewRatingAdapter extends RecyclerView.Adapter<UserWithRatingViewHolder>{
    OnItemClickListener listener;
    List<DishReview> dishReviewList;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public UserListReviewRatingAdapter(List<DishReview> dishReviewList){
        this.dishReviewList = dishReviewList;
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
            DishReview rev = dishReviewList.get(position);
            holder.nameTv.setText(Model.instance.getUserByIdOld(rev.getUserId()).getFirstName()+ " " +position);
            Model.instance.setStarByRating(rev.getRating(), holder.star1, holder.star2, holder.star3, holder.star4, holder.star5, holder.ratingTv);
        }

        @Override
        public int getItemCount() {
            return dishReviewList.size();
        }


}
