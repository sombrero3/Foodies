package com.example.foodies.model;

import android.media.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Dish {
    String restaurantId;
    String id;
    String name;
    String price;
    String description;
    String imageUrl;
    String rating;
    boolean deleted = false;
    Long updateDate = new Long(0);


    //-------Constructors-------//
    public Dish(){
        id = Integer.toString(IdGenerator.instance.getNextId());
        restaurantId = "";
        name = "";
        price = "";
        rating ="No rating yet";
        description = "";
    }
    public Dish(String name){
        id = Integer.toString(IdGenerator.instance.getNextId());
        restaurantId = "";
        this.name = name;
        price = "";
        rating ="No rating yet";
        description = "";
    }
    public Dish( String restaurantId, String name, String price, String description){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        rating ="No rating yet";
    }
    public Dish( String restaurantId, String name, String price){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = "";
        rating ="No rating yet";
    }
    public Dish( String restaurantId, String name, String price, String description, DishReview dishReview){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        rating = dishReview.getRating();
    }


    //-------Getters and Setters-------//
    public String getRestaurantId() {
        return restaurantId;
    }
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
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
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public Long getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }
    //---------------------------------//




}
