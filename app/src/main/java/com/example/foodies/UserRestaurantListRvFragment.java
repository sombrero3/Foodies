package com.example.foodies;

import android.media.Image;
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

        //implement usersRestaurantList and replace it with restaurantList---------------------------------------------------------//
        User user = Model.instance.getUserById(UserRestaurantListRvFragmentArgs.fromBundle(getArguments()).getUserId());
        restaurantList = Model.instance.getAllRestaurantsThatUserHasReviewsOnByUserId(user.getId());
        //-------------------------------------------------------------------------------------------------------------------------//

        RecyclerView list = view.findViewById(R.id.user_restaurant_list_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter adapter = new MyAdapter();
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

                Navigation.findNavController(v).navigate(UserRestaurantListRvFragmentDirections.actionUserRestaurantListRvFragmentToNewReviewFragment(user.getId()));

        });
        }else{
            addReviewBtn.setVisibility(View.INVISIBLE);
        }
        //--------controlling the addReview button--------------//
     //   addReviewBtn.setVisibility(View.INVISIBLE);
      //  addReviewBtn.setClickable(false);
        //-----------------------------------------------------//
        //add.setOnClickListener(Navigation.createNavigateOnClickListener(StudentListRvFragmentDirections.actionGlobalAboutFragment()));
        //setHasOptionsMenu(true);
        return view;

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv,descriptionTv,ratingTv;
        ImageView image,star1,star2,star3,star4,star5;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.restaurant_row_name);
            descriptionTv = itemView.findViewById(R.id.restaurant_row_description);
            ratingTv = itemView.findViewById(R.id.restaurant_row_rating_tv);
            image = itemView.findViewById(R.id.restaurant_row_img);
            star1 = itemView.findViewById(R.id.restaurant_list_row_star1_iv);
            star2 = itemView.findViewById(R.id.restaurant_list_row_star2_iv);
            star3 = itemView.findViewById(R.id.restaurant_list_row_star3_iv);
            star4 = itemView.findViewById(R.id.restaurant_list_row_star4_iv);
            star5 = itemView.findViewById(R.id.restaurant_list_row_star5_iv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(v,pos);
                }
            });

        }
    }
    interface OnItemClickListener{
        void onItemClick(View v,int position);
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        OnItemClickListener listener;
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.restaurant_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Restaurant restaurant = restaurantList.get(position);
            holder.nameTv.setText(restaurant.getName());
            holder.descriptionTv.setText("Friend and 20 other friend visited this text should be dynamic");

            //restaurant.setRating("3.5");
            Model.instance.setStarByRating(restaurant.getRating(), holder.star1, holder.star2, holder.star3, holder.star4, holder.star5, holder.ratingTv);

        }

        @Override
        public int getItemCount() {
            return restaurantList.size();
        }
    }
}