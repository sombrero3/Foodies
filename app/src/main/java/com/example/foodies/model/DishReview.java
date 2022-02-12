package com.example.foodies.model;

import android.media.Image;

public class DishReview extends Review{
    String dishId;
    String rating;
    String imageUrl;

    //-------Constructors-------//
    public DishReview() {
        dishId ="";
        restaurantId = "";
        userId = "";
        id = Integer.toString(IdGenerator.instance.getNextId());
        description = "";
        rating = "No rating yet";

    }
    public DishReview(String dishId, String restaurantId, String userId, String rating) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.description = "description";
        this.rating = rating;
        this.id = Integer.toString(IdGenerator.instance.getNextId());
    }
    public DishReview(String dishId, String restaurantId, String userId, String description, String rating) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.description = description;
        this.rating = rating;
        this.id = Integer.toString(IdGenerator.instance.getNextId());
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
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

//---------------------------------//


}
