package com.example.foodies.model;

import android.media.Image;

import java.util.LinkedList;
import java.util.List;

public class User {
    String id;
    String firstName;
    String lastName;
    String totalReviews;
    String email;
    String password;
    Image image;
    List<Review> generalReviewList;
    List<DishReview> dishReviewList;
    List<User> friendsList,friendRequestList,ignoredList,confirmedList;
    String totalRestaurantsVisited;

    //-------Constructors-------//
    public User(){
        id = Integer.toString(IdGenerator.instance.getNextId());
        firstName = "";
        lastName = "";
        email ="No email address";
        totalReviews ="0";
        generalReviewList = new LinkedList<>();
        dishReviewList = new LinkedList<>();
        friendsList = new LinkedList<>();
        friendRequestList = new LinkedList<>();
        ignoredList = new LinkedList<>();
        confirmedList = new LinkedList<>();
        password = "";
        totalRestaurantsVisited = "0";
    }
    public User(String firstName,String password){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.firstName = firstName;
        this.lastName = password;
        email ="No email address";
        totalReviews ="0";
        this.password = password;
        generalReviewList = new LinkedList<>();
        dishReviewList = new LinkedList<>();
        friendsList = new LinkedList<>();
        friendRequestList = new LinkedList<>();
        ignoredList = new LinkedList<>();
        confirmedList = new LinkedList<>();
        totalRestaurantsVisited = "0";
    }
    public User(String firstName,String password,String email){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.firstName = firstName;
        this.lastName = password;
        this.email = email;
        totalReviews ="0";
        this.password = password;
        generalReviewList = new LinkedList<>();
        dishReviewList = new LinkedList<>();
        friendsList = new LinkedList<>();
        friendRequestList = new LinkedList<>();
        ignoredList = new LinkedList<>();
        confirmedList = new LinkedList<>();
        totalRestaurantsVisited = "0";
    }

    //-------Getters and Setters-------//
    public String getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getTotalReviews() {
        return totalReviews;
    }
    public void setTotalReviews(String totalReviews) {
        this.totalReviews = totalReviews;
    }
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public List<DishReview> getDishReviewList() {
        return dishReviewList;
    }
    public void setDishReviewList(List<DishReview> dishReviewList) {
        this.dishReviewList = dishReviewList;
    }
    public void setFriendsList(List<User> friendsList) {
        this.friendsList = friendsList;
    }
    public String getTotalRestaurantsVisited() {
        return totalRestaurantsVisited;
    }
    public void setTotalRestaurantsVisited(String totalRestaurantsVisited) {
        this.totalRestaurantsVisited = totalRestaurantsVisited;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<User> getFriendRequestList() {
        return friendRequestList;
    }
    public void setFriendRequestList(List<User> friendRequestList) {
        this.friendRequestList = friendRequestList;
    }
    public List<User> getIgnoredList() {
        return ignoredList;
    }
    public void setIgnoredList(List<User> ignoredList) {
        this.ignoredList = ignoredList;
    }
    public List<User> getFriendsList() {
        updateFriendLists();
        return friendsList;
    }
    public List<User> getConfirmedList() {
        return confirmedList;
    }
    public void setConfirmedList(List<User> confirmedList) {
        this.confirmedList = confirmedList;
    }

    public List<Review> getGeneralReviewList() {
        return generalReviewList;
    }

    public void setGeneralReviewList(List<Review> generalReviewList) {
        this.generalReviewList = generalReviewList;
    }
    //---------------------------------//

    public void addReview(DishReview dishReview){

        int newTotal = Integer.parseInt(totalReviews)+1;
        totalReviews = Integer.toString(newTotal);
        boolean flag = false;
        for(DishReview rev: dishReviewList){
            if(rev.getRestaurantId().equals(dishReview.getRestaurantId())){
                flag = true;
                break;
            }
        }
        if(!flag){
           newTotal = Integer.parseInt(totalRestaurantsVisited)+1;
           totalRestaurantsVisited = Integer.toString(newTotal);
        }
        dishReviewList.add(dishReview);
    }
    public void deleteReview(DishReview dishReview){
        dishReviewList.remove(dishReview);
        int newTotal = Integer.valueOf(totalReviews)-1;
        totalReviews = Integer.toString(newTotal);
        String restaurant = dishReview.getRestaurantId();
        boolean flag = false;
        for(int i = 0; i< dishReviewList.size(); i++){
            if(dishReviewList.get(i).getRestaurantId().equals(restaurant)){
                flag = true;
                break;
            }
        }
        if(!flag){
            newTotal = Integer.valueOf(totalRestaurantsVisited)-1;
            totalRestaurantsVisited = Integer.toString(newTotal);
        }
    }
    public void addFriend(User friend){
        if(!friendsList.contains(friend)&& !friend.getId().equals(id)) {
            friendsList.add(friend);
        }
    }
    public void deleteFriend(User friend){
        friendsList.remove(friend);
    }
    public void friendRequestToConfirm(User user){
        if(!friendRequestList.contains(user)) {
            friendRequestList.add(user);
        }
    }
    public void friendRequestConfirmed(User user){
        if(!confirmedList.contains(user)){confirmedList.add(user);}
       // addFriend(user);
    }
    public void cancelFriendship(User user){
        deleteFriend(user);
    }
    public void friendRequestDelete(User user){
        friendRequestList.remove(user);
    }
    public void friendRequestIgnored(User user){if(!ignoredList.contains(user)){ignoredList.add(user);} }
    public void friendRequestCancelIgnore(User user){ignoredList.remove(user); }
    public void friendRequestUnConfirmed(User user){confirmedList.remove(user); }
    public void updateFriendLists() {
        for (User user:confirmedList) {
            if(!friendsList.contains(user)) {
                friendsList.add(user);
            }
            friendRequestList.remove(user);
            confirmedList.remove(user);
        }
        for (User user:ignoredList) {
            friendRequestList.remove(user);
            ignoredList.remove(user);
        }
        for (User user:friendsList) {
            if(user.id.equals(id)){
                friendsList.remove(user);
            }
        }

    }
}
