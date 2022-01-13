package com.example.foodies.model;

import java.util.LinkedList;
import java.util.List;

public class Model {

    List<User> userList = new LinkedList<>();
    List<Restaurant> restaurantList = new LinkedList<>();
    List<Dish> dishList = new LinkedList<>();
    List<Review> reviewList = new LinkedList<>();

    public static final Model instance = new Model();

}
