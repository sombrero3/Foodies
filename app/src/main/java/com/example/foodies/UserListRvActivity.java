package com.example.foodies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.foodies.model.Model;
import com.example.foodies.model.User;

import java.util.List;

public class UserListRvActivity extends AppCompatActivity {
    List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_rv);

        userList = Model.instance.getUserList();
        RecyclerView userListRv = findViewById(R.id.user_list_rv);
        userListRv.setHasFixedSize(true);
        userListRv.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter();
        userListRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG", "row was clicked "+ position);
            }
        });

    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        EditText nameEt;
        EditText restaurantEt;
        EditText reviewsEt;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            nameEt = itemView.findViewById(R.id.user_row_name_et);
            restaurantEt = itemView.findViewById(R.id.user_row_resto_et);
            reviewsEt = itemView.findViewById(R.id.user_row_reviews_et);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    listener.onItemClick(pos);
                }
            });

        }
    }
    interface OnItemClickListener{
        void onItemClick(int position);
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        OnItemClickListener listener;
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.user_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(view,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            User user = userList.get(position);
            holder.nameEt.setText(user.getFirstName()+" "+user.getLastName());
            holder.restaurantEt.setText("Visited "+ user.getTotalRestaurantsVisited() +" restaurants total");
            holder.reviewsEt.setText("Has total of " + user.getReviewList().size()+ " reviews");
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }
    }
}