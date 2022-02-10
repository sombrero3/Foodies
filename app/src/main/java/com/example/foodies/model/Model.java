package com.example.foodies.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodies.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Model {

    List<User> userList = new LinkedList<>();
    List<Restaurant> restaurantList = new ArrayList<>();
    List<Dish> dishList = new LinkedList<>();
    List<DishReview> dishReviewList = new LinkedList<>();
    User signedUser;
    boolean signedFlag;

    public static final Model instance = new Model();

    private Model() {
        signedFlag = false;
        for(int i=1;i<11;i++){
            User user = new User("name "+i, "" + i ,"email@gmail.com");
            userList.add(user);
        }

        Random rand = new Random();
        for(int j=0;j<userList.size()-2;j++){
            for(int i=0;i<2;i++) {
                int x = Math.abs(rand.nextInt() % (userList.size()-2));
                if (!userList.get(j).getFriendsList().contains(userList.get(x)) && x!=j ) {
                    userList.get(i).addFriend(userList.get(x));
                    userList.get(x).addFriend(userList.get(i));
                }
            }
        }
        setSignedUser(userList.get(0));
        setSignedFlag(true);
        friendRequestCreateRequestFromUser(userList.get(8));
        friendRequestCreateRequestFromUser(userList.get(9));

        Random random = new Random();
        for(int i=0;i<10;i++){
            Restaurant res = new Restaurant("Restaurant name "+i);
            for(int j=0;j<10;j++){
                Dish dish = new Dish("Dish name "+i + " " + j);
                for(int k=0;k<10;k++){
                        String rating  = Integer.toString(Math.abs((random.nextInt()%5))+1);
                        DishReview dishReview = new DishReview(dish.getId(), res.getId(),userList.get(k).getId(),rating);
                        dish.setPrice(Integer.toString(k)+"$");
                        userList.get(k).addReview(dishReview);
                        dishReviewList.add(dishReview);
                        dish.addReview(dishReview);
                }
                dishList.add(dish);
                res.addDish(dish);
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
    public List<DishReview> getReviewList() {
        return dishReviewList;
    }
    public void setReviewList(List<DishReview> dishReviewList) {
        this.dishReviewList = dishReviewList;
    }

    public User getSignedUser() {
        return signedUser;
    }

    public void setSignedUser(User signedUser) {
        this.signedUser = signedUser;
    }

    public boolean isSignedFlag() {
        return signedFlag;
    }

    public void setSignedFlag(boolean signedFlag) {
        this.signedFlag = signedFlag;
    }
    //---------------------------------//
    public boolean confirmUserLogin(String name,String password){
        for (User user:userList) {
            if(user.getFirstName().equals(name) && user.getPassword().equals(password)){
                setSignedFlag(true);
                setSignedUser(user);
                return true;
            }
        }
        return false;
    }
    public Dish getDishById(String dishId){
        for(Dish dish:dishList){
            if(dish.getId().equals(dishId)){
                return dish;
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
    public DishReview getReviewById(String id){
        for(int i = 0; i< dishReviewList.size(); i++){
            if(dishReviewList.get(i).getId().equals(id)){
                return dishReviewList.get(i);
            }
        }
        return new DishReview();
    }
    public User getUserById(String id){
        for(int i=0;i< userList.size();i++){
            if(userList.get(i).getId().equals(id)){
                return userList.get(i);
            }
        }
        return new User();
    }

    public void addDishReview(DishReview dishReview){
        getSignedUser().addReview(dishReview);
        getDishById(dishReview.getDishId()).addReview(dishReview);
        dishReviewList.add(dishReview);
    }
    public void addDish(Dish dish){
      getRestaurantById(dish.getRestaurantId()).addDish(dish);
      dishList.add(dish);
    }
    public void addRestaurant(Restaurant restaurant){
        restaurantList.add(restaurant);
    }
    public void addUser(User user){
        userList.add(user);
    }

    public void deleteReview(DishReview dishReview){
        getDishById(dishReview.getDishId()).deleteReview(dishReview);       // remove from the dish's review list
        getUserById(dishReview.getUserId()).deleteReview(dishReview);       // remove the review from the user's review list
        getRestaurantById(dishReview.getRestaurantId()).updateRating();
        dishReviewList.remove(dishReview);
    }
    public void deleteDish(Dish dish){
        int size = dish.getReviewList().size();
        for(int i=0;i<size;i++){                   // remove the reviews on this dish from each users review list
            String user = dish.getReviewList().get(i).getUserId();
            int size2 = userList.size();
            for(int j=0; j<size2;j++){
                if(userList.get(j).getId().equals(user)){
                    userList.get(j).deleteReview(dish.getReviewList().get(i));
                    break;
                }
            }
        }
        size = restaurantList.size();
        for(int i=0;i<size;i++){                  // remove the dish from the restautant's dish list
            String restaurant = dish.getRestaurantId();
            if(restaurantList.get(i).getId().equals(restaurant)){
                restaurantList.get(i).deleteDish(dish);
                break;
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
                        dishReviewList.remove(restaurant.getDishList().get(i).getReviewList().get(j));
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
        size = user.getDishReviewList().size();  // remove all user's reviews from their dishes
        for(int i=0;i<size;i++){
            int size2 =dishList.size();
            String dish = user.getDishReviewList().get(i).getDishId();
            for(int j=0;j<size2;j++) {
                if(dishList.get(j).getId().equals(dish)){
                    dishList.get(j).deleteReview(user.getDishReviewList().get(i));
                }
            }
        }
        userList.remove(user); // delete from Model user list
    }

    public List<User> getAllUsersThatHaveReviewsOnRestaurantByRestaurantId(String restaurantId){
        List<User> result = new LinkedList<>();
        for(int i = 0; i< dishReviewList.size(); i++){
            if(dishReviewList.get(i).getRestaurantId().equals(restaurantId) && !result.contains(getUserById(dishReviewList.get(i).getUserId()))){
                result.add(getUserById(dishReviewList.get(i).getUserId()));

            }
        }
        return result;
    }
    public List<Restaurant> getAllRestaurantsThatUserHasReviewsOnByUserId(String userId){
        List<Restaurant> result = new LinkedList<>();
        for(int i = 0; i< dishReviewList.size(); i++){
            if(dishReviewList.get(i).getUserId().equals(userId) && !result.contains(getRestaurantById(dishReviewList.get(i).getRestaurantId()))){
                result.add(getRestaurantById(dishReviewList.get(i).getRestaurantId()));

            }
        }
        return result;
    }
    public List<Dish> getAllDishesThatTheUserHasAReviewedOnInThisRestaurantByUserIdAndRestaurantId(String userId,String restaurantId){
        List<Dish> result = new LinkedList<>();
        List<DishReview> dishReviews = Model.instance.getUserById(userId).getDishReviewList();
        for(DishReview dishReview : dishReviews){
            if(dishReview.getUserId().equals(userId) && dishReview.getRestaurantId().equals(restaurantId) && !result.contains(getDishById(dishReview.getDishId()))){
                result.add(getDishById(dishReview.getDishId()));
            }
        }
        return result;
    }
    public DishReview getReviewOnDishByDishIdAndUserId(String dishId, String userId){
        List<DishReview> dishReviews = Model.instance.getUserById(userId).getDishReviewList();
        for(DishReview dishReview : dishReviews){
            if(dishReview.getDishId().equals(dishId) && dishReview.getUserId().equals(userId)){
                return dishReview;
            }
        }
        return dishReviews.get(0);
    }
    public String getRestaurantIdByName(String resName){
        for(int i=0;i<restaurantList.size();i++){
            if(restaurantList.get(i).getName().equals(resName)){
                return restaurantList.get(i).getId();
            }
        }
        return "No Such Restaurant";
    }
    public String getDishIdByRestaurantIdAndDishName(String resId,String dishName){
        Restaurant restaurant = getRestaurantById(resId);
        List<Dish> dishl = restaurant.getDishList();
        for(int i=0;i<dishl.size();i++){
            if(dishl.get(i).getName().equals(dishName)){
                return dishl.get(i).getId();
            }
        }
        return "No Such Dish";
    }
    public List<DishReview> getAllFriendsReviewsOnDishByDishId(String dishId){
        User signedUser = getSignedUser();
        String signedUserId = signedUser.getId();
        Dish dish = getDishById(dishId);
        List<User> friends = signedUser.getFriendsList();
        List<DishReview> dishReviews =  new LinkedList<>();
        for(DishReview rev:dish.getReviewList()){
            for(User friend:friends){
                if(rev.getUserId().equals(friend.getId()) && !rev.getUserId().equals(signedUserId)){
                    dishReviews.add(rev);
                }
            }
        }
        return dishReviews;
    }
    public List<User> getUsersByName(String name){
        List<User> result = new LinkedList<>();
        for (User user :userList) {
            if(user.getFirstName().contains(name)){
                result.add(user);
            }
        }
        return result;
    }
    public List<User> getUsersByEmail(String email){
        List<User> result = new LinkedList<>();
        for (User user : userList) {
            if (user.getEmail().contains(email)&& !user.getEmail().equals("No email address")) {
                result.add(user);
            }
        }
        return result;
    }
    public List<User> getUsersByNameAndEmail(String name,String email){
        List<User> result = new LinkedList<>();
        for (User user :userList) {
            if(user.getFirstName().contains(name)&& user.getEmail().contains(email)){
                result.add(user);
            }
        }
        return result;
    }
    public List<User> getNotFriendsUsersByName(String name) {
        List<User> result = new LinkedList<>();
        for (User user :userList) {
            if(!signedUser.getFriendsList().contains(user) && user.getFirstName().contains(name) && !signedUser.getId().equals(user.getId())){
                result.add(user);
            }
        }
        return result;
    }

    public List<User> getNotFriendsUsersByEmail(String email) {
        List<User> result = new LinkedList<>();
        for (User user : userList) {
            if (!signedUser.getFriendsList().contains(user) && user.getEmail().contains(email)&& !user.getEmail().equals("No email address")) {
                result.add(user);
            }
        }
        return result;
    }
    public List<User> getNotFriendsUsersByNameAndEmail(String name,String email){
        List<User> result = new LinkedList<>();
        for (User user :userList) {
            if(!signedUser.getFriendsList().contains(user) && user.getFirstName().contains(name)&& user.getEmail().contains(email)){
                result.add(user);
            }
        }
        return result;
    }
    public List<User> peopleYouMayKnow(){
        List<User> signedUserFriends , result;
        result = new LinkedList<>();
        signedUserFriends = signedUser.getFriendsList();
        for (User friend: signedUserFriends) {
            for (User friendfriends:friend.getFriendsList()) {
                if(!result.contains(friendfriends) && !signedUserFriends.contains(friendfriends) && !friendfriends.getId().equals(signedUser.getId())) {
                    result.add(friendfriends);
                }
            }
        }
        return result;
    }

    public String getRestaurantRatingGivenByAUser(User user,String restaurantId){
        List<DishReview> dishReviews = user.getDishReviewList();
        double f ,reminder,sum=0,avg;
        int counter=0;
        String rating="No rating yet";
        for (DishReview dishReview : dishReviews) {
            if(dishReview.getRestaurantId().equals(restaurantId) && !dishReview.getRating().equals("No rating yet")){
                sum += Double.parseDouble(dishReview.getRating());
                counter++;

            }
        }

        if(counter>0) {
            f = sum / counter;
            avg = Math.floor(sum / counter);
            reminder = f - avg;
            if (reminder < 0.25) {
                rating = Double.toString(avg);
            } else if (reminder >= 0.25 && reminder < 0.75) {
                rating = Double.toString(avg + 0.5);
            } else if (reminder >= 0.75) {
                rating = Double.toString(avg + 1);
            }
        }

        return rating;
    }

    public List<DishReview> getUserHighestRatingReviewsByUserId(String userId) {
        List<DishReview> result = new LinkedList<>();
        List<DishReview> dishReviews = getUserById(userId).getDishReviewList();
        for (DishReview dishReview : dishReviews) {
            if(Double.parseDouble(dishReview.getRating())>4.0){
                result.add(dishReview);
            }
        }
        return result;
    }



//    public Integer getNumOfFriendsVisitedInRestaurant(String restaurantID){
//        int count=0;
//        List<User> myFriends=getSignedUser().getFriendsList();
//        for (User friend: myFriends) {
//            for(Review rev: reviewList){
//                if(rev.restaurantId.equals(restaurantID) && friend.getReviewList().contains(rev)){
//                    count++;
//                }
//            }
//        }
//        return count;
//    }


//    public String getRandomFriendNameVisitedInRestaurant(String restaurantID){
//        List <User> myFriends=getSignedUser().getFriendsList();
//        Set <User> myFriendsWithReview=new HashSet<User>();
////        List <User> myFriendsWithReview=new LinkedList<>();
//        for(User friend:myFriends) {
//            for (Review rev : reviewList) {
//                if (rev.restaurantId.equals(restaurantID) && friend.getReviewList().contains(rev)) {
//                    myFriendsWithReview.add(friend);
//                }
//           }
//        }
//       return null;
//    }
    public void setStarByRating(String ratingVal, ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5, TextView rateTv){

        if(!ratingVal.equals("No rating yet")){
            rateTv.setVisibility(View.INVISIBLE);
            float rate =Float.parseFloat(ratingVal);
            if(rate==0.5){
                star1.setImageResource(R.drawable.ic_baseline_star_half_24);
                star2.setVisibility(View.INVISIBLE);
                star3.setVisibility(View.INVISIBLE);
                star4.setVisibility(View.INVISIBLE);
                star5.setVisibility(View.INVISIBLE);
            }
            else if(rate==1){
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setVisibility(View.INVISIBLE);
                star3.setVisibility(View.INVISIBLE);
                star4.setVisibility(View.INVISIBLE);
                star5.setVisibility(View.INVISIBLE);

            }
            else if(rate==1.5){
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_half_24);
                star3.setVisibility(View.INVISIBLE);
                star4.setVisibility(View.INVISIBLE);
                star5.setVisibility(View.INVISIBLE);
            }
            else if(rate==2){
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_24);
                star3.setVisibility(View.INVISIBLE);
                star4.setVisibility(View.INVISIBLE);
                star5.setVisibility(View.INVISIBLE);
            }
            else if(rate==2.5){
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_24);
                star3.setImageResource(R.drawable.ic_baseline_star_half_24);
                star4.setVisibility(View.INVISIBLE);
                star5.setVisibility(View.INVISIBLE);
            }
            else if(rate==3){
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_24);
                star3.setImageResource(R.drawable.ic_baseline_star_24);
                star4.setVisibility(View.INVISIBLE);
                star5.setVisibility(View.INVISIBLE);
            }
            else if(rate==3.5){
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_24);
                star3.setImageResource(R.drawable.ic_baseline_star_24);
                star4.setImageResource(R.drawable.ic_baseline_star_half_24);
                star5.setVisibility(View.INVISIBLE);
            }
            else if(rate==4){
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_24);
                star3.setImageResource(R.drawable.ic_baseline_star_24);
                star4.setImageResource(R.drawable.ic_baseline_star_24);
                star5.setVisibility(View.INVISIBLE);
            }
            else if(rate==4.5){
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_24);
                star3.setImageResource(R.drawable.ic_baseline_star_24);
                star4.setImageResource(R.drawable.ic_baseline_star_24);
                star5.setImageResource(R.drawable.ic_baseline_star_half_24);
            }
            else if(rate==5){
                star1.setImageResource(R.drawable.ic_baseline_star_24);
                star2.setImageResource(R.drawable.ic_baseline_star_24);
                star3.setImageResource(R.drawable.ic_baseline_star_24);
                star4.setImageResource(R.drawable.ic_baseline_star_24);
                star5.setImageResource(R.drawable.ic_baseline_star_24);
            }
        }
        else{
            star1.setVisibility(View.INVISIBLE);
            star2.setVisibility(View.INVISIBLE);
            star3.setVisibility(View.INVISIBLE);
            star4.setVisibility(View.INVISIBLE);
            star5.setVisibility(View.INVISIBLE);
        }
    }

    public List<Restaurant> searchRestaurantByName(String text) {
        List<Restaurant> result = new LinkedList<>();
        for (Restaurant res:restaurantList) {
            if(res.getName().contains(text)){
                result.add(res);
            }
        }
        return result;
    }

    public List<Restaurant> searchRestaurantByNameAndRestaurantList(String text, List<Restaurant> restaurantList) {
        List<Restaurant> result = new LinkedList<>();
        for (Restaurant res:restaurantList) {
            if(res.getName().contains(text)){
                result.add(res);
            }
        }
        return result;
    }

    public String getReviewRatingByDishIdAndUserId(String dishId, String userId) {
        String rating ="";
        User user = getUserById(userId);
        for (DishReview dishReview :user.getDishReviewList()) {
            if(dishReview.getDishId().equals(dishId)){
                rating = dishReview.getRating();
            }
        }

        return rating;
    }

    public List<User> getUsersFromListByEmail(List<User> userList, String email) {
        List<User> result = new LinkedList<>();
        for (User user : userList) {
            if (user.getEmail().contains(email)&& !user.getEmail().equals("No email address")) {
                result.add(user);
            }
        }
        return result;
    }

    public List<User> getUsersFromListByName(List<User> userList,String name){
        List<User> result = new LinkedList<>();
        for (User user :userList) {
            if(user.getFirstName().contains(name)){
                result.add(user);
            }
        }
        return result;
    }

    public List<User> getUsersFromListByNameAndEmail(List<User> userList,String name,String email){
        List<User> result = new LinkedList<>();
        for (User user :userList) {
            if(user.getFirstName().contains(name)&& user.getEmail().contains(email)){
                result.add(user);
            }
        }
        return result;
    }


    public void friendRequestCancel(User user2){
        user2.friendRequestDelete(signedUser);
    }
    public void friendRequestCreateRequestFromUser(User user){
        signedUser.friendRequestToConfirm(user);
    }
    public void friendRequestSendRequestToUser(User user2 ){
        user2.friendRequestToConfirm(signedUser);
    }

    public void friendRequestConfirmed(String userId) {
        signedUser.friendRequestConfirmed(getUserById(userId));
    }

    public void createFriendship(String userId) {
        User user2 = getUserById(userId);
        signedUser.friendRequestConfirmed(user2);
        user2.friendRequestConfirmed(signedUser);
    }

    public void cancelFriendsihp(User user2){
        signedUser.cancelFriendship(user2);
        user2.cancelFriendship(signedUser);
    }

    public void recoverFriendship(User user1, User user2) {
        user1.addFriend(user2);
        user2.addFriend(user1);
    }

    public void friendRequestIgnored(User user){
        signedUser.friendRequestIgnored(user);
    }

    public void friendRequestCancelIgnore(User user){
        signedUser.friendRequestCancelIgnore(user);
    }


    public void friendRequestUnConfirmed(User user) {
        signedUser.friendRequestUnConfirmed(user);
    }

    public int getNumOfReviewsFromFriendsOnRestaurant(String restaurantId) {
        int counter=0;
        List<User> friends = signedUser.getFriendsList();
        for (User user: friends) {
            List<DishReview> reviews = user.getDishReviewList();
            for (DishReview rev:reviews) {
                if(rev.getRestaurantId().equals(restaurantId)){
                    counter++;
                }
            }
        }
        return counter;
    }

    public List<User> getAllFriendssThatHaveReviewsOnRestaurantByRestaurantId(String restaurantId) {
        List<User> result = new LinkedList<>();
        List<User> friends = signedUser.getFriendsList();
        for(User user: friends){
            List<DishReview> reviews = user.getDishReviewList();
            for (DishReview rev:reviews) {
                if(rev.getRestaurantId().equals(restaurantId)){
                    result.add(user);
                    break;
                }
            }
        }
        return result;
    }

    public List<DishReview> getAllFriendsReviewsOnDishByDishIdAndUserId(String dishId, String userId) {
        User signedUser = getSignedUser();
        String signedUserId = signedUser.getId();
        Dish dish = getDishById(dishId);
        List<User> friends = signedUser.getFriendsList();
        List<DishReview> dishReviews =  new LinkedList<>();
        for(DishReview rev:dish.getReviewList()){
            for(User friend:friends){
                if(rev.getUserId().equals(friend.getId()) && !rev.getUserId().equals(userId) && !rev.getUserId().equals(signedUserId)){
                    dishReviews.add(rev);
                }
            }
        }
        return dishReviews;
    }
}
