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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.model.Dish;
import com.example.foodies.model.Model;
import com.example.foodies.model.Restaurant;

import java.util.List;


public class UserReviewsOnRestaurantRvFragment extends Fragment {
        List<Dish> dishList;
        TextView nameTv;
        TextView generaReviewTitle;
        TextView descriptionTv;
        ImageView image,star1,star2,star3,star4,star5;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_user_reviews_on_restaurant_rv, container, false);

            //implement usersRestaurantList and replace it with restaurantList---------------------------------------------------------//
            dishList = Model.instance.getDishList();
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
                    Log.d("TAG","restaurant clicked: " + dishName);
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
            //--------controlling the addReview button--------------//
            //   addReviewBtn.setVisibility(View.INVISIBLE);
            //  addReviewBtn.setClickable(false);
            //-----------------------------------------------------//
            //add.setOnClickListener(Navigation.createNavigateOnClickListener(StudentListRvFragmentDirections.actionGlobalAboutFragment()));
            //setHasOptionsMenu(true);
            return view;

        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView nameTv,priceTv;
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


            }

            @Override
            public int getItemCount() {
                return dishList.size();
            }
        }
    }