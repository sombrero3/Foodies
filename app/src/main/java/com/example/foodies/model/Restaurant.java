package com.example.foodies.model;

import android.media.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Restaurant {
    String id;
    String name;
    String location;
    Image image;
    String rating;
    String numOfUsersVisited;

    //-------Constructors-------//
    public Restaurant(){
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        name = "";
        location = "";
        rating = "No rating yet";
        numOfUsersVisited = "0";
    }
    public Restaurant(String name) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = "";
        this.rating = "No rating yet";
        numOfUsersVisited ="0";
    }
    public Restaurant(String name, String location) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        this.rating = "No rating yet";
        numOfUsersVisited ="0";
    }
    public Restaurant(String name, String location, String rating) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        this.rating = rating;
        numOfUsersVisited ="0";
    }
    public Restaurant(String name, String location,  Dish dish) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        rating = dish.getRating();
        numOfUsersVisited ="0";
    }

    //-------Getters and Setters-------//
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getRating() {
        return rating;
    }
    public String getNumOfUsersVisited() {
        return numOfUsersVisited;
    }
    public void setNumOfUsersVisited(String numOfUsersVisited) {
        this.numOfUsersVisited = numOfUsersVisited;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }

//---------------------------------//


//    public void addGeneralReview(Review review){
//        for(Review rev:generalReviewList){
//            if(review.getUserId().equals(rev.getUserId())){
//                generalReviewList.remove(rev);
//            }
//        }
//        generalReviewList.add(review);
//    }
//    public void deleteGeneralReview(Review review){
//        generalReviewList.remove(review);
//    }
//    public void addDish(Dish dish){
//        dishList.add(dish);
//        updateRating();
//
//    }
//    public void deleteDish(Dish dish){
//        dishList.remove(dish);
//        if(!dish.getRating().equals("No rating yet")) {
//            updateRating();
//        }
//    }
//    public String getGeneralReviewDescriptionByUserId(String userId){
//        for(Review review:generalReviewList){
//            if(review.getUserId().equals(userId)){
//                return review.description;
//            }
//        }
//        return "No general review yet";
//    }
}
