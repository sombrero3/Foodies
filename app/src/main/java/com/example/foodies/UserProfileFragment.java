package com.example.foodies;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.example.foodies.AdaptersAndViewHolders.FavoriteDishAdapter;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.AdaptersAndViewHolders.UserAdapter;
import com.example.foodies.model.Dish;
import com.example.foodies.model.Model;
import com.example.foodies.model.DishReview;
import com.example.foodies.model.User;

import java.util.List;

public class UserProfileFragment extends Fragment {
    TextView nameTv,totalReviewsTv,totalRestaurantsTv;
    Button allReviewsBtn;
    ImageView addFriendIv;
    RecyclerView reviewsRv,friendsRv;
    List<User> friendsList, signedUserFriendsList,signedUserFriendRequestList;
    List<DishReview> dishReviewList;
    User user;
    boolean flagRequest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        String userId = UserProfileFragmentArgs.fromBundle(getArguments()).getUserId();
        user = Model.instance.getUserById(userId);
        User signedUser = Model.instance.getSignedUser();
        friendsList = Model.instance.getFriendsList(userId);
        friendsList.remove(Model.instance.getSignedUser());
        String signedUserId = signedUser.getId();
        if(!userId.equals(signedUserId)){
            signedUserFriendsList = Model.instance.getFriendsList(signedUserId);
        }
        signedUserFriendRequestList = Model.instance.getFriendsRequests(signedUserId);

        dishReviewList = Model.instance.getUserHighestRatingReviewsByUserId(user.getId());

        reviewsRv = view.findViewById(R.id.user_profile_favorit_dishes_rv);
        reviewsRv.setHasFixedSize(true);
        RecyclerView.LayoutManager horizontalLayout = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        reviewsRv.setLayoutManager(horizontalLayout);
        FavoriteDishAdapter favoriteDishAdapter = new FavoriteDishAdapter(dishReviewList);
        reviewsRv.setAdapter(favoriteDishAdapter);

        friendsRv = view.findViewById(R.id.user_profile_friends_rv);
        friendsRv.setHasFixedSize(true);
        RecyclerView.LayoutManager horizontalLayout2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        friendsRv.setLayoutManager(horizontalLayout2);
        UserAdapter userAdapter = new UserAdapter(friendsList);
        friendsRv.setAdapter(userAdapter);

        nameTv = view.findViewById(R.id.user_profile_name_tv);
        totalRestaurantsTv =view.findViewById(R.id.user_profile_total_restaurants_num_tv);
        totalReviewsTv = view.findViewById(R.id.user_profile_total_reviews_num_tv);
        addFriendIv = view.findViewById(R.id.user_profile_add_friend_iv);
        allReviewsBtn = view.findViewById(R.id.user_profile_all_reviews_btn);

        favoriteDishAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Dish dish = Model.instance.getDishById(dishReviewList.get(position).getDishId());
                String dishName = dish.getName();
                String price = dish.getPrice();
                Log.d("TAG","dish clicked: " + dishName + " price: "+price );
                Navigation.findNavController(v).navigate((NavDirections) UserProfileFragmentDirections.actionUserProfileFragmentToReviewFragment2(dishReviewList.get(position).getId()));
            }
        });

        userAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String userName = friendsList.get(position).getLastName();
                Log.d("TAG","user's row clicked: " + userName);
                Navigation.findNavController(v).navigate((NavDirections) UserProfileFragmentDirections.actionUserProfileFragmentSelf(friendsList.get(position).getId()));
            }
        });

        nameTv.setText(user.getFirstName()+ " "+ user.getLastName());
        totalReviewsTv.setText("Posted total of "+user.getTotalReviews()+" reviews");
        totalRestaurantsTv.setText("Posted reviews on "+user.getTotalRestaurantsVisited()+" restaurants");

        //signedUser.updateFriendLists();

        if(userId.equals(signedUserId)) {
            addFriendIv.setOnClickListener((v) -> {
                Navigation.findNavController(v).navigate(UserProfileFragmentDirections.actionUserProfileFragmentToAddFriendFragment());
            });
        }else if(!signedUserFriendsList.contains(user)){
            User userProfile = Model.instance.getUserById(userId);
            flagRequest =false;
            addFriendIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(signedUserFriendRequestList.contains(userProfile)){
                        if(!flagRequest){
                            Model.instance.friendRequestConfirmed(userProfile.getId());
                            addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_disabled_orange_24);
                            flagRequest = true;
                        }else{
                            Model.instance.cancelFriendsihp(userProfile);
                            addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_orange24);
                            flagRequest = false;
                        }
                    }else {
                        if (!flagRequest) {
                            Model.instance.friendRequestSendRequestToUser(userProfile);
                            addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_disabled_orange_24);
                            flagRequest = true;
                        } else {
                            Model.instance.friendRequestCancel(userProfile);
                            addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_orange24);
                            flagRequest = false;
                        }
                    }
                }
            });
        }else if(signedUserFriendsList.contains(user)){
            User userProfile = Model.instance.getUserById(userId);
            addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_disabled_orange_24);
            flagRequest =false;
            addFriendIv.setOnClickListener((v)->{
                if(!flagRequest) {
                    Model.instance.cancelFriendsihp(userProfile);
                    addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_orange24);
                    flagRequest=true;
                }else{
                    Model.instance.recoverFriendship(userProfile);
                    addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_disabled_orange_24);
                    flagRequest=false;
                }
            });
        }

        if(signedUserId.equals(userId)){
            allReviewsBtn.setText("My reviews");
        }else{
            allReviewsBtn.setText("All reviews");
        }


        allReviewsBtn.setOnClickListener((v)-> {
            Navigation.findNavController(v).navigate((NavDirections) UserProfileFragmentDirections.actionUserProfileFragmentToUserRestaurantListRvFragment(userId));
        });

        setHasOptionsMenu(true);
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.all_other_fragments_menu,menu);

    }
    @Override
    public void onPrepareOptionsMenu (Menu menu) {
        if (user.getId().equals(Model.instance.getSignedUser().getId())) {
            menu.findItem(R.id.main_menu_profile).setEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


                return super.onOptionsItemSelected(item);

    }
}
