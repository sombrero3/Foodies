package com.example.foodies.model;

import java.util.LinkedList;
import java.util.List;

public class Model {

    List<User> userList = new LinkedList<>();
    List<Restaurant> restaurantList = new LinkedList<>();
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
            if(dishId == dishList.get(i).id){
                return dishList.get(i);
            }
        }
        return new Dish();
    }
}
