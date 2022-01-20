package com.example.foodies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.R;
import com.example.foodies.model.Dish;
import com.example.foodies.model.Model;
import com.example.foodies.model.Restaurant;
import com.example.foodies.model.Review;
import com.example.foodies.model.User;

import java.util.List;


public class ReviewFragment extends Fragment {
    TextView dishNameTv,dishPriceTv, userNameTv, descriptionTv;
    List<Review> reviewList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_review, container, false);

        String revId = ReviewFragmentArgs.fromBundle(getArguments()).getReviewId();
        Review review = Model.instance.getReviewById(revId);
        reviewList = Model.instance.getAllFriendsReviewsOnDishByDishIdAndUserId(review.getDishId(),review.getUserId());
        RecyclerView list = view.findViewById(R.id.review_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        dishNameTv = view.findViewById(R.id.review_dish_name_tv);
        dishPriceTv = view.findViewById(R.id.review_price_tv);
        userNameTv = view.findViewById(R.id.review_user_name_tv);
        descriptionTv = view.findViewById(R.id.review_description_tv);
        Dish dish = Model.instance.getDishById(review.getDishId());
        User user = Model.instance.getUserById(review.getUserId());
        dishNameTv.setText(dish.getName());
        dishPriceTv.setText(dish.getPrice());
        userNameTv.setText(user.getFirstName()+ " "+user.getLastName());
        descriptionTv.setText(review.getDescription());


        return view;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv, numOfRestaurantsTv, numOfReviewsTv;
        ImageView image;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.user_review_list_row_name_tv);
//            numOfRestaurantsTv = itemView.findViewById(R.id.user_row_resto_tv);
//            numOfReviewsTv = itemView.findViewById(R.id.user_row_reviews_tv);
            image = itemView.findViewById(R.id.user_review_list_row_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(v,pos);
                }
            });

        }
    }
    interface OnItemClickListener{
        void onItemClick(View v,int position);
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        OnItemClickListener listener;
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.user_review_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Review rev = reviewList.get(position);
            holder.nameTv.setText(Model.instance.getUserById(rev.getUserId()).getFirstName()+ " " +position);
            //holder.descriptionTv.setText("Friend and 20 other friend visited this text should be dynamic");

        }

        @Override
        public int getItemCount() {
            return reviewList.size();
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        String revId = ReviewFragmentArgs.fromBundle(getArguments()).getReviewId();
//        Review review = Model.instance.getReviewById(revId);
//        if(review.getUserId().equals("1")){
//            Navigation.findNavController(this.getView()).navigate(ReviewFragmentDirections.actionReviewFragment2ToUserRestaurantListRvFragment(review.getUserId()));
//        }else{
//            Navigation.findNavController(this.getView()).navigate(ReviewFragmentDirections.actionReviewFragment2ToUserReviewsOnRestaurantRvFragment(review.getUserId(), review.getRestaurantId()));
//        }
//    }
}