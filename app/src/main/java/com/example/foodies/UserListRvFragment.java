package com.example.foodies;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.model.Model;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.model.User;
import com.example.foodies.AdaptersAndViewHolders.UserAdapter;

import java.util.List;


public class UserListRvFragment extends Fragment {
    List<User> userList;
    TextView nameTv,numOfFriendsTv,emailTv;
    ImageView imgIv,addFriendIv;
    User user;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list_rv, container, false);


        user = Model.instance.getUserById(UserListRvFragmentArgs.fromBundle(getArguments()).getUserId());
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
                Navigation.findNavController(v).navigate((NavDirections) UserListRvFragmentDirections.actionUserListRvFragmentToUserProfileFragment2(userList.get(position).getId()));
            }
        });

        nameTv = view.findViewById(R.id.user_list_name_tv);
        emailTv = view.findViewById(R.id.user_list_email_tv);
        numOfFriendsTv = view.findViewById(R.id.user_list_numOfFriends_tv);
        imgIv = view.findViewById(R.id.user_list_img_iv);
        addFriendIv = view.findViewById(R.id.user_list_add_friend_iv);

        nameTv.setText(user.getFirstName()+ " "+user.getLastName());
        emailTv.setText(user.getEmail());
        numOfFriendsTv.setText(Integer.toString(userList.size()) +" friends :");
        addFriendIv.setOnClickListener((v)->{
            Navigation.findNavController(v).navigate(UserListRvFragmentDirections.actionUserListRvFragmentToAddFriendFragment());
        });
        setHasOptionsMenu(true);
        return view;

    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.all_other_fragments_menu,menu);

    }

    @Override
    public void onPrepareOptionsMenu (Menu menu) {
        if (user.getId().equals(Model.instance.getSignedUser().getId())) {
            menu.findItem(R.id.main_menu_my_friends).setEnabled(false);
        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}