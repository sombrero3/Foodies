package com.example.foodies;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodies.AdaptersAndViewHolders.FriendRequestAdapter;
import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.AdaptersAndViewHolders.UserAdapter;
import com.example.foodies.model.Model;
import com.example.foodies.model.User;

import java.util.List;


public class FriendRequestsFragment extends Fragment {
    List<User> userList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_requests, container, false);

        //User user = Model.instance.getUserById(UserListRvFragmentArgs.fromBundle(getArguments()).getUserId());

        userList = Model.instance.getSignedUser().getFriendsList();

        RecyclerView list = view.findViewById(R.id.friend_request_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        FriendRequestAdapter adapter = new FriendRequestAdapter(userList);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
//                String userId = searchResultList.get(position).getId();
//                Navigation.findNavController(v).navigate((NavDirections) AddFriendFragmentDirections.actionAddFriendFragmentToUserProfileFragment(userId));
            }
        });
    setHasOptionsMenu(true);
        return view;
    }
}