package com.example.foodies.AdaptersAndViewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.R;

import com.example.foodies.model.Dish;
import com.example.foodies.model.Model;
import com.example.foodies.model.User;

import java.util.List;

public class DishListAdapter extends RecyclerView.Adapter<DishListViewHolder> {
    OnItemClickListener listener;
    List<Dish> dishList;
    User user;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public DishListAdapter(List<Dish> dishList,User user){
        this.user=user;
        this.dishList = dishList;
    }

    @NonNull
    @Override
    public DishListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_list_row,parent,false);
        DishListViewHolder holder = new DishListViewHolder(view,listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DishListViewHolder holder, int position) {
        Dish dish = dishList.get(position);
        holder.nameTv.setText(dish.getName());
        holder.priceTv.setText(dish.getPrice());
        String rating = Model.instance.getReviewRatingByDishIdAndUserId(dish.getId(),user.getId());
        Model.instance.setStarByRating(rating, holder.star1, holder.star2, holder.star3, holder.star4, holder.star5, holder.ratingTv);
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

}
