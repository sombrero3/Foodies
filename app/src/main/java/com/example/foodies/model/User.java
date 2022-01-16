package com.example.foodies.model;

import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class User {
    String id;
    String firstName;
    String lastName;
    String totalReviews;
    Image image;
    List<Review> reviewList;
    //List<User> friends
    List<String> restaurantsVisitedId;

    //-------Constructors-------//
    public User(){
        id = Integer.toString(IdGenerator.instance.getNextId());
        firstName = "";
        lastName = "";
        totalReviews ="0";
        reviewList = new ArrayList<>();
        restaurantsVisitedId = new ArrayList<>();
    }
    public User(String firstName,String lastName){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.firstName = firstName;
        this.lastName = lastName;
        totalReviews ="0";
        reviewList = new ArrayList<>();
        restaurantsVisitedId = new ArrayList<>();
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
    public List<String> getRestaurantsVisitedId() {
        return restaurantsVisitedId;
    }
    public void setRestaurantsVisitedId(List<String> restaurantsVisitedId) {
        this.restaurantsVisitedId = restaurantsVisitedId;
    }
    //---------------------------------//

    public void addReview(Review review){
        reviewList.add(review);
        int newTotal = Integer.valueOf(totalReviews)+1;
        totalReviews = Integer.toString(newTotal);
        if(!restaurantsVisitedId.contains(review.getRestaurantId())){
            restaurantsVisitedId.add(review.getRestaurantId());
        }
    }
    public void deleteReview(Review review){
        reviewList.remove(review);
        int newTotal = Integer.valueOf(totalReviews)-1;
        totalReviews = Integer.toString(newTotal);
        for(int i=0;i<reviewList.size();i++){
            if(reviewList.get(i).getRestaurantId().equals(review.getUserId())){
                restaurantsVisitedId.remove(review.getRestaurantId());
            }
        }
    }
}
