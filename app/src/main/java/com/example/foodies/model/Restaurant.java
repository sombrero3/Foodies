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
    List<Review> generalReviewList;
    List<Dish> dishList;

    //-------Constructors-------//
    public Restaurant(){
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        name = "";
        location = "";
        rating = "No rating yet";
        generalReviewList = new LinkedList<>();
        dishList=new ArrayList<>();
        numOfUsersVisited = "0";
    }
    public Restaurant(String name) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = "";
        this.rating = "No rating yet";
        generalReviewList = new LinkedList<>();
        dishList=new ArrayList<>();
        numOfUsersVisited ="0";
    }
    public Restaurant(String name, String location) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        this.rating = "No rating yet";
        generalReviewList = new LinkedList<>();
        dishList=new ArrayList<>();
        numOfUsersVisited ="0";
    }
    public Restaurant(String name, String location, String rating) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        this.rating = rating;
        generalReviewList = new LinkedList<>();
        dishList=new ArrayList<>();
        numOfUsersVisited ="0";
    }
    public Restaurant(String name, String location,  Dish dish) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        generalReviewList = new LinkedList<>();
        this.dishList = new ArrayList<>();
        dishList.add(dish);
        rating = dish.getRating();
        numOfUsersVisited ="0";
    }
    public Restaurant(String name, String location,  ArrayList<Dish> dishList) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        generalReviewList = new LinkedList<>();
        this.dishList = dishList;
        updateRating();
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
    public List<Dish> getDishList() {
        return dishList;
    }
    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
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
    public List<Review> getGeneralReviewList() {
        return generalReviewList;
    }
    public void setGeneralReviewList(List<Review> generalReviewList) {
        this.generalReviewList = generalReviewList;
    }
//---------------------------------//

    public void updateRating(){
        double f ,reminder,sum=0,avg,counter=0;

        for(Dish dish:dishList){
            if(!dish.getRating().equals("No rating yet")) {
                sum += Double.parseDouble(dish.getRating());
                counter++;
            }
        }
        if(counter>0) {
            f = sum / counter;
            avg = Math.floor(sum / counter);
            reminder = f - avg;
            if (reminder < 0.25) {
                rating = Double.toString(avg);
            } else if (reminder >= 0.25 && reminder < 0.75) {
                rating = Double.toString(avg + 0.5);
            } else if (reminder >= 0.75) {
                rating = Double.toString(avg + 1);
            }
        }else{
            rating = "No rating yet";
        }
    }
    public void addGeneralReview(Review review){
        for(Review rev:generalReviewList){
            if(review.getUserId().equals(rev.getUserId())){
                generalReviewList.remove(rev);
            }
        }
        generalReviewList.add(review);
    }
    public void deleteGeneralReview(Review review){
        generalReviewList.remove(review);
    }
    public void addDish(Dish dish){
        dishList.add(dish);
        updateRating();

    }
    public void deleteDish(Dish dish){
        dishList.remove(dish);
        if(!dish.getRating().equals("No rating yet")) {
            updateRating();
        }
    }
    public String getGeneralReviewDescriptionByUserId(String userId){
        for(Review review:generalReviewList){
            if(review.getUserId().equals(userId)){
                return review.description;
            }
        }
        return "No general review yet";
    }
}
