package com.example.foodies;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodies.AdaptersAndViewHolders.OnItemClickListener;
import com.example.foodies.AdaptersAndViewHolders.UserAdapter;
import com.example.foodies.model.Model;
import com.example.foodies.model.ModelFireBase;
import com.example.foodies.model.User;

import java.util.LinkedList;
import java.util.List;

public class AddFriendFragment extends Fragment {
    List<User> searchResultList;
    TextView rvTitleTv,wrongDetailsTv;
    EditText nameEt,emailEt;
    Button searchBtn;
    UserAdapter adapter;
    RecyclerView list;
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_friend, container, false);


        searchResultList = Model.instance.peopleYouMayKnow();
        list = view.findViewById(R.id.add_friend_rv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserAdapter(searchResultList);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener((v, position) -> {
            String userId = searchResultList.get(position).getId();
            Navigation.findNavController(v).navigate((NavDirections) AddFriendFragmentDirections.actionAddFriendFragmentToUserProfileFragment(userId));
        });
        nameEt = view.findViewById(R.id.add_friend_name_et);
        emailEt = view.findViewById(R.id.add_friend_email_et);
        rvTitleTv = view.findViewById(R.id.add_friend_rv_title_tv);
        searchBtn = view.findViewById(R.id.add_friend_serach_btn);
        wrongDetailsTv = view.findViewById(R.id.add_friend_wrong_details_tv);
        wrongDetailsTv.setVisibility(View.INVISIBLE);

        searchBtn.setOnClickListener(view1 -> search());
        setHasOptionsMenu(true);
        return view;
    }

    private void search() {
        String name = nameEt.getEditableText().toString();
        String email = emailEt.getEditableText().toString();
        boolean flag = true;
        if (name.equals("") || name.charAt(0) == ' ') {
            if (email.equals("") || email.charAt(0) == ' ') {
                wrongDetailsTv.setVisibility(View.VISIBLE);
                rvTitleTv.setText("No results");
                searchResultList.clear();
                flag=false;
            } else {
                searchResultList.clear();
                searchResultList.addAll(Model.instance.getNotFriendsUsersByEmail(email));
            }
        } else if (email.equals("") || email.charAt(0) == ' ') {
            searchResultList.clear();
            searchResultList.addAll(Model.instance.getNotFriendsUsersByName(name));
        } else {
            searchResultList.clear();
            searchResultList.addAll(Model.instance.getNotFriendsUsersByNameAndEmail(name,email));
        }
        adapter.notifyDataSetChanged();
        if(flag){
            wrongDetailsTv.setVisibility(View.INVISIBLE);
            if(searchResultList.size()>0) {
                rvTitleTv.setText("Search result :");
            }else{
                rvTitleTv.setText("No results");
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.all_other_fragments_menu,menu);

    }
    @Override
    public void onPrepareOptionsMenu (Menu menu) {
        menu.findItem(R.id.main_menu_add_friend).setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}