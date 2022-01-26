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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.model.Model;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.model.User;
import com.example.foodies.AdaptersAndViewHolders.UserListAdapter;

import java.util.List;


public class UserListRvFragment extends Fragment {
    List<User> userList;
    TextView nameTv,numOfFriendsTv;
    ImageView imgIv;
    Button addFriendBtn;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list_rv, container, false);


        User user = Model.instance.getUserById(UserListRvFragmentArgs.fromBundle(getArguments()).getUserId());
        userList = Model.instance.getUserById(user.getId()).getFriendsList();

        RecyclerView list = view.findViewById(R.id.userlist_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        UserListAdapter adapter = new UserListAdapter(userList);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                String userName = userList.get(position).getLastName();
                Log.d("TAG","user's row clicked: " + userName);
                Navigation.findNavController(v).navigate(UserListRvFragmentDirections.actionUserListRvFragmentToUserProfileFragment2(userList.get(position).getId()));
            }
        });

        nameTv = view.findViewById(R.id.user_list_name_tv);
        numOfFriendsTv = view.findViewById(R.id.user_list_numOfFriends_tv);
        imgIv = view.findViewById(R.id.user_list_img_iv);
        addFriendBtn = view.findViewById(R.id.user_list_addFriend_btn);

        nameTv.setText(user.getFirstName()+ " "+user.getLastName());

        addFriendBtn.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(UserListRvFragmentDirections.actionUserListRvFragmentToAddFriendFragment());
        });
        setHasOptionsMenu(true);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.user_list,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_menu){
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);

        }
    }

//    class MyViewHolder extends RecyclerView.ViewHolder{
//        TextView nameEt;
//        TextView restaurantEt;
//        TextView reviewsEt;
//
//        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
//            super(itemView);
//            nameEt = itemView.findViewById(R.id.user_row_name_tv);
//            restaurantEt = itemView.findViewById(R.id.user_row_resto_tv);
//            reviewsEt = itemView.findViewById(R.id.user_row_reviews_tv);
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
//
//    interface OnItemClickListener{
//        void onItemClick(View v,int position);
//    }
//
//    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
//        OnItemClickListener listener;
//        public void setOnItemClickListener(OnItemClickListener listener){
//            this.listener = listener;
//        }
//
//        @NonNull
//        @Override
//        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = getLayoutInflater().inflate(R.layout.user_list_row,parent,false);
//            MyViewHolder holder = new MyViewHolder(view,listener);
//            return holder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//            User user = userList.get(position);
//            holder.nameEt.setText(user.getFirstName()+" "+user.getLastName());
//            holder.restaurantEt.setText("Visited "+ user.getTotalRestaurantsVisited() +" restaurants total");
//            holder.reviewsEt.setText("Has total of " + user.getReviewList().size()+ " reviews");
//        }
//
//        @Override
//        public int getItemCount() {
//            return userList.size();
//        }
//    }
}