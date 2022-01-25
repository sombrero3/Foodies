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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.model.Model;
import com.example.foodies.model.Restaurant;
import com.example.foodies.model.User;

import java.util.List;


public class RestaurantPageRvFragment extends Fragment {
    List<User> usersList;
    TextView nameTv, locationTv, numOfReviewsTv,ratingTv,secondaryTitleTv;
    ImageView image,star1,star2,star3,star4,star5;
    Restaurant restaurant;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_page_rv, container, false);

        String resId = RestaurantPageRvFragmentArgs.fromBundle(getArguments()).getRestaurantId();
        restaurant = Model.instance.getRestaurantById(resId);
        usersList = Model.instance.getAllUsersThatHaveReviewsOnRestaurantByRestaurantId(resId);

        RecyclerView list = view.findViewById(R.id.restaurant_page_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String userName = usersList.get(position).getFirstName() + " " +usersList.get(position).getLastName();
                Log.d("TAG","user clicked: " + userName);
                User user = usersList.get(position);
                Navigation.findNavController(v).navigate(RestaurantPageRvFragmentDirections.actionRestaurantPageRvFragmentToUserReviewsOnRestaurantRvFragment(user.getId(),resId));

            }
        });

        nameTv = view.findViewById(R.id.restaurant_page_name_tv);
        locationTv = view.findViewById(R.id.restaurant_page_location_tv);
        numOfReviewsTv = view.findViewById(R.id.restaurant_page_num_of_reviews_tv);
        ratingTv = view.findViewById(R.id.restaurant_page_rating_tv);
        secondaryTitleTv = view.findViewById(R.id.restaurant_page_secondary_title_tv);
        star1 = view.findViewById(R.id.restaurant_page_star1_iv);
        star2 = view.findViewById(R.id.restaurant_page_star2_iv);
        star3 = view.findViewById(R.id.restaurant_page_star3_iv);
        star4 = view.findViewById(R.id.restaurant_page_star4_iv);
        star5 = view.findViewById(R.id.restaurant_page_star5_iv);

        Model.instance.setStarByRating(restaurant.getRating(),star1,star2,star3,star4,star5,ratingTv);

        nameTv.setText(restaurant.getName());
        locationTv.setText(restaurant.getLocation());
        secondaryTitleTv.setText("Friends which posted review about "+restaurant.getName()+" :");

        //setHasOptionsMenu(true);
        return view;

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv,ratingTv;
        ImageView image,star1,star2,star3,star4,star5;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.user_review_list_row_name_tv);
            image = itemView.findViewById(R.id.user_review_list_row_img);
            star1 = itemView.findViewById(R.id.user_review_list_row_star1_iv);
            star2 = itemView.findViewById(R.id.user_review_list_row_star2_iv);
            star3 = itemView.findViewById(R.id.user_review_list_row_star3_iv);
            star4 = itemView.findViewById(R.id.user_review_list_row_star4_iv);
            star5 = itemView.findViewById(R.id.user_review_list_row_star5_iv);
            ratingTv = itemView.findViewById(R.id.user_review_list_row_rating_tv);
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
            View view = getLayoutInflater().inflate(R.layout.user_review_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            User user = usersList.get(position);
            holder.nameTv.setText(user.getFirstName()+" "+user.getLastName());

            String rating = Model.instance.getRestaurantRatingGivenByAUser(user,restaurant.getId());
            Model.instance.setStarByRating(rating, holder.star1, holder.star2, holder.star3, holder.star4, holder.star5, holder.ratingTv);

        }

        @Override
        public int getItemCount() {
            return usersList.size();
        }
    }
}