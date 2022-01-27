package com.example.foodies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.AdaptersAndViewHolders.UserListRestaurantRatingAdapter;
import com.example.foodies.AdaptersAndViewHolders.UserWithRatingViewHolder;
import com.example.foodies.model.Model;
import com.example.foodies.model.Restaurant;
import com.example.foodies.model.User;

import java.util.List;


public class RestaurantPageRvFragment extends Fragment {
    List<User> usersList;
    TextView nameTv, locationTv, numOfReviewsTv,ratingTv,secondaryTitleTv;
    ImageView image,star1,star2,star3,star4,star5;
    Restaurant restaurant;
    Button addReviewBtn;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_page_rv, container, false);

        String resId = RestaurantPageRvFragmentArgs.fromBundle(getArguments()).getRestaurantId();
        restaurant = Model.instance.getRestaurantById(resId);
        usersList = Model.instance.getAllUsersThatHaveReviewsOnRestaurantByRestaurantId(resId);

        RecyclerView list = view.findViewById(R.id.restaurant_page_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        UserListRestaurantRatingAdapter adapter = new UserListRestaurantRatingAdapter(usersList,resId);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String userName = usersList.get(position).getFirstName() + " " +usersList.get(position).getLastName();
                Log.d("TAG","user clicked: " + userName);
                User user = usersList.get(position);
                Navigation.findNavController(v).navigate((NavDirections) RestaurantPageRvFragmentDirections.actionRestaurantPageRvFragmentToUserReviewsOnRestaurantRvFragment(user.getId(),resId));

            }
        });

        nameTv = view.findViewById(R.id.restaurant_page_name_tv);
        locationTv = view.findViewById(R.id.restaurant_page_location_tv);
        numOfReviewsTv = view.findViewById(R.id.restaurant_page_num_of_reviews_tv);
        ratingTv = view.findViewById(R.id.restaurant_page_rating_tv);
        secondaryTitleTv = view.findViewById(R.id.restaurant_page_secondary_title_tv);
        addReviewBtn = view.findViewById(R.id.restaurant_page_addreview_btn);
        star1 = view.findViewById(R.id.restaurant_page_star1_iv);
        star2 = view.findViewById(R.id.restaurant_page_star2_iv);
        star3 = view.findViewById(R.id.restaurant_page_star3_iv);
        star4 = view.findViewById(R.id.restaurant_page_star4_iv);
        star5 = view.findViewById(R.id.restaurant_page_star5_iv);

        Model.instance.setStarByRating(restaurant.getRating(),star1,star2,star3,star4,star5,ratingTv);

        nameTv.setText(restaurant.getName());
        locationTv.setText(restaurant.getLocation());
        secondaryTitleTv.setText("Friends which posted review about "+restaurant.getName()+" :");

        addReviewBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate((NavDirections) RestaurantPageRvFragmentDirections.actionRestaurantPageRvFragmentToNewReviewFragment("restaurant "+restaurant.getId()));
        });
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