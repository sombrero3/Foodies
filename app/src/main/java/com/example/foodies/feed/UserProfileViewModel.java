package com.example.foodies.feed;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodies.model.DishReview;
import com.example.foodies.model.Model;
import com.example.foodies.model.User;

import java.util.List;

public class UserProfileViewModel extends ViewModel {
    LiveData<User> user;
    LiveData<List<User>> userList;
    LiveData<List<DishReview>> dishReviewList;
    User signedUser ;

    public UserProfileViewModel() {
        signedUser = Model.instance.getSignedUser();

    }

    public LiveData<User> getUser() {
        return user;
    }


    public interface setUserListener{
        void onComplete();
    }
    public void setUser(String userId,setUserListener listener) {
        Model.instance.getUserById(userId, new Model.getUserByIdListener() {
            @Override
            public void onComplete(User u) {
                user= new LiveData<User>() {
                    @Override
                    protected void postValue(User value) {
                        super.setValue(u);
                        listener.onComplete();
                    }
                };

                //userList = Model.instance.getUserFriendList(String userId,new listener)
                //dishReviewList = Model.instance.getFavoriteDishes(String userId,new listener)
            }
        });
        this.user = user;
    }

    public void setUserList(LiveData<List<User>> userList) {
        this.userList = userList;
    }

    public LiveData<List<DishReview>> getDishReviewList() {
        return dishReviewList;
    }

    public void setDishReviewList(LiveData<List<DishReview>> dishReviewList) {
        this.dishReviewList = dishReviewList;
    }

    public User getSignedUser() {
        return signedUser;
    }

    public void setSignedUser(User signedUser) {
        this.signedUser = signedUser;
    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }
}
