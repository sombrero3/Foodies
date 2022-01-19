package com.example.foodies.model;

import com.example.foodies.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Model {

    List<User> userList = new LinkedList<>();
    List<Restaurant> restaurantList = new ArrayList<>();
    List<Dish> dishList = new LinkedList<>();
    List<Review> reviewList = new LinkedList<>();

    public static final Model instance = new Model();

    private Model() {
        for(int i=0;i<10;i++){
            User user = new User("name "+i, "" + i );
            userList.add(user);
        }
        Random rand = new Random();
        for(int i=0;i<10;i++){
            for(int j=0;j<4;j++) {
                int x = Math.abs(rand.nextInt() % 10);

                if( userList.get(i).getFriendsList().size() == 0 ){
                    userList.get(i).addFriend(userList.get(x));
                    userList.get(x).addFriend(userList.get(i));
                }else if (!userList.get(i).getFriendsList().contains(userList.get(x))) {
                    userList.get(i).addFriend(userList.get(x));
                    userList.get(x).addFriend(userList.get(i));
                }
            }
        }
        for(int i=0;i<10;i++){
            Restaurant res = new Restaurant("Restaurant name "+i);
            for(int j=0;j<10;j++){
                Dish dish = new Dish("Dish name "+i + " " + j);
                for(int k=0;k<10;k++){
                        Review review = new Review(dish.getId(), res.getId(),userList.get(k).getId(),"4");
                        dish.setPrice(Integer.toString(k)+"$");
                        reviewList.add(review);

                }
                dishList.add(dish);
            }
            restaurantList.add(res);
        }

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
        int size = userList.size();
        for(int i=0;i<size;i++){
            if(userList.get(i).getId().equals(user)){
                userList.get(i).addReview(review);
                break;
            }
        }
        String dish = review.getDishId();
        size = dishList.size();
        for(int i=0;i<size;i++){
            if(dishList.get(i).getId().equals(dish)){
                dishList.get(i).addReview(review);
                break;
            }
        }

    }
    public void addDish(Dish dish){
        String restaurant = dish.getRestaurantId();
        int size = restaurantList.size();
        for(int i=0;i<size;i++){
            if(restaurantList.get(i).getId().equals(restaurant)){
                restaurantList.get(i).addDish(dish);
            }
        }
        dishList.add(dish);
    }
    public void addRestaurant(Restaurant restaurant){
        restaurantList.add(restaurant);
    }
    public void addUser(User user){
        userList.add(user);
    }

    public void deleteReview(Review review){
        int size =dishList.size();
        for(int i=0; i<size;i++){                  // remove from the dish's review list
            if(dishList.get(i).getId().equals(review.getDishId())){
                dishList.get(i).deleteReview(review);
            }
        }
        size =userList.size();
        for(int i=0; i<size;i++){                  // remove the review from the user's review list
            if(userList.get(i).getId().equals(review.getUserId())){
                userList.get(i).deleteReview(review);
            }
        }
        reviewList.remove(review);
    }
    public void deleteDish(Dish dish){
        int size = dish.getReviewList().size();
        for(int i=0;i<size;i++){                   // remove the reviews on this dish from each users review list
            String user = dish.getReviewList().get(i).getUserId();
            int size2 = userList.size();
            for(int j=0; j<size2;j++){
                if(userList.get(j).getId().equals(user)){
                    userList.get(j).deleteReview(dish.getReviewList().get(i));
                }
            }
        }
        size = restaurantList.size();
        for(int i=0;i<size;i++){                  // remove the dish from the restautant's dish list
            String restaurant = dish.getRestaurantId();
            if(restaurantList.get(i).getId().equals(restaurant)){
                restaurantList.get(i).deleteDish(dish);
            }
        }
        dishList.remove(dish);
    }
    public void deleteRestaurant(Restaurant restaurant){
        int size = restaurant.getDishList().size();
        for(int i=0;i<size;i++){                  // for each restaurant's dish
            int size2 = restaurant.getDishList().get(i).getReviewList().size();
            for(int j=0;j<size2;j++) {            // remove all dish's reviews from their user's review list than delete review
                int size3 = userList.size();
                for (int k = 0; k < size3;k++) {
                    if(restaurant.getDishList().get(i).getReviewList().get(j).userId.equals(userList.get(k).getId())){
                        userList.get(k).deleteReview(restaurant.getDishList().get(i).getReviewList().get(j));
                        reviewList.remove(restaurant.getDishList().get(i).getReviewList().get(j));
                    }
                }
            }
            dishList.remove(restaurant.getDishList().get(i));  // remove dishs
        }
        restaurantList.remove(restaurant); // remove restaurant
    }
    public void deleteUser(User user){
        int size = user.getFriendsList().size(); // remove user from all of his friend's users list
        for(int i=0;i<size;i++){
            user.getFriendsList().get(i).getFriendsList().remove(user);
        }
        size = user.getReviewList().size();  // remove all user's reviews from their dishes
        for(int i=0;i<size;i++){
            int size2 =dishList.size();
            String dish = user.getReviewList().get(i).getDishId();
            for(int j=0;j<size2;j++) {
                if(dishList.get(j).getId().equals(dish)){
                    dishList.get(j).deleteReview(user.getReviewList().get(i));
                }
            }
        }
        userList.remove(user); // delete from Model user list
    }

    public List<User> getAllUsersThatHaveReviewsOnRestaurantByRestaurantId(String restaurantId){
        List<User> result = new LinkedList<>();
        for(int i=0;i<reviewList.size();i++){
            if(reviewList.get(i).getRestaurantId().equals(restaurantId) && !result.contains(getUserById(reviewList.get(i).getUserId()))){
                result.add(getUserById(reviewList.get(i).getUserId()));
            }
        }
        return result;
    }
    public List<Restaurant> getAllRestaurantsThatUserHasReviewsOnByUserId(String userId){
        List<Restaurant> result = new LinkedList<>();
        for(int i=0;i<reviewList.size();i++){
            if(reviewList.get(i).getUserId().equals(userId) && !result.contains(getRestaurantById(reviewList.get(i).getRestaurantId()))){
                result.add(getRestaurantById(reviewList.get(i).getRestaurantId()));
            }
        }
        return result;
    }
    public List<Dish> getAllDishesThatTheUserHasAReviewedOnInThisRestaurantByUserIdAndRestaurantId(String userId,String restaurantId){
        List<Dish> result = new LinkedList<>();
        for(int i=0;i<reviewList.size();i++){
            if(reviewList.get(i).getUserId().equals(userId) && reviewList.get(i).getRestaurantId().equals(restaurantId) && !result.contains(getDishById(reviewList.get(i).getDishId()))){
                result.add(getDishById(reviewList.get(i).getDishId()));
            }
        }
        return result;
    }
}
