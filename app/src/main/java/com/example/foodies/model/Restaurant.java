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
    String numOfUsersVisited;
    List<Dish> dishList;

    //-------Constructors-------//
    public Restaurant(){
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        name = "";
        location = "";
        rating = "No rating yet";
        dishList=new ArrayList<>();
        numOfUsersVisited = "0";
    }
    public Restaurant(String name) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = "";
        this.rating = "No rating yet";
        dishList=new ArrayList<>();
        numOfUsersVisited ="0";
    }
    public Restaurant(String name, String location) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        this.rating = "No rating yet";
        dishList=new ArrayList<>();
        numOfUsersVisited ="0";
    }
    public Restaurant(String name, String location, String rating) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        this.rating = rating;
        dishList=new ArrayList<>();
        numOfUsersVisited ="0";
    }
    public Restaurant(String name, String location,  Dish dish) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        this.dishList = new ArrayList<>();
        dishList.add(dish);
        rating = dish.getRating();
        numOfUsersVisited ="0";
    }
    public Restaurant(String name, String location,  ArrayList<Dish> dishList) {
        this.id = Integer.toString(IdGenerator.instance.getNextId());
        this.name = name;
        this.location = location;
        this.dishList = dishList;
        updateRating();
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
    public List<Dish> getDishList() {
        return dishList;
    }
    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
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
    //    public void setRating(String rating) {
//        this.rating = rating;
//    }

    //---------------------------------//

    public void updateRating(){
        double f ,reminder,sum=0,avg;

        for(int i=0;i<dishList.size();i++){
            sum+= Double.parseDouble(dishList.get(i).getRating());
        }

        f = sum/dishList.size();
        avg = Math.floor(sum/dishList.size());
        reminder = f - avg;
        if(reminder<0.25){
            rating =Double.toString(avg);
        }
        else if(reminder>=0.25 && reminder < 0.75){
            rating = Double.toString(avg+0.5);
        }
        else if(reminder>=0.75){
            rating=Double.toString(avg+1);
        }
    }
    public void addDish(Dish dish){
        dishList.add(dish);
        if(!dish.getRating().equals("No rating yet")) {
            updateRating();
        }
    }
    public void deleteDish(Dish dish){
        dishList.remove(dish);
        if(!dish.getRating().equals("No rating yet")) {
            updateRating();
        }
    }
}
