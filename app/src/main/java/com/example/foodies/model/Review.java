package com.example.foodies.model;

import android.media.Image;

import androidx.room.Entity;

public class Review {
    String restaurantId;
    String userId;
    String id;
    String description;
    boolean deleted = false;
    Long updateDate = new Long(0);

    public Review(){
        id = Integer.toString(IdGenerator.instance.getNextId());
        userId = "";
        restaurantId = "";
        description = "";
    }
    public Review(String restaurantId,String userId){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.userId = userId;
        this.restaurantId = restaurantId;
        description = "";
    }
    public Review(String restaurantId,String userId,String description){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.description = description;
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
    public void setId(String id) {
        this.id = id;
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
}
