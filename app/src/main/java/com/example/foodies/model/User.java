package com.example.foodies.model;

import android.media.Image;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    String email;

    String id;
    String firstName;
    String lastName;
    String totalReviews;
    String password;
    String imageUrl;
    boolean deleted = false;
    Long updateDate = new Long(0);
    String totalRestaurantsVisited;

    //-------Constructors-------//
    public User(){
        id = Integer.toString(IdGenerator.instance.getNextId());
        firstName = "";
        lastName = "";
        email ="No email address";
        totalReviews ="0";
        password = "";
        totalRestaurantsVisited = "0";
    }
    public User(String firstName,String password){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.firstName = firstName;
        this.lastName = password;
        email ="No email address";
        totalReviews ="0";
        this.password = password;
        totalRestaurantsVisited = "0";
    }
    public User(String firstName,String password,String email){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.firstName = firstName;
        this.lastName = password;
        this.email = email;
        totalReviews ="0";
        this.password = password;
        totalRestaurantsVisited = "0";
    }
    //TODO- id
    public User(String email,String password,String firstName,String lastName){
        //id = Integer.toString(IdGenerator.instance.getNextId());
        id = "something";
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        totalReviews ="0";
        this.password = password;
        totalRestaurantsVisited = "0";
    }

    //-------Getters and Setters-------//
    public String getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getTotalReviews() {
        return totalReviews;
    }
    public void setTotalReviews(String totalReviews) {
        this.totalReviews = totalReviews;
    }
    public String getTotalRestaurantsVisited() {
        return totalRestaurantsVisited;
    }
    public void setTotalRestaurantsVisited(String totalRestaurantsVisited) {
        this.totalRestaurantsVisited = totalRestaurantsVisited;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

    public void increaseTotalRestaurantsVisited() {
        int num = Integer.parseInt((totalRestaurantsVisited))+1;
        totalRestaurantsVisited = Integer.toString(num);
    }

    public void increaseTotalReviews() {
        int num = Integer.parseInt((totalReviews))+1;
        totalReviews = Integer.toString(num);
    }

    public Map<String, Object> toJason() {



        return null;
    }
}
