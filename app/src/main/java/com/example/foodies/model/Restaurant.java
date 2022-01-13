package com.example.foodies.model;

import android.media.Image;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    String id;
    String name;
    String location;
    Image image;
    String rating;
    List<String> usersVisited;
    List<Dish> dishList;

    //-------Constructors-------//
    public Restaurant(){
        String id = Integer.toString(IdGenerator.instance.getNextId());
        name = "";
        location = "";
        rating = "No rating yet";
        dishList=new ArrayList<>();
        usersVisited = new ArrayList<>();
    }
    public Restaurant(String name) {
        String id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = "";
        this.rating = "";
        dishList=new ArrayList<>();
        usersVisited = new ArrayList<>();
    }
    public Restaurant(String name, String location) {
        String id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        this.rating = "";
        dishList=new ArrayList<>();
        usersVisited = new ArrayList<>();
    }
    public Restaurant(String name, String location, String rating) {
        String id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        this.rating = rating;
        dishList=new ArrayList<>();
        usersVisited = new ArrayList<>();
    }
    public Restaurant(String name, String location,  Dish dish) {
        String id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        this.dishList = new ArrayList<>();
        dishList.add(dish);
        rating = dish.getRating();
        usersVisited = new ArrayList<>();
    }
    public Restaurant(String name, String location,  ArrayList<Dish> dishList) {
        String id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        this.dishList = dishList;
        updateRating();
        usersVisited = new ArrayList<>();
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
    public List<Dish> getDishList() {
        return dishList;
    }
    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }
    public List<String> getUsersVisited() {
        return usersVisited;
    }
    public void setUsersVisited(List<String> usersVisited) {
        this.usersVisited = usersVisited;
    }
    //    public void setRating(String rating) {
//        this.rating = rating;
//    }

    //---------------------------------//

    public void updateRating(){
        int sum=0,avg;
        double f ,reminder,res;

        for(int i=0;i<dishList.size();i++){
            sum+= Integer.valueOf(dishList.get(i).getRating());
        }

        f = sum/dishList.size();
        avg = sum/dishList.size();
        reminder = f - avg;
        if(reminder<0.25){
            rating = Integer.toString(sum);
        }
        else if(reminder>=0.25 && reminder < 0.75){
            res = avg +0.5;
            rating = Double.toString(res);
        }
        else if(reminder>=0.75){
            rating=Integer.toString(avg+1);
        }
    }
    public void addDish(Dish dish){
        dishList.add(dish);
        updateRating();
    }
    public void deleteDish(Dish dish){
        dishList.remove(dish);
        updateRating();
    }
}
