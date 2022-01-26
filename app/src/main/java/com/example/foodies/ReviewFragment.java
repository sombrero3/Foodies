package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.AdaptersAndViewHolders.UserListReviewRatingAdapter;
import com.example.foodies.model.Dish;
import com.example.foodies.model.Model;
import com.example.foodies.model.Review;
import com.example.foodies.model.User;

import java.util.List;


public class ReviewFragment extends Fragment {
    TextView dishNameTv,dishPriceTv, userNameTv, descriptionTv,ratingTv;
    ImageView image,star1,star2,star3,star4,star5;
    List<Review> reviewList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_review, container, false);

        String revId = ReviewFragmentArgs.fromBundle(getArguments()).getReviewId();
        Review review = Model.instance.getReviewById(revId);
        reviewList = Model.instance.getAllFriendsReviewsOnDishByDishId(review.getDishId());
        RecyclerView list = view.findViewById(R.id.review_rv);
        list.setHasFixedSize(true);
        RecyclerView.LayoutManager horizontalLayout = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        list.setLayoutManager(horizontalLayout);
        UserListReviewRatingAdapter adapter = new UserListReviewRatingAdapter(reviewList);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Review rev = reviewList.get(position);
                Navigation.findNavController(v).navigate(ReviewFragmentDirections.actionReviewFragmentSelf(rev.getId()));
            }
        });

        dishNameTv = view.findViewById(R.id.review_dish_name_tv);
        dishPriceTv = view.findViewById(R.id.review_price_tv);
        userNameTv = view.findViewById(R.id.review_user_name_tv);
        descriptionTv = view.findViewById(R.id.review_description_tv);
        ratingTv = view.findViewById(R.id.review_rating_tv);
        star1 = view.findViewById(R.id.review_star1_iv);
        star2 = view.findViewById(R.id.review_star2_iv);
        star3 = view.findViewById(R.id.review_star3_iv);
        star4 = view.findViewById(R.id.review_star4_iv);
        star5 = view.findViewById(R.id.review_star5_iv);

        Dish dish = Model.instance.getDishById(review.getDishId());
        User user = Model.instance.getUserById(review.getUserId());

        Model.instance.setStarByRating(review.getRating(),star1,star2,star3,star4,star5,ratingTv);

        dishNameTv.setText(dish.getName());
        dishPriceTv.setText(dish.getPrice());
        userNameTv.setText(user.getFirstName()+ " "+user.getLastName());
        descriptionTv.setText(review.getDescription());

        return view;
    }
}