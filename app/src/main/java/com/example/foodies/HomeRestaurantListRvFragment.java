package com.example.foodies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.model.Model;
import com.example.foodies.model.Restaurant;
import com.example.foodies.model.User;

import java.util.List;


public class HomeRestaurantListRvFragment extends Fragment {
    List<Restaurant> restaurantList;
    EditText searchEt;
    ImageButton searchIbtn;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_restaurant_list_rv, container, false);
        restaurantList = Model.instance.getRestaurantList();

        RecyclerView list = view.findViewById(R.id.home_restaurant_list_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String restaurantName = restaurantList.get(position).getName();
                Log.d("TAG","user's row clicked: " + restaurantName);
                //Navigation.findNavController(v).navigate(StudentListRvFragmentDirections.actionStudentListRvFragmentToStudentDetailsFragment(stId));

            }
        });
        searchEt = view.findViewById(R.id.home_restaurant_list_search);
        searchIbtn = view.findViewById(R.id.home_restaurant_search_ibtn);
        searchIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //implement here search func
            }
        });

        //add.setOnClickListener(Navigation.createNavigateOnClickListener(StudentListRvFragmentDirections.actionGlobalAboutFragment()));
        //setHasOptionsMenu(true);
        return view;

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        TextView descriptionTv;
        ImageView image;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.restaurant_row_name);
            descriptionTv = itemView.findViewById(R.id.restaurant_row_description);
            image = itemView.findViewById(R.id.restaurant_row_img);
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

        }

        @Override
        public int getItemCount() {
            return restaurantList.size();
        }
    }
}