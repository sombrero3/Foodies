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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.model.Model;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.model.User;
import com.example.foodies.AdaptersAndViewHolders.UserAdapter;

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
        UserAdapter adapter = new UserAdapter(userList);
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

        return view;

    }

}