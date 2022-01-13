package com.example.foodies.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Dish {
    String restaurantId;
    List<Review> reviewList;
    String id;
    String name;
    String price;
    String description;
    List<String> images;
    boolean vegetarian;

    //-------Constructors-------//
    public Dish(){
        id ="";
        restaurantId = "";
        name = "";
        price = "";
        description = "";
        vegetarian = false;
        reviewList = new ArrayList<>();
        images = new LinkedList<>();
    }
    public Dish( String id,String restaurantId, String name, String price, String description, boolean vegetarian){
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.vegetarian = vegetarian;
        images = new LinkedList<>();
        reviewList = new ArrayList<>();
    }
    public Dish( String id,String restaurantId, String name, String price, String description, boolean vegetarian,String image){
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        images = new LinkedList<>();
        images.add(image);
        this.vegetarian = vegetarian;
        reviewList = new ArrayList<>();
    }
    public Dish( String id,String restaurantId, String name, String price, String description, boolean vegetarian, Review review){
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.vegetarian = vegetarian;
        images = new LinkedList<>();
        reviewList = new ArrayList<>();
        reviewList.add(review);
    }
    public Dish( String id,String restaurantId, String name, String price, String description, boolean vegetarian,String image, Review review){
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        images = new LinkedList<>();
        images.add(image);
        this.vegetarian = vegetarian;
        reviewList = new ArrayList<>();
        reviewList.add(review);
    }

    //-------Getters and Setters-------//
    public String getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
    public List<Review> getReviewList() {
        return reviewList;
    }
    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
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
    public boolean isVegetarian() {
        return vegetarian;
    }
    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }
    public List<String> getImages() {
        return images;
    }
    public void setImages(List<String> images) {
        this.images = images;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    //---------------------------------//
    public void addReview(Review review){
        reviewList.add(review);
    }
    public void deleteReview(Review review){
      reviewList.remove(review);
    }
    public void addImage(String image){
        images.add(image);
    }
    public void deleteImage(String image){ images.remove(image); }
}
