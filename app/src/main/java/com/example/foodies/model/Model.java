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

    public void addReview(Review review){
        String user = review.getUserId();
        int size = Model.instance.userList.size();
        for(int i=0;i<size;i++){
            if(Model.instance.userList.get(i).getId().equals(user)){
                Model.instance.userList.get(i).addReview(review);
                break;
            }
        }
        String dish = review.getDishId();
        size = Model.instance.dishList.size();
        for(int i=0;i<size;i++){
            if(Model.instance.dishList.get(i).getId().equals(dish)){
                Model.instance.dishList.get(i).addReview(review);
                break;
            }
        }

    }
    public void addDish(Dish dish){
        String restaurant = dish.getRestaurantId();
        int size = Model.instance.restaurantList.size();
        for(int i=0;i<size;i++){
            if(Model.instance.restaurantList.get(i).getId().equals(restaurant)){
                Model.instance.restaurantList.get(i).addDish(dish);
            }
        }
        Model.instance.dishList.add(dish);
    }
    public void addRestaurant(Restaurant restaurant){
        Model.instance.restaurantList.add(restaurant);
    }
    public void addUser(User user){
        Model.instance.userList.add(user);
    }

    public void deleteReview(Review review){
        int size =Model.instance.dishList.size();
        for(int i=0; i<size;i++){                  // remove from the dish's review list
            if(Model.instance.dishList.get(i).getId().equals(review.getDishId())){
                Model.instance.dishList.get(i).deleteReview(review);
            }
        }
        size =Model.instance.userList.size();
        for(int i=0; i<size;i++){                  // remove the review from the user's review list
            if(Model.instance.userList.get(i).getId().equals(review.getUserId())){
                Model.instance.userList.get(i).deleteReview(review);
            }
        }
        reviewList.remove(review);
    }
    public void deleteDish(Dish dish){
        int size = dish.getReviewList().size();
        for(int i=0;i<size;i++){                   // remove the reviews on this dish from each users review list
            String user = dish.getReviewList().get(i).getUserId();
            int size2 = Model.instance.userList.size();
            for(int j=0; j<size2;j++){
                if(Model.instance.userList.get(j).getId().equals(user)){
                    Model.instance.userList.get(j).deleteReview(dish.getReviewList().get(i));
                }
            }
        }
        size = Model.instance.restaurantList.size();
        for(int i=0;i<size;i++){                  // remove the dish from the restautant's dish list
            String restaurant = dish.getRestaurantId();
            if(Model.instance.restaurantList.get(i).getId().equals(restaurant)){
                Model.instance.restaurantList.get(i).deleteDish(dish);
            }
        }
        dishList.remove(dish);
    }
    public void deleteRestaurant(Restaurant restaurant){
        int size = restaurant.getDishList().size();
        for(int i=0;i<size;i++){                  // for each restaurant's dish
            int size2 = restaurant.getDishList().get(i).getReviewList().size();
            for(int j=0;j<size2;j++) {            // remove all dish's reviews from their user's review list than delete review
                int size3 = Model.instance.userList.size();
                for (int k = 0; k < size3;k++) {
                    if(restaurant.getDishList().get(i).getReviewList().get(j).userId.equals(Model.instance.userList.get(k).getId())){
                        Model.instance.userList.get(k).deleteReview(restaurant.getDishList().get(i).getReviewList().get(j));
                        Model.instance.reviewList.remove(restaurant.getDishList().get(i).getReviewList().get(j));
                    }
                }
            }
            Model.instance.dishList.remove(restaurant.getDishList().get(i));  // remove dishs
        }
        Model.instance.restaurantList.remove(restaurant); // remove restaurant
    }
    public void deleteUser(User user){
        int size = user.getFriendsList().size(); // remove user from all of his friend's users list
        for(int i=0;i<size;i++){
            user.getFriendsList().get(i).getFriendsList().remove(user);
        }
        size = user.getReviewList().size();  // remove all user's reviews from their dishes
        for(int i=0;i<size;i++){
            int size2 =Model.instance.dishList.size();
            String dish = user.getReviewList().get(i).getDishId();
            for(int j=0;j<size2;j++) {
                if(Model.instance.dishList.get(j).getId().equals(dish)){
                    Model.instance.dishList.get(j).deleteReview(user.getReviewList().get(i));
                }
            }
        }
        userList.remove(user); // delete from Model user list
    }
}
