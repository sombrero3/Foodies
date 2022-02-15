package com.example.foodies.feed;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.AdaptersAndViewHolders.UserListReviewRatingAdapter;
import com.example.foodies.R;
import com.example.foodies.model.Dish;
import com.example.foodies.model.Model;
import com.example.foodies.model.DishReview;
import com.example.foodies.model.User;

import java.util.List;


public class ReviewFragment extends Fragment {
    TextView dishNameTv,dishPriceTv, userNameTv, descriptionTv,ratingTv;
    ImageView image,star1,star2,star3,star4,star5,editIv,deleteIv;
    List<DishReview> dishReviewList;
    //Button editBtn,deleteBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_review, container, false);

        String revId = ReviewFragmentArgs.fromBundle(getArguments()).getReviewId();
        DishReview dishReview = Model.instance.getReviewById(revId);
        dishReviewList = Model.instance.getAllFriendsReviewsOnDishByDishIdAndUserId(dishReview.getDishId(),dishReview.getUserId());
        RecyclerView list = view.findViewById(R.id.review_rv);
        list.setHasFixedSize(true);
        RecyclerView.LayoutManager horizontalLayout = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        list.setLayoutManager(horizontalLayout);
        UserListReviewRatingAdapter adapter = new UserListReviewRatingAdapter(dishReviewList);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                DishReview rev = dishReviewList.get(position);
                Navigation.findNavController(v).navigate((NavDirections) ReviewFragmentDirections.actionReviewFragmentSelf(rev.getId()));
            }
        });

        dishNameTv = view.findViewById(R.id.review_dish_name_tv);
        dishPriceTv = view.findViewById(R.id.review_price_tv);
//        userNameTv = view.findViewById(R.id.review_user_name_tv);
        descriptionTv = view.findViewById(R.id.review_description_tv);
        ratingTv = view.findViewById(R.id.review_rating_tv);
        editIv = view.findViewById(R.id.review_edit_iv);
        deleteIv = view.findViewById(R.id.review_delete_iv);
        star1 = view.findViewById(R.id.review_star1_iv);
        star2 = view.findViewById(R.id.review_star2_iv);
        star3 = view.findViewById(R.id.review_star3_iv);
        star4 = view.findViewById(R.id.review_star4_iv);
        star5 = view.findViewById(R.id.review_star5_iv);

        Dish dish = Model.instance.getDishById(dishReview.getDishId());
        User user = Model.instance.getUserByIdOld(dishReview.getUserId());

        Model.instance.setStarByRating(dishReview.getRating(),star1,star2,star3,star4,star5,ratingTv);

        dishNameTv.setText(Model.instance.getUserByIdOld(dishReview.getUserId()).getFirstName()+"'s review about "+dish.getName());
        dishPriceTv.setText(dish.getPrice());
//        userNameTv.setText(user.getFirstName()+ " "+user.getLastName());
        descriptionTv.setText(dishReview.getDescription());

        if(dishReview.getUserId().equals(Model.instance.getSignedUser().getId())) {
            editIv.setOnClickListener((v) -> {
                Navigation.findNavController(v).navigate((NavDirections) ReviewFragmentDirections.actionReviewFragment2ToNewReviewFragment("edit " + dishReview.getId()));
            });

            deleteIv.setOnClickListener((v) -> {
                Model.instance.deleteReview(dishReview);
                Navigation.findNavController(v).navigateUp();
            });
        }else{
            editIv.setVisibility(View.INVISIBLE);
            editIv.setClickable(false);
            deleteIv.setVisibility(View.INVISIBLE);
            deleteIv.setClickable(false);

        }
        setHasOptionsMenu(true);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.all_other_fragments_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}