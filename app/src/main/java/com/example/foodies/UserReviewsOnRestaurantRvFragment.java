package com.example.foodies;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.model.Dish;
import com.example.foodies.model.Model;
import com.example.foodies.model.Restaurant;
import com.example.foodies.model.Review;
import com.example.foodies.model.User;

import java.util.List;



public class UserReviewsOnRestaurantRvFragment extends Fragment {
        List<Dish> dishList;
        TextView nameTv;
        TextView generaReviewTitle;
        TextView descriptionTv;
        ImageView image,star1,star2,star3,star4,star5;
        TextView ratingTv;
       float rate;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_user_reviews_on_restaurant_rv, container, false);

            //implement usersRestaurantList and replace it with restaurantList---------------------------------------------------------//
            User user = Model.instance.getUserById(UserReviewsOnRestaurantRvFragmentArgs.fromBundle(getArguments()).getUserId());
            Restaurant restaurant = Model.instance.getRestaurantById(UserReviewsOnRestaurantRvFragmentArgs.fromBundle(getArguments()).getRestaurantId());
            dishList = Model.instance.getAllDishesThatTheUserHasAReviewedOnInThisRestaurantByUserIdAndRestaurantId(user.getId(),restaurant.getId());
            //-------------------------------------------------------------------------------------------------------------------------//

            RecyclerView list = view.findViewById(R.id.user_reviews_on_restaurant_dishes_list_rv);
            list.setHasFixedSize(true);
            list.setLayoutManager(new LinearLayoutManager(getContext()));
            MyAdapter adapter = new MyAdapter();
            list.setAdapter(adapter);

            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    String dishName = dishList.get(position).getName();
                    String price = dishList.get(position).getPrice();
                    String dishId = dishList.get(position).getId();
                    Review review = Model.instance.getReviewOnDishByDishIdAndUserId(dishId, user.getId());
                    Log.d("TAG","dish clicked: " + dishName + " price: "+price );
                    Navigation.findNavController(v).navigate(UserReviewsOnRestaurantRvFragmentDirections.actionUserReviewsOnRestaurantRvFragmentToReviewFragment2(review.getId()));
                    //Navigation.findNavController(v).navigate(StudentListRvFragmentDirections.actionStudentListRvFragmentToStudentDetailsFragment(stId));

                }
            });
            nameTv = view.findViewById(R.id.user_reviews_on_restaurant_name_tv);
            generaReviewTitle = view.findViewById(R.id.user_reviews_on_restaurant_general_review_tv);
            descriptionTv = view.findViewById(R.id.user_reviews_on_restaurant_general_review_description_tv);
            star1 = view.findViewById(R.id.user_reviews_on_restaurant_star1_iv);
            star2 = view.findViewById(R.id.user_reviews_on_restaurant_star2_iv);
            star3 = view.findViewById(R.id.user_reviews_on_restaurant_star3_iv);
            star4 = view.findViewById(R.id.user_reviews_on_restaurant_star4_iv);
            star5 = view.findViewById(R.id.user_reviews_on_restaurant_star5_iv);
            ratingTv = view.findViewById(R.id.user_reviews_on_restaurant_rating_tv);

            restaurant.setRating("2.5");
            Model.instance.setStarByRating(restaurant.getRating(),star1,star2,star3,star4,star5,ratingTv);



           // star5.setVisibility(View.INVISIBLE);       erase image
         //   star5.setImageResource(R.drawable.boy1);   change image


            nameTv.setText(user.getFirstName()+"'s reviews on "+restaurant.getName());
            //--------controlling the addReview button--------------//
            //   addReviewBtn.setVisibility(View.INVISIBLE);
            //  addReviewBtn.setClickable(false);
            //-----------------------------------------------------//
            //add.setOnClickListener(Navigation.createNavigateOnClickListener(StudentListRvFragmentDirections.actionGlobalAboutFragment()));
            //setHasOptionsMenu(true);
            return view;

        }



        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView nameTv,priceTv,ratingTv;
            ImageView image,star1,star2,star3,star4,star5;


            public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
                super(itemView);
                nameTv = itemView.findViewById(R.id.dish_list_row_name_tv);
                priceTv = itemView.findViewById(R.id.dish_list_row_price_tv);
                image = itemView.findViewById(R.id.dish_list_row_img);
                star1 = itemView.findViewById(R.id.dish_list_row_star1_iv);
                star2 = itemView.findViewById(R.id.dish_list_row_star2_iv);
                star3 = itemView.findViewById(R.id.dish_list_row_star3_iv);
                star4 = itemView.findViewById(R.id.dish_list_row_star4_iv);
                star5 = itemView.findViewById(R.id.dish_list_row_star5_iv);
              ratingTv =itemView.findViewById(R.id.dish_list_row_rating_tv);
                //  String dishName=nameTv.toString();
              //  Model.instance.setStarByRating(,star1,star2,star3,star4,star5,ratingTv);
              //  Model.instance.setStarByRating(ratingTv.toString(), star1,star2, star3, star4, star5, ratingTv);


                //   star5.setVisibility(View.INVISIBLE);

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
                View view = getLayoutInflater().inflate(R.layout.dish_list_row,parent,false);
                MyViewHolder holder = new MyViewHolder(view,listener);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                Dish dish = dishList.get(position);
                holder.nameTv.setText(dish.getName());
                holder.priceTv.setText(dish.getPrice());
              //  Model.instance.setStarByRating(holder.ratingTv.toString(), holder.star1, holder.star2, holder.star3, holder.star4, holder.star5, holder.ratingTv);
            }

            @Override
            public int getItemCount() {
                return dishList.size();
            }
        }
    }