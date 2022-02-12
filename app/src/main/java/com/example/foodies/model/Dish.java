package com.example.foodies.model;

import android.media.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Dish {
    String restaurantId;
    //List<DishReview> dishReviewList;
    String id;
    String name;
    String price;
    String description;
    String imageUrl;
    String rating;


    //-------Constructors-------//
    public Dish(){
        id = Integer.toString(IdGenerator.instance.getNextId());
        restaurantId = "";
        name = "";
        price = "";
        rating ="No rating yet";
        description = "";
      //  dishReviewList = new ArrayList<>();
    }
    public Dish(String name){
        id = Integer.toString(IdGenerator.instance.getNextId());
        restaurantId = "";
        this.name = name;
        price = "";
        rating ="No rating yet";
        description = "";
        //dishReviewList = new LinkedList<>();
    }
    public Dish( String restaurantId, String name, String price, String description){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        //dishReviewList = new LinkedList<>();
        rating ="No rating yet";
    }
    public Dish( String restaurantId, String name, String price){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = "";
        //dishReviewList = new LinkedList<>();
        rating ="No rating yet";
    }
    public Dish( String restaurantId, String name, String price, String description, DishReview dishReview){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        //dishReviewList = new LinkedList<>();
        //dishReviewList.add(dishReview);
        rating = dishReview.getRating();
    }


    //-------Getters and Setters-------//
    public String getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
//    public List<DishReview> getReviewList() {
//        return dishReviewList;
//    }
//    public void setReviewList(List<DishReview> dishReviewList) {
//        this.dishReviewList = dishReviewList;
//    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getId() {
        return id;
    }
    public String getRating() {
    return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    //---------------------------------//


//    public void addReview(DishReview dishReview){
//        dishReviewList.add(dishReview);
//        updateRating();
//    }
//    public void deleteReview(DishReview dishReview){
//        dishReviewList.remove(dishReview);
//        updateRating();
//    }

}
