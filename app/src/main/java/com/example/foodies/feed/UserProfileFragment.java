package com.example.foodies.feed;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.AdaptersAndViewHolders.FavoriteDishAdapter;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.AdaptersAndViewHolders.UserAdapter;
import com.example.foodies.R;
import com.example.foodies.model.Dish;
import com.example.foodies.model.DishReview;
import com.example.foodies.model.Model;
import com.example.foodies.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.LinkedList;
import java.util.List;

public class UserProfileFragment extends Fragment {
    TextView nameTv,totalReviewsTv,totalRestaurantsTv;
    Button allReviewsBtn;
    ImageView addFriendIv;
    RecyclerView reviewsRv,friendsRv;
    List<User> friendsList, signedUserFriendsList,signedUserFriendRequestList;
    List<DishReview> dishReviewList;
    User user;
    String userId;
    boolean flagRequest;
    ProgressBar prog;
    FavoriteDishAdapter favoriteDishAdapter;
    UserAdapter userAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        prog = view.findViewById(R.id.user_profile_prog);
        nameTv = view.findViewById(R.id.user_profile_name_tv);
        totalRestaurantsTv =view.findViewById(R.id.user_profile_total_restaurants_num_tv);
        totalReviewsTv = view.findViewById(R.id.user_profile_total_reviews_num_tv);
        addFriendIv = view.findViewById(R.id.user_profile_add_friend_iv);
        allReviewsBtn = view.findViewById(R.id.user_profile_all_reviews_btn);
        reviewsRv = view.findViewById(R.id.user_profile_favorit_dishes_rv);
        friendsRv = view.findViewById(R.id.user_profile_friends_rv);

        signedUserFriendsList = new LinkedList<>();
        setSignedUserFriendsList();
        signedUserFriendRequestList = new LinkedList<>();
        User signedUser = Model.instance.getSignedUser();
        String signedUserId = signedUser.getId();

        dishReviewList = new LinkedList<>();
        friendsList = new LinkedList<>();

        userId = UserProfileFragmentArgs.fromBundle(getArguments()).getUserId();
        setSignedUserFriendRequestList();
        setUserUI(userId);
        reviewsRv.setHasFixedSize(true);
        RecyclerView.LayoutManager horizontalLayout = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        reviewsRv.setLayoutManager(horizontalLayout);
        favoriteDishAdapter = new FavoriteDishAdapter(dishReviewList);
        reviewsRv.setAdapter(favoriteDishAdapter);

        friendsRv.setHasFixedSize(true);
        RecyclerView.LayoutManager horizontalLayout2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        friendsRv.setLayoutManager(horizontalLayout2);
        userAdapter = new UserAdapter(friendsList);
        friendsRv.setAdapter(userAdapter);

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

        setAddFriendBtnLogic();

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

    private void setSignedUserFriendRequestList() {
        Model.instance.getFriendsRequests(Model.instance.getSignedUser().getId(),new Model.GetFriendsRequestsListener() {
            @Override
            public void onComplete(List<User> fr) {
                signedUserFriendRequestList.clear();
                signedUserFriendRequestList.addAll(fr);
            }
        });
    }

    private void setAddFriendBtnLogic() {
        User signedUser = Model.instance.getSignedUser();
        String signedUserId = signedUser.getId();
        if(userId.equals(signedUserId)) {
            addFriendIv.setOnClickListener((v) -> {
                Navigation.findNavController(v).navigate(UserProfileFragmentDirections.actionUserProfileFragmentToAddFriendFragment());
            });
        }else if(!signedUserFriendsList.contains(user)){
            User userProfile = Model.instance.getUserByIdOld(userId);
            flagRequest =false;
            addFriendIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFriendIv.setEnabled(false);
                    if(signedUserFriendRequestList.contains(userProfile)){
                        if(!flagRequest){
                            try {
                                Model.instance.friendRequestConfirmed(userProfile.getId(), new Model.FriendRequestConfirmedListener() {
                                    @Override
                                    public void onComplete() {
                                        flagRequest = true;
                                        addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_disabled_orange_24);
                                        addFriendIv.setEnabled(true);
                                    }
                                });
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Model.instance.cancelFriendsihp(userProfile, new Model.CancelFriendshipListener() {
                                @Override
                                public void onComplete() {
                                    addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_orange24);
                                    addFriendIv.setEnabled(true);
                                    flagRequest = false;
                                }
                            });
                        }
                    }else {
                        if (!flagRequest) {
                            try {
                                Model.instance.friendRequestSendRequestToUser(userProfile, new Model.VoidListener() {
                                    @Override
                                    public void onComplete() {
                                        addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_disabled_orange_24);
                                        addFriendIv.setEnabled(true);
                                        flagRequest = true;
                                    }
                                });
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                Model.instance.friendRequestCancel(userProfile, new Model.VoidListener() {
                                    @Override
                                    public void onComplete() {
                                        addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_orange24);
                                        addFriendIv.setEnabled(true);
                                        flagRequest = false;
                                    }
                                });
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });
        }else if(signedUserFriendsList.contains(user)){
            User userProfile = Model.instance.getUserByIdOld(userId);
            addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_disabled_orange_24);
            flagRequest =false;
            addFriendIv.setOnClickListener((v)->{
                addFriendIv.setEnabled(false);
                if(!flagRequest) {
                    Model.instance.cancelFriendsihp(userProfile, new Model.CancelFriendshipListener() {
                        @Override
                        public void onComplete() {
                            addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_orange24);
                            addFriendIv.setEnabled(true);
                            flagRequest=true;
                        }
                    });

                }else{
                    Model.instance.recoverFriendship(userProfile, new Model.VoidListener() {
                        @Override
                        public void onComplete() {
                            addFriendIv.setImageResource(R.drawable.ic_baseline_person_add_disabled_orange_24);
                            addFriendIv.setEnabled(true);
                            flagRequest=false;
                        }
                    });
                }
            });
        }
    }

    private void setUserUI(String userId) {
        prog.setVisibility(View.VISIBLE);
        User signedUser = Model.instance.getSignedUser();
        String signedUserId = signedUser.getId();
        Model.instance.getUserById(userId, new Model.getUserByIdListener() {
            @Override
            public void onComplete(User user) {
                nameTv.setText(user.getFirstName());
                nameTv.setText(user.getFirstName()+ " "+ user.getLastName());
                totalReviewsTv.setText("Posted total of "+user.getTotalReviews()+" reviews");
                totalRestaurantsTv.setText("Posted reviews on "+user.getTotalRestaurantsVisited()+" restaurants");
                prog.setVisibility(View.INVISIBLE);
                setFriendList(userId);
                setFavoriteList(userId);

            }
        });
//              if (student.getAvatarUrl() != null) {
//                  Picasso.get().load(student.getAvatarUrl()).into(avatarIv);
//              }
//        });

        prog.setVisibility(View.GONE);
    }
    private void setSignedUserFriendsList() {
        Model.instance.getFriendsList(Model.instance.getSignedUser().getId(), new Model.GetFriendListListener() {
            @Override
            public void onComplete(List<User> friends) {
                signedUserFriendsList.clear();
                signedUserFriendsList.addAll(friends);
            }
        });
    }
    private void setFavoriteList(String userId) {
        dishReviewList.clear();
        dishReviewList.addAll(Model.instance.getUserHighestRatingReviewsByUserId(userId));
        favoriteDishAdapter.notifyDataSetChanged();

    }
    private void setFriendList(String userId) {
        Model.instance.getFriendsList(userId, new Model.GetFriendListListener() {
            @Override
            public void onComplete(List<User> friends) {
                friendsList.clear();
                friendsList.addAll(friends);
                userAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.all_other_fragments_menu,menu);

    }
    @Override
    public void onPrepareOptionsMenu (Menu menu) {
        if (userId.equals(Model.instance.getSignedUser().getId())) {
            menu.findItem(R.id.main_menu_profile).setEnabled(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


                return super.onOptionsItemSelected(item);

    }
}
