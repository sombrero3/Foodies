package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.model.Dish;
import com.example.foodies.model.Model;
import com.example.foodies.model.Restaurant;
import com.example.foodies.model.Review;
import com.example.foodies.model.User;


public class NewReviewFragment extends Fragment {
       EditText restaurantEt,dishEt,priceEt,descriptionEt;
       Button postReviewBtn, uploadImgBtn;
       TextView locationTv,titleTv,ratingTv;
       ImageView locationIv,image,star1,star2,star3,star4,star5;
       Review review;
       User user;
       boolean flagEditing,flagFromRestaurantPage,flagStar1,flagStar2,flagStar3,flagStar4,flagStar5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_review, container, false);
        user = Model.instance.getSignedUser();
        String args = NewReviewFragmentArgs.fromBundle(getArguments()).getEditSpaceReviewId();

        postReviewBtn = view.findViewById(R.id.new_review_postReview_btn);
        restaurantEt = view.findViewById(R.id.new_review_restaurant_et);
        dishEt = view.findViewById(R.id.new_review_dish_et);
        priceEt = view.findViewById(R.id.new_review_price_et);
        descriptionEt = view.findViewById(R.id.new_review_description_et);
        locationIv = view.findViewById(R.id.user_row_img);
        locationTv = view.findViewById(R.id.new_review_location_tv);
        titleTv = view.findViewById(R.id.new_review_title_tv);
        ratingTv = view.findViewById(R.id.new_review_rating_tv);
        star1= view.findViewById(R.id.new_review_star1_iv);
        star2= view.findViewById(R.id.new_review_star2_iv);
        star3= view.findViewById(R.id.new_review_star3_iv);
        star4= view.findViewById(R.id.new_review_star4_iv);
        star5= view.findViewById(R.id.new_review_star5_iv);
        flagEditing = false;
        ratingTv.setText("No rating yet");
        if(!args.equals("")){
            String []arr = args.split(" ");
            String reviewId = arr[1];

            if(arr[0].equals("edit")) {
                flagEditing = true;
                titleTv.setText("REVIEW EDITOR");
                review = Model.instance.getReviewById(reviewId);
                Dish dish = Model.instance.getDishById(review.getDishId());
                String restaurantName = Model.instance.getRestaurantById(review.getRestaurantId()).getName();
                restaurantEt.setText(restaurantName);
                dishEt.setText(dish.getName());
                priceEt.setText(dish.getPrice());
                descriptionEt.setText(review.getDescription());
                ratingTv.setText(review.getRating());
            }else if(arr[0].equals("restaurant")){
                flagFromRestaurantPage=true;
                String restaurantName = Model.instance.getRestaurantById(arr[1]).getName();
                restaurantEt.setText(restaurantName);
            }
        }

        setStarsOnClick();
        locationTv.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(NewReviewFragmentDirections.actionNewReviewFragmentToMapFragment());
        });
        locationIv.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(NewReviewFragmentDirections.actionNewReviewFragmentToMapFragment());
        });

        postReviewBtn.setOnClickListener((v)->{
            postReview();
            Navigation.findNavController(v).navigate(NewReviewFragmentDirections.actionNewReviewFragmentToReviewFragment2(review.getId()));
        });

       return view;
    }

    public void postReview(){
        if(!flagEditing) {
            review = new Review();
            review.setUserId(user.getId());
        }
        review.setRating(ratingTv.getText().toString());
        review.setDescription(descriptionEt.getText().toString());
        String resId = Model.instance.getRestaurantIdByName(restaurantEt.getText().toString());
        if(resId.equals("No Such Restaurant")){
            Restaurant restaurant = new Restaurant(restaurantEt.getText().toString());
            resId = restaurant.getId();
            Dish dish = new Dish(resId,dishEt.getText().toString(),priceEt.getText().toString());
            review.setRestaurantId(resId);
            review.setDishId(dish.getId());
            restaurant.addDish(dish);
            Model.instance.addDish(dish);
            Model.instance.addRestaurant(restaurant);
        }else{
            review.setRestaurantId(resId);
            String dishId = Model.instance.getDishIdByRestaurantIdAndDishName(resId,dishEt.getText().toString());
            if(dishId.equals("No Such Dish")){
                Dish dish = new Dish(resId,dishEt.getText().toString(),priceEt.getText().toString());
                review.setDishId(dish.getId());
                Model.instance.addDish(dish);
            }else{
                review.setDishId(dishId);
            }

        }
        Model.instance.addReview(review);
    }
    public void setStarsOnClick(){
        flagStar1=false;
        star1.setOnClickListener((v)->{
            if(!flagStar1) {
                ratingTv.setText("1");
                flagStar1=true;
            }else{
                ratingTv.setText("0.5");
                flagStar1=false;
            }
        });

        flagStar2=false;
        star2.setOnClickListener((v)->{
            if(!flagStar2) {
                ratingTv.setText("2");
                flagStar2=true;
            }else{
                ratingTv.setText("1.5");
                flagStar2=false;
            }
        });

        flagStar3=false;
        star3.setOnClickListener((v)->{
            if(!flagStar3) {
                ratingTv.setText("3");
                flagStar3=true;
            }else{
                ratingTv.setText("2.5");
                flagStar3=false;
            }
        });

        flagStar4=false;
        star4.setOnClickListener((v)->{
            if(!flagStar4) {
                ratingTv.setText("4");
                flagStar4=true;
            }else{
                ratingTv.setText("3.5");
                flagStar4=false;
            }
        });

        flagStar5=false;
        star5.setOnClickListener((v)->{
            if(!flagStar5) {
                ratingTv.setText("5");
                flagStar5=true;
            }else{
                ratingTv.setText("4.5");
                flagStar5=false;
            }
        });
    }
}