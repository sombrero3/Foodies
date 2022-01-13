package com.example.foodies.model;

import android.media.Image;

public class Review {
    String dishId;
    String restaurantId;
    String userId;
    String id;
    Image image;
    String description;
    int rating;

    //-------Constructors-------//
    public Review() {
        dishId ="";
        restaurantId = "";
        userId = "";
        id = IdGenerator.instance.getNextId().toString();
        description = "";
        rating = 0;
    }
    public Review(String dishId, String restaurantId, String userId, String description, int rating) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.description = description;
        this.rating = rating;
        this.id = IdGenerator.instance.getNextId().toString();
    }


    //-------Getters and Setters-------//
    public String getDishId() {
        return dishId;
    }
    public void setDishId(String dishId) {
        this.dishId = dishId;
    }
    public String getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
        Model.instance.getDishById(dishId).updateRating();
    }
    //---------------------------------//
}
