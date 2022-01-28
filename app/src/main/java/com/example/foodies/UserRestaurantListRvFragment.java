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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.AdaptersAndViewHolders.RestaurantListGeneralRatingAdapter;
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
    ImageButton searchIbtn;
    EditText searchEt;
    User user;
    boolean flag;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_restaurant_list_rv, container, false);

        String userId = UserRestaurantListRvFragmentArgs.fromBundle(getArguments()).getUserId();
//        if(userId==null){
//            user = Model.instance.getSignedUser();
//        }else{
            user = Model.instance.getUserById(userId);
       // }

        restaurantList = Model.instance.getAllRestaurantsThatUserHasReviewsOnByUserId(user.getId());


        RecyclerView list = view.findViewById(R.id.user_restaurant_rv);
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
        nameTv = view.findViewById(R.id.user_restaurant_name_tv);
        descriptionTv = view.findViewById(R.id.user_restaurant_description_tv);
        titleTv = view.findViewById(R.id.user_restaurant_title_tv);
        addReviewBtn = view.findViewById(R.id.user_restaurant_list_addreview_btn);
        searchIbtn = view.findViewById(R.id.user_restaurant_search_ibtn);
        searchEt = view.findViewById(R.id.user_restaurant_list_search_et);

        nameTv.setText(user.getFirstName() +" "+ user.getLastName());

        if(user.getId().equals(Model.instance.getSignedUser().getId())){
            addReviewBtn.setOnClickListener((v)->{
                Navigation.findNavController(v).navigate((NavDirections) UserRestaurantListRvFragmentDirections.actionUserRestaurantListRvFragmentToNewReviewFragment(""));
            });
        }else{
            addReviewBtn.setVisibility(View.INVISIBLE);
        }
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

        searchIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurantList = Model.instance.searchRestaurantByName(searchEt.getEditableText().toString());
                //adapter.notifyDataSetChanged();
                RestaurantListGeneralRatingAdapter newAdapter = new RestaurantListGeneralRatingAdapter(restaurantList);
                list.setAdapter(newAdapter);
                newAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        String restaurantId = restaurantList.get(position).getId();
                        Navigation.findNavController(v).navigate((NavDirections) HomeRestaurantListRvFragmentDirections.actionHomeRestaurantListRvFragmentToRestaurantPageRvFragment(restaurantId));
                    }
                });
            }
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
            menu.findItem(R.id.main_menu_my_reviews).setEnabled(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}