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

import com.example.foodies.model.Model;
import com.example.foodies.model.User;

import java.util.List;


public class UserListRvFragment extends Fragment {
    List<User> userList;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list_rv, container, false);
        userList = Model.instance.getUserList();

        RecyclerView list = view.findViewById(R.id.userlist_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String userName = userList.get(position).getLastName();
                Log.d("TAG","user's row clicked: " + userName);
                //Navigation.findNavController(v).navigate(StudentListRvFragmentDirections.actionStudentListRvFragmentToStudentDetailsFragment(stId));

            }
        });
        //ImageButton add = view.findViewById(R.id.studentlist_add_btn);

        //add.setOnClickListener(Navigation.createNavigateOnClickListener(StudentListRvFragmentDirections.actionGlobalAboutFragment()));
        //setHasOptionsMenu(true);
        return view;

    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        EditText nameEt;
        EditText restaurantEt;
        EditText reviewsEt;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            nameEt = itemView.findViewById(R.id.user_row_name_tv);
            restaurantEt = itemView.findViewById(R.id.user_row_resto_tv);
            reviewsEt = itemView.findViewById(R.id.user_row_reviews_tv);
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