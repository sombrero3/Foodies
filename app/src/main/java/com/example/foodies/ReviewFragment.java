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
import android.widget.TextView;

import com.example.foodies.R;
import com.example.foodies.model.Dish;
import com.example.foodies.model.Model;
import com.example.foodies.model.Review;
import com.example.foodies.model.User;


public class ReviewFragment extends Fragment {
    TextView dishNameTv,dishPriceTv, userNameTv, descriptionTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_review, container, false);

        String revId = ReviewFragmentArgs.fromBundle(getArguments()).getReviewId();
        Review review = Model.instance.getReviewById(revId);

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

        setHasOptionsMenu(true);
        return view;
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


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.review,menu);
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