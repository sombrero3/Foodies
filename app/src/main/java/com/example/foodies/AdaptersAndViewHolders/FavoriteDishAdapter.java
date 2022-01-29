package com.example.foodies.AdaptersAndViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;
import com.example.foodies.model.Dish;
import com.example.foodies.model.DishReview;
import com.example.foodies.model.Model;

import java.util.List;

public class FavoriteDishAdapter extends RecyclerView.Adapter<DishListUserRatingViewHolder> {
    OnItemClickListener listener;
    List<DishReview> dishReviewList;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public FavoriteDishAdapter(List<DishReview> dishReviewList){
        this.dishReviewList = dishReviewList;
    }

    @NonNull
    @Override
    public DishListUserRatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_list_row,parent,false);
        DishListUserRatingViewHolder holder = new DishListUserRatingViewHolder(view,listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DishListUserRatingViewHolder holder, int position) {
        DishReview dishReview = dishReviewList.get(position);
        Dish dish =Model.instance.getDishById(dishReview.getDishId());
        holder.nameTv.setText(dish.getName());
        holder.priceTv.setText(dish.getPrice());
        Model.instance.setStarByRating(dishReview.getRating(), holder.star1, holder.star2, holder.star3, holder.star4, holder.star5, holder.ratingTv);
    }

    @Override
    public int getItemCount() {
        return dishReviewList.size();
    }

}
