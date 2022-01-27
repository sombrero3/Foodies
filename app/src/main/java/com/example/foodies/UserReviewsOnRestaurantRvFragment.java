package com.example.foodies;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.AdaptersAndViewHolders.DishListUserRatingAdapter;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.model.Dish;
import com.example.foodies.model.Model;
import com.example.foodies.model.Restaurant;
import com.example.foodies.model.Review;
import com.example.foodies.model.User;

import java.util.List;



public class UserReviewsOnRestaurantRvFragment extends Fragment {
        List<Dish> dishList;
        TextView nameTv,descriptionTv,generaReviewTitle,ratingTv,secondaryTitleTv;
        ImageView image,star1,star2,star3,star4,star5;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_user_reviews_on_restaurant_rv, container, false);

            User user = Model.instance.getUserById(UserReviewsOnRestaurantRvFragmentArgs.fromBundle(getArguments()).getUserId());
            Restaurant restaurant = Model.instance.getRestaurantById(UserReviewsOnRestaurantRvFragmentArgs.fromBundle(getArguments()).getRestaurantId());
            dishList = Model.instance.getAllDishesThatTheUserHasAReviewedOnInThisRestaurantByUserIdAndRestaurantId(user.getId(),restaurant.getId());

            RecyclerView list = view.findViewById(R.id.user_reviews_on_restaurant_dishes_list_rv);
            list.setHasFixedSize(true);
            list.setLayoutManager(new LinearLayoutManager(getContext()));
            DishListUserRatingAdapter adapter = new DishListUserRatingAdapter(dishList,user);
            list.setAdapter(adapter);

            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    String dishName = dishList.get(position).getName();
                    String price = dishList.get(position).getPrice();
                    String dishId = dishList.get(position).getId();
                    Review review = Model.instance.getReviewOnDishByDishIdAndUserId(dishId, user.getId());
                    Log.d("TAG","dish clicked: " + dishName + " price: "+price );
                    Navigation.findNavController(v).navigate((NavDirections) UserReviewsOnRestaurantRvFragmentDirections.actionUserReviewsOnRestaurantRvFragmentToReviewFragment(review.getId()));
                }
            });

            nameTv = view.findViewById(R.id.user_reviews_on_restaurant_name_tv);
            generaReviewTitle = view.findViewById(R.id.user_reviews_on_restaurant_general_review_tv);
            descriptionTv = view.findViewById(R.id.user_reviews_on_restaurant_general_review_description_tv);
            secondaryTitleTv = view.findViewById(R.id.user_reviews_on_restaurant_secondary_title_tv);
            star1 = view.findViewById(R.id.user_reviews_on_restaurant_star1_iv);
            star2 = view.findViewById(R.id.user_reviews_on_restaurant_star2_iv);
            star3 = view.findViewById(R.id.user_reviews_on_restaurant_star3_iv);
            star4 = view.findViewById(R.id.user_reviews_on_restaurant_star4_iv);
            star5 = view.findViewById(R.id.user_reviews_on_restaurant_star5_iv);
            ratingTv = view.findViewById(R.id.user_reviews_on_restaurant_rating_tv);

            String rating = Model.instance.getRestaurantRatingGivenByAUser(user,restaurant.getId());
            Model.instance.setStarByRating(rating,star1,star2,star3,star4,star5,ratingTv);

            nameTv.setText(user.getFirstName()+"'s reviews on "+restaurant.getName());
            secondaryTitleTv.setText("Dishes "+user.getFirstName()+" posted reviews on :");
            setHasOptionsMenu(true);
            return view;

        }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.all_other_framnets_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    }