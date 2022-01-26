package com.example.foodies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.AdaptersAndViewHolders.RestaurantListUserRatingAdapter;
import com.example.foodies.model.Model;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.model.Restaurant;
import com.example.foodies.model.User;

import java.util.List;


public class UserRestaurantListRvFragment extends Fragment {
    List<Restaurant> restaurantList;
    TextView nameTv,titleTv,descriptionTv;
    Button addReviewBtn;
    ImageView image;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_restaurant_list_rv, container, false);


        User user = Model.instance.getUserById(UserRestaurantListRvFragmentArgs.fromBundle(getArguments()).getUserId());
        restaurantList = Model.instance.getAllRestaurantsThatUserHasReviewsOnByUserId(user.getId());


        RecyclerView list = view.findViewById(R.id.user_restaurant_list_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        RestaurantListUserRatingAdapter adapter = new RestaurantListUserRatingAdapter(restaurantList,user);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String restaurantName = restaurantList.get(position).getName();
                Log.d("TAG","restaurant clicked: " + restaurantName);
                Navigation.findNavController(v).navigate(UserRestaurantListRvFragmentDirections.actionUserRestaurantListRvFragmentToUserReviewsOnRestaurantRvFragment(user.getId(),restaurantList.get(position).getId()));

            }
        });
        nameTv = view.findViewById(R.id.user_restaurant_name_tv);
        descriptionTv = view.findViewById(R.id.user_restaurant_description_tv);
        titleTv = view.findViewById(R.id.user_restaurant_title_tv);
        addReviewBtn = view.findViewById(R.id.user_restaurant_list_addreview_btn);
        nameTv.setText(user.getFirstName() +" "+ user.getLastName());

        if(user.getId().equals(Model.instance.getSignedUser().getId())){
            addReviewBtn.setOnClickListener((v)->{
                Navigation.findNavController(v).navigate(UserRestaurantListRvFragmentDirections.actionUserRestaurantListRvFragmentToNewReviewFragment());
            });
        }else{
            addReviewBtn.setVisibility(View.INVISIBLE);
        }
        //setHasOptionsMenu(true);
        return view;

    }
}