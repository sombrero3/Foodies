package com.example.foodies.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Model {

    List<User> userList = new LinkedList<>();
    List<Restaurant> restaurantList = new ArrayList<>();
    List<Dish> dishList = new LinkedList<>();
    List<Review> reviewList = new LinkedList<>();

    public static final Model instance = new Model();

    public Model() {
    }

    //-------Getters and Setters-------//
    public List<User> getUserList() {
        return userList;
    }
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }
    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }
    public List<Dish> getDishList() {
        return dishList;
    }
    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }
    public List<Review> getReviewList() {
        return reviewList;
    }
    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }
    //---------------------------------//

    public Dish getDishById(String dishId){
        for(int i=0;i<dishList.size();i++){
            if(dishList.get(i).id.equals(dishId)){
                return dishList.get(i);
            }
        }
        return new Dish();
    }
    public Restaurant getRestaurantById(String id){
        for(int i=0;i<restaurantList.size();i++){
            if(restaurantList.get(i).getId().equals(id)){
                return restaurantList.get(i);
            }
        }
        return new Restaurant();
    }
    public Review getReviewById(String id){
        for(int i=0;i<reviewList.size();i++){
            if(reviewList.get(i).getId().equals(id)){
                return reviewList.get(i);
            }
        }
        return new Review();
    }
    public User getUserById(String id){
        for(int i=0;i< userList.size();i++){
            if(userList.get(i).getId().equals(id)){
                return userList.get(i);
            }
        }
        return new User();
    }
}
