package com.example.foodies.model;

import android.media.Image;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Restaurant {
    String id;
    String name;
    String location;
    String rating;
    String numOfUsersVisited;
    String imageUrl;
    boolean deleted = false;
    Long updateDate = new Long(0);

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
    public void setId(String id) {
        this.id = id;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
