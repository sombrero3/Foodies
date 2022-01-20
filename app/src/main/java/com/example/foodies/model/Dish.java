package com.example.foodies.model;

import android.media.Image;

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
    List<Image> images;
    String rating;
    boolean vegetarian;

    //-------Constructors-------//
    public Dish(){
        id = Integer.toString(IdGenerator.instance.getNextId());
        restaurantId = "";
        name = "";
        price = "";
        rating ="No rating yet";
        description = "";
        vegetarian = false;
        reviewList = new ArrayList<>();
        images = new LinkedList<>();

    }
    public Dish(String name){
        id = Integer.toString(IdGenerator.instance.getNextId());
        restaurantId = "";
        this.name = name;
        price = "";
        rating ="No rating yet";
        description = "";
        vegetarian = false;
        reviewList = new ArrayList<>();
        images = new LinkedList<>();

    }
    public Dish( String restaurantId, String name, String price, String description, boolean vegetarian){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.vegetarian = vegetarian;
        images = new LinkedList<>();
        reviewList = new ArrayList<>();
        rating ="No rating yet";
    }
    public Dish( String restaurantId, String name, String price){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = "";
        this.vegetarian = false;
        images = new LinkedList<>();
        reviewList = new ArrayList<>();
        rating ="No rating yet";
    }
    public Dish( String restaurantId, String name, String price, String description, boolean vegetarian, Review review){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.vegetarian = vegetarian;
        images = new LinkedList<>();
        reviewList = new ArrayList<>();
        reviewList.add(review);
        rating = review.getRating();
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
    public List<Image> getImages() {
        return images;
    }
    public void setImages(List<Image> images) {
        this.images = images;
    }
    public String getId() {
        return id;
    }
    public String getRating() {
    return rating;
    }
    //---------------------------------//

    public void updateRating(){
        double f ,reminder,sum=0,avg;

        for(int i=0;i<reviewList.size();i++){
            sum+= Double.parseDouble(reviewList.get(i).getRating());
        }

        f = sum/reviewList.size();
        avg = Math.floor(sum/reviewList.size());
        reminder = f - avg;
        if(reminder<0.25){
            rating =Double.toString(avg);
        }
        else if(reminder>=0.25 && reminder < 0.75){
            rating = Double.toString(avg+0.5);
        }
        else if(reminder>=0.75){
            rating=Double.toString(avg+1);
        }
    }
    public void addReview(Review review){
        reviewList.add(review);
        updateRating();
    }
    public void deleteReview(Review review){
        reviewList.remove(review);
        updateRating();
    }
    public void addImage(Image image){
        images.add(image);
    }
    public void deleteImage(Image image){ images.remove(image); }
}
