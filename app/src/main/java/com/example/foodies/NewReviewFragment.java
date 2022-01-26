package com.example.foodies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
       TextView locationTv;
       ImageView locationIv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_review, container, false);
        User user = Model.instance.getSignedUser();
        postReviewBtn = view.findViewById(R.id.new_review_postReview_btn);
        restaurantEt = view.findViewById(R.id.new_review_restaurant_et);
        dishEt = view.findViewById(R.id.new_review_dish_et);
        priceEt = view.findViewById(R.id.new_review_price_et);
        descriptionEt = view.findViewById(R.id.new_review_description_et);
        locationIv = view.findViewById(R.id.user_row_img);
        locationTv = view.findViewById(R.id.new_review_location_tv);

        locationTv.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(NewReviewFragmentDirections.actionNewReviewFragmentToMapFragment());
        });
        locationIv.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(NewReviewFragmentDirections.actionNewReviewFragmentToMapFragment());
        });



        postReviewBtn.setOnClickListener((v)->{
            Review review = new Review();
            review.setUserId(user.getId());
            review.setDescription(descriptionEt.getText().toString());
            String resId = Model.instance.getRestaurantIdByName(restaurantEt.getText().toString());
            if(resId.equals("No Such Restaurant")){
                Restaurant restaurant = new Restaurant(restaurantEt.getText().toString());
                resId = restaurant.getId();
                Dish dish = new Dish(resId,dishEt.getText().toString(),priceEt.getText().toString());
                review.setRestaurantId(resId);
                review.setDishId(dish.getId());
                dish.addReview(review);
                restaurant.addDish(dish);
                Model.instance.addDish(dish);
                Model.instance.addRestaurant(restaurant);
            }else{
                review.setRestaurantId(resId);
                String dishId = Model.instance.getDishIdByRestaurantIdAndDishName(resId,dishEt.getText().toString());
                if(dishId.equals("No Such Dish")){
                    Dish dish = new Dish(resId,dishEt.getText().toString(),priceEt.getText().toString());
                    review.setDishId(dish.getId());
                    dish.addReview(review);
                    Model.instance.addDish(dish);
                }else{
                    review.setDishId(dishId);
                }

            }
            Model.instance.addReview(review);
            Navigation.findNavController(v).navigate(NewReviewFragmentDirections.actionNewReviewFragmentToReviewFragment2(review.getId()));
        });
        setHasOptionsMenu(true);
       return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.new_review_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_menu){
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);

        }
    }



}