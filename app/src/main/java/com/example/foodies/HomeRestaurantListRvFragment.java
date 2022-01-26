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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.model.Model;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.model.Restaurant;
import com.example.foodies.AdaptersAndViewHolders.RestaurantAdapter;
import com.example.foodies.model.User;

import java.util.List;


public class HomeRestaurantListRvFragment extends Fragment {
    List<Restaurant> restaurantList;
    EditText searchEt;
    TextView nameTv;
    ImageButton searchIbtn;
    Button addReviewBtn,menuBtn;
    ImageView locationIv;
    boolean flag;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_restaurant_list_rv, container, false);

        //----to use user validation put the next two lines in comment----//
        Model.instance.setSignedUser(Model.instance.getUserById("1"));
        Model.instance.setSignedFlag(true);
        //----------------------------------------------------------------//

        User user = Model.instance.getSignedUser();
        restaurantList = Model.instance.getRestaurantList();

        RecyclerView list = view.findViewById(R.id.home_restaurant_list_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        RestaurantAdapter adapter = new RestaurantAdapter(restaurantList);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String restaurantName = restaurantList.get(position).getName();
                String restaurantId = restaurantList.get(position).getId();
                Log.d("TAG","Restaurant clicked: " + restaurantName + " " + restaurantId);
                Navigation.findNavController(v).navigate(HomeRestaurantListRvFragmentDirections.actionHomeRestaurantListRvFragmentToRestaurantPageRvFragment(restaurantId));

            }
        });

        searchEt = view.findViewById(R.id.home_restaurant_list_search_et);
        searchIbtn = view.findViewById(R.id.home_restaurant_search_ibtn);
        locationIv = view.findViewById(R.id.home_restaurant_location_iv);
        addReviewBtn = view.findViewById(R.id.home_restaurant_list_review_btn);
        nameTv = view.findViewById(R.id.home_restaurant_list_name_tv);
        menuBtn = view.findViewById(R.id.home_restaurant_list_menu_btn);

        nameTv.setText("Hello "+ user.getFirstName() );
        flag = true;
        searchEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag) {
                    searchEt.setText("");
                    flag = false;
                }
            }
        });

        locationIv.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(HomeRestaurantListRvFragmentDirections.actionHomeRestaurantListRvFragmentToMapFragment());
        });

        searchIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurantList = Model.instance.serachRestaurantByName(searchEt.getEditableText().toString());
                //adapter.notifyDataSetChanged();
                RestaurantAdapter newAdapter = new RestaurantAdapter(restaurantList);
                list.setAdapter(newAdapter);
                newAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        String restaurantId = restaurantList.get(position).getId();
                        Navigation.findNavController(v).navigate(HomeRestaurantListRvFragmentDirections.actionHomeRestaurantListRvFragmentToRestaurantPageRvFragment(restaurantId));
                    }
                });
            }
        });

        menuBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(HomeRestaurantListRvFragmentDirections.actionHomeRestaurantListRvFragmentToHomeFragment());
        });

        addReviewBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(HomeRestaurantListRvFragmentDirections.actionHomeRestaurantListRvFragmentToNewReviewFragment());
        });
        //setHasOptionsMenu(true);
        return view;

    }

//    class MyViewHolder extends RecyclerView.ViewHolder{
//        TextView nameTv,descriptionTv,ratingTv;
//        ImageView image,star1,star2,star3,star4,star5;
//
//        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
//            super(itemView);
//            nameTv = itemView.findViewById(R.id.restaurant_row_name);
//            descriptionTv = itemView.findViewById(R.id.restaurant_row_description);
//            image = itemView.findViewById(R.id.restaurant_row_img);
//            ratingTv = itemView.findViewById(R.id.restaurant_row_rating_tv);
//            image = itemView.findViewById(R.id.restaurant_row_img);
//            star1 = itemView.findViewById(R.id.restaurant_list_row_star1_iv);
//            star2 = itemView.findViewById(R.id.restaurant_list_row_star2_iv);
//            star3 = itemView.findViewById(R.id.restaurant_list_row_star3_iv);
//            star4 = itemView.findViewById(R.id.restaurant_list_row_star4_iv);
//            star5 = itemView.findViewById(R.id.restaurant_list_row_star5_iv);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition();
//                    listener.onItemClick(v,pos);
//                }
//            });
//
//        }
//    }
//    interface OnItemClickListener{
//        void onItemClick(View v,int position);
//    }
//    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
//        OnItemClickListener listener;
//        public void setOnItemClickListener(OnItemClickListener listener){
//            this.listener = listener;
//        }
//
//        @NonNull
//        @Override
//        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = getLayoutInflater().inflate(R.layout.restaurant_list_row,parent,false);
//            MyViewHolder holder = new MyViewHolder(view,listener);
//            return holder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//            Restaurant restaurant = restaurantList.get(position);
//            holder.nameTv.setText(restaurant.getName());
//            holder.descriptionTv.setText("Friend and 20 other friend visited this text should be dynamic");
//            Model.instance.setStarByRating(restaurant.getRating(), holder.star1, holder.star2, holder.star3, holder.star4, holder.star5, holder.ratingTv);
//        }
//
//        @Override
//        public int getItemCount() {
//            return restaurantList.size();
//        }
//    }
}