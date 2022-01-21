package com.example.foodies.model;

import android.media.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User {
    String id;
    String firstName;
    String lastName;
    String totalReviews;
    String password;
    Image image;
    List<Review> reviewList;
    List<User> friendsList;
    String totalRestaurantsVisited;

    //-------Constructors-------//
    public User(){
        id = Integer.toString(IdGenerator.instance.getNextId());
        firstName = "";
        lastName = "";
        totalReviews ="0";
        reviewList = new LinkedList<>();
        friendsList = new LinkedList<>();
        password = "";
        totalRestaurantsVisited = "0";
    }
    public User(String firstName,String lastName){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.firstName = firstName;
        this.lastName = lastName;
        totalReviews ="0";
        password = lastName;
        reviewList = new LinkedList<>();
        friendsList = new LinkedList<>();
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
    public List<Review> getReviewList() {
        return reviewList;
    }
    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
    public List<User> getFriendsList() {
        return friendsList;
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
//---------------------------------//

    public void addReview(Review review){
        reviewList.add(review);
        int newTotal = Integer.valueOf(totalReviews)+1;
        totalReviews = Integer.toString(newTotal);
        boolean flag = false;
        for(int i=0;i<reviewList.size();i++){
            if(reviewList.get(i).getRestaurantId().equals(review.getRestaurantId())){
                flag = true;
                break;
            }
        }
        if(!flag){
           newTotal = Integer.valueOf(totalRestaurantsVisited)+1;
           totalRestaurantsVisited = Integer.toString(newTotal);
        }
    }
    public void deleteReview(Review review){
        reviewList.remove(review);
        int newTotal = Integer.valueOf(totalReviews)-1;
        totalReviews = Integer.toString(newTotal);
        String restaurant = review.getRestaurantId();
        boolean flag = false;
        for(int i=0;i<reviewList.size();i++){
            if(reviewList.get(i).getRestaurantId().equals(restaurant)){
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
        friendsList.add(friend);
    }
    public void deleteFriend(User friend){
        friendsList.remove(friend);
    }

}
