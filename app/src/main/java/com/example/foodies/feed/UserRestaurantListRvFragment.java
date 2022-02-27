package com.example.foodies.feed;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foodies.AdaptersAndViewHolders.RestaurantListGeneralRatingAdapter;
import com.example.foodies.AdaptersAndViewHolders.RestaurantListUserRatingAdapter;
import com.example.foodies.R;
import com.example.foodies.model.Model;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.model.Restaurant;
import com.example.foodies.model.User;

import java.util.List;


public class UserRestaurantListRvFragment extends Fragment {
    List<Restaurant> restaurantList;
    TextView nameTv,titleTv,totalReviewsTv;
    Button addReviewBtn;
    ImageView image;
    ImageButton searchIbtn;
    EditText searchEt;
    User user;
    ProgressBar prog;
    boolean flag;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_restaurant_list_rv, container, false);

        RecyclerView list = view.findViewById(R.id.user_restaurant_rv);
        nameTv = view.findViewById(R.id.user_restaurant_name_tv);
        totalReviewsTv = view.findViewById(R.id.user_restaurant_num_reviews_tv);
        titleTv = view.findViewById(R.id.user_restaurant_title_tv);
        addReviewBtn = view.findViewById(R.id.user_restaurant_list_addreview_btn);
        searchIbtn = view.findViewById(R.id.user_restaurant_search_ibtn);
        searchEt = view.findViewById(R.id.user_restaurant_list_search_et);
        prog=view.findViewById(R.id.user_restaurant_prog);

        prog.setVisibility(View.VISIBLE);
        String userId = UserRestaurantListRvFragmentArgs.fromBundle(getArguments()).getUserId();
        user=Model.instance.getUserByIdOld(userId);
        setUserUi(userId);

        restaurantList = Model.instance.getAllRestaurantsThatUserHasReviewsOnByUserId(user.getId());
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        RestaurantListUserRatingAdapter adapter = new RestaurantListUserRatingAdapter(restaurantList,user);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String restaurantName = restaurantList.get(position).getName();
                Log.d("TAG","restaurant clicked: " + restaurantName);
                Navigation.findNavController(v).navigate((NavDirections) UserRestaurantListRvFragmentDirections.actionUserRestaurantListRvFragmentToUserReviewsOnRestaurantRvFragment(user.getId(),restaurantList.get(position).getId()));

            }
        });
        if(user.getId().equals(Model.instance.getSignedUser().getId())){
            addReviewBtn.setOnClickListener((v)->{
                Navigation.findNavController(v).navigate((NavDirections) UserRestaurantListRvFragmentDirections.actionUserRestaurantListRvFragmentToNewReviewFragment(""));
            });
        }else{
            addReviewBtn.setVisibility(View.INVISIBLE);
        }

        searchIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurantList.clear();
                restaurantList.addAll(Model.instance.searchRestaurantByName(searchEt.getEditableText().toString()));
                adapter.notifyDataSetChanged();
            }
        });

        flag = true;
        searchEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag) {
                    searchEt.setText("");
                    flag = false;
                }else{
                    flag = true;
                }
            }
        });

        prog.setVisibility(View.GONE);
        setHasOptionsMenu(true);
        return view;
    }

    private void setUserUi(String userId) {
        String signedUserId = Model.instance.getSignedUser().getId();
        if(signedUserId.equals(userId)){
            User user =Model.instance.getSignedUser();
            nameTv.setText(user.getFirstName() +" "+ user.getLastName());
            totalReviewsTv.setText("Posted "+user.getTotalReviews()+" reviews");
            titleTv.setText(user.getFirstName()+"'s reviews :");
        }else {
            Model.instance.getUserById(userId, new Model.getUserByIdListener() {
                @Override
                public void onComplete(User user) {
                    nameTv.setText(user.getFirstName() + " " + user.getLastName());
                    totalReviewsTv.setText("Posted " + user.getTotalReviews() + " reviews");
                    titleTv.setText(user.getFirstName() + "'s reviews :");
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.all_other_fragments_menu,menu);

    }
    @Override
    public void onPrepareOptionsMenu (Menu menu) {
        if (user.getId().equals(Model.instance.getSignedUser().getId())) {
            menu.findItem(R.id.main_menu_my_reviews).setEnabled(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}