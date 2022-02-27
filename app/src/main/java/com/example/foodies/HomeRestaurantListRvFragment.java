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

import com.example.foodies.model.Model;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.model.Restaurant;
import com.example.foodies.AdaptersAndViewHolders.RestaurantListGeneralRatingAdapter;
import com.example.foodies.model.User;

import java.util.List;


public class HomeRestaurantListRvFragment extends Fragment {
    List<Restaurant> restaurantList;
    EditText searchEt;
    TextView nameTv;
    ImageButton searchIbtn;
    ImageView locationIv;
    boolean flag;
    //Button addReviewBtn,menuBtn;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_restaurant_list_rv, container, false);



        User user = Model.instance.getSignedUser();
        restaurantList = Model.instance.getRestaurantList();

        RecyclerView list = view.findViewById(R.id.home_restaurant_list_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        RestaurantListGeneralRatingAdapter adapter = new RestaurantListGeneralRatingAdapter(restaurantList);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String restaurantName = restaurantList.get(position).getName();
                String restaurantId = restaurantList.get(position).getId();
                Log.d("TAG","Restaurant clicked: " + restaurantName + " " + restaurantId);
                Navigation.findNavController(v).navigate((NavDirections) HomeRestaurantListRvFragmentDirections.actionHomeRestaurantListRvFragmentToRestaurantPageRvFragment(restaurantId));

            }
        });

        searchEt = view.findViewById(R.id.home_restaurant_list_search_et);
        searchIbtn = view.findViewById(R.id.home_restaurant_search_ibtn);
        locationIv = view.findViewById(R.id.home_restaurant_location_iv);

        nameTv = view.findViewById(R.id.home_restaurant_list_name_tv);


        nameTv.setText("Hello "+ user.getFirstName() );
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

        locationIv.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(HomeRestaurantListRvFragmentDirections.actionHomeRestaurantListRvFragmentToMapsActivity());
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

//          addReviewBtn = view.findViewById(R.id.home_restaurant_list_review_btn);
//        menuBtn = view.findViewById(R.id.home_restaurant_list_menu_btn);
//        menuBtn.setOnClickListener((v)->{
//            Navigation.findNavController(v).navigate(HomeRestaurantListRvFragmentDirections.actionHomeRestaurantListRvFragmentToHomeFragment());
//        });
//
//        addReviewBtn.setOnClickListener((v)->{
//            Navigation.findNavController(v).navigate((NavDirections) HomeRestaurantListRvFragmentDirections.actionHomeRestaurantListRvFragmentToNewReviewFragment(""));
//        });

        setHasOptionsMenu(true);
        return view;

    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_restaurant_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
