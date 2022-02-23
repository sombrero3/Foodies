package com.example.foodies.model;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.foodies.R;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Model {

    List<User> userList = new LinkedList<>();
    List<Restaurant> restaurantList = new ArrayList<>();
    List<Dish> dishList = new LinkedList<>();
    List<DishReview> dishReviewList = new LinkedList<>();
    List<Review> generalReviewList = new LinkedList<>();
    List<FriendshipStatus> friendshipStatuses = new LinkedList<>();
    List<User> signedUserFriends = new LinkedList<>();
    User signedUser;
    MutableLiveData<UsersListLoadingState> usersListLoadingState = new MutableLiveData<>();
    ModelFireBase modelFireBase = new ModelFireBase();
    boolean signedFlag;

    public static final Model instance = new Model();
    public Executor executor = Executors.newFixedThreadPool(1);
    public Handler mainThread = HandlerCompat.createAsync(Looper.getMainLooper());

    public void signUp(String email, String password,String firstName,String lastName,ProgressBar progressBar) {
        modelFireBase.signIn(email,password,firstName,lastName,progressBar);
    }

    public User getSignedUser() {
        return signedUser;
    }


    public interface GetAllUsersListener{
        void onComplete(List<User> users);
    }
    public void getAllUsers(GetAllUsersListener listener) {
        modelFireBase.getAllUsers(new GetAllUsersListener() {
            @Override
            public void onComplete(List<User> users) {
                userList.clear();
                userList.addAll(users);
                listener.onComplete(userList);
            }
        });
    }

    public interface setCurrentUserListener {
        void onComplete(User user);
    }
    public void setCurrentUser(setCurrentUserListener listener) {
            modelFireBase.setCurrentUser(new setCurrentUserListener() {
                @Override
                public void onComplete(User user) {
                    signedUser = user;
                    listener.onComplete(user);
                }
            });
    }

    public enum UsersListLoadingState{
        loading,
        loaded
    }



    private Model() {
        signedFlag = false;

        for(int i=0;i<10;i++){
            restaurantList.add(new Restaurant("name "+i));
        }
        usersListLoadingState.setValue(UsersListLoadingState.loaded);

//        for(int i=0;i<10;i++){
//            User user = new User("name "+i, "" + i ,"email"+i+"@gmail.com");
//            userList.add(user);
//        }
//
//        friendshipStatuses.add(new FriendshipStatus("0","1","friends"));
//        friendshipStatuses.add(new FriendshipStatus("0","3","friends"));
//        friendshipStatuses.add(new FriendshipStatus("0","5","friends"));
//        friendshipStatuses.add(new FriendshipStatus("0","7","friends"));
//        friendshipStatuses.add(new FriendshipStatus("1","8","friends"));
//        friendshipStatuses.add(new FriendshipStatus("1","3","friends"));
//        friendshipStatuses.add(new FriendshipStatus("1","4","friends"));
//        friendshipStatuses.add(new FriendshipStatus("1","6","friends"));
//        friendshipStatuses.add(new FriendshipStatus("2","3","friends"));
//        friendshipStatuses.add(new FriendshipStatus("2","5","friends"));
//        friendshipStatuses.add(new FriendshipStatus("2","9","friends"));
//        friendshipStatuses.add(new FriendshipStatus("2","4","friends"));
//        friendshipStatuses.add(new FriendshipStatus("3","5","friends"));
//        friendshipStatuses.add(new FriendshipStatus("3","7","friends"));
//        friendshipStatuses.add(new FriendshipStatus("3","8","friends"));
//        friendshipStatuses.add(new FriendshipStatus("3","6","friends"));
//        setSignedUser(userList.get(0));
//        setSignedFlag(true);
//        friendshipStatuses.add(new FriendshipStatus("0","8","pending"));
//        friendshipStatuses.add(new FriendshipStatus("0","9","pending"));
//
//        Random random = new Random();
//        for(int i=0;i<10;i++){
//            Restaurant res = new Restaurant("Restaurant name "+i);
//            restaurantList.add(res);
//            for(int j=0;j<10;j++){
//                Dish dish = new Dish("Dish name "+i + " " + j);
//                dish.setRestaurantId(res.getId());
//                addDish(dish);
//                for(int k=0;k<10;k++){
//                        String rating  = Integer.toString(Math.abs((random.nextInt()%5))+1);
//                        DishReview dishReview = new DishReview(dish.getId(), res.getId(),userList.get(k).getId(),rating);
//                        dish.setPrice(Integer.toString(Math.abs(random.nextInt()%500))+"$");
//                        addDishReview(dishReview);
//                }
//            }
//        }
    }

    //-------Getters and Setters-------//

    public List<DishReview> getDishReviewList() {
        return dishReviewList;
    }

    public void setDishReviewList(List<DishReview> dishReviewList) {
        this.dishReviewList = dishReviewList;
    }

    public List<Review> getGeneralReviewList() {
        return generalReviewList;
    }

    public void setGeneralReviewList(List<Review> generalReviewList) {
        this.generalReviewList = generalReviewList;
    }

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




    public boolean isSignedFlag() {
        return signedFlag;
    }
    public void setSignedFlag(boolean signedFlag) {

        this.signedFlag = signedFlag;
    }
    //---------------------------------//

    /**
     * Authentication
     *
     */
    public boolean isSignedIn() {
        return modelFireBase.isSignedIn();
    }

    public void dishUpdateRating(String dishId){

        String rating ="No rating yet";
        double f ,reminder,sum=0,avg,counter=0;
        List<DishReview> list = getAllReviewsOnADish(dishId);
        for(DishReview dishReview : list){
            if(dishReview.getDishId().equals(dishId) && !dishReview.getRating().equals("No rating yet")) {
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
        }else{
            rating = "No rating yet";
        }
        getDishById(dishId).setRating(rating);
    }
    public void restaurantUpdateRating(String restaurantId){
        String rating = "No rating yet";
        double f ,reminder,sum=0,avg,counter=0;

        for(Dish dish:dishList){
            if(dish.getRestaurantId().equals(restaurantId) && !dish.getRating().equals("No rating yet")) {
                sum += Double.parseDouble(dish.getRating());
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
        }else{
            rating = "No rating yet";
        }
        getRestaurantById(restaurantId).setRating(rating);
    }
//    public boolean confirmUserLogin(String name,String password){
//        for (User user:userList) {
//            if(user.getFirstName().equals(name) && user.getPassword().equals(password)){
//                setSignedFlag(true);
//                setSignedUser(user);
//                return true;
//            }
//        }
//        return false;
//    }
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

    public interface getUserByIdListener{
        void onComplete(User user);
    }
    public void getUserById(String id,getUserByIdListener listener){
        modelFireBase.getUserById(id,listener);

    }
    public User getUserByIdOld(String id){
        for(int i=0;i< userList.size();i++){
            if(userList.get(i).getId().equals(id)){
                return userList.get(i);
            }
        }
        return new User();
    }


    public boolean ifUserHasReviewOnThatRestaurant(String userId,String restaurantId){
        boolean res = false;
        for(DishReview dr:dishReviewList) {
            if (dr.getRestaurantId().equals(restaurantId) && dr.getUserId().equals(userId)){
                res=true;
                break;
            }
        }
        if(!res){
            for(Review rev:generalReviewList){
                if(rev.getUserId().equals(userId) && rev.getRestaurantId().equals(restaurantId)){
                    res=true;
                    break;
                }
            }
        }
        return res;
    }

    public void addDishReview(DishReview dishReview){
        if(!ifUserHasReviewOnThatRestaurant(dishReview.getUserId(),dishReview.getRestaurantId())){
            getUserByIdOld(dishReview.getUserId()).increaseTotalRestaurantsVisited();
        }
        dishReviewList.add(dishReview);
        getUserByIdOld(dishReview.getUserId()).increaseTotalReviews();
        dishUpdateRating(dishReview.getDishId());
        restaurantUpdateRating(dishReview.getRestaurantId());

    }
    public void addDish(Dish dish){
      dishList.add(dish);
    }
    public void addRestaurant(Restaurant restaurant){
        restaurantList.add(restaurant);
    }

    public interface AddUserListener{
        void onComplete();
    }
    public void addUser(User user,AddUserListener listener) throws JsonProcessingException {
        usersListLoadingState.setValue(UsersListLoadingState.loading);
        modelFireBase.addUser(user, new AddUserListener() {
            @Override
            public void onComplete() {
                //refreshStudentList();
                listener.onComplete();
            }
        });
    }

    public void deleteReview(DishReview dishReview){
       // getUserById(dishReview.getUserId()).deleteReview(dishReview);       // remove the review from the user's review list
        User user = getSignedUser();
        dishReviewList.remove(dishReview);
        int newTotal = Integer.valueOf(user.getTotalReviews())-1;
        getSignedUser().setTotalReviews(Integer.toString(newTotal));
        String restaurant = dishReview.getRestaurantId();
        boolean flag = false;
        for(int i = 0; i< dishReviewList.size(); i++){
            if(dishReviewList.get(i).getRestaurantId().equals(restaurant) && user.getId().equals(dishReview.getUserId())){
                flag = true;
                break;
            }
        }
        if(!flag){
            newTotal = Integer.valueOf(user.getTotalRestaurantsVisited())-1;
            getSignedUser().setTotalRestaurantsVisited(Integer.toString(newTotal));
        }

        dishReviewList.remove(dishReview);
        dishUpdateRating(dishReview.getDishId());
        restaurantUpdateRating(dishReview.getRestaurantId());
    }
//    public void deleteDish(Dish dish){
//        int size = dish.getReviewList().size();
//        for(int i=0;i<size;i++){                   // remove the reviews on this dish from each users review list
//            String user = dish.getReviewList().get(i).getUserId();
//            int size2 = userList.size();
//            for(int j=0; j<size2;j++){
//                if(userList.get(j).getId().equals(user)){
//                    userList.get(j).deleteReview(dish.getReviewList().get(i));
//                    break;
//                }
//            }
//        }
//        size = restaurantList.size();
//        for(int i=0;i<size;i++){                  // remove the dish from the restautant's dish list
//            String restaurant = dish.getRestaurantId();
//            if(restaurantList.get(i).getId().equals(restaurant)){
//                restaurantList.get(i).deleteDish(dish);
//                break;
//            }
//        }
//        dishList.remove(dish);
//    }
//    public void deleteRestaurant(Restaurant restaurant){
//        int size = restaurant.getDishList().size();
//        for(int i=0;i<size;i++){                  // for each restaurant's dish
//            int size2 = restaurant.getDishList().get(i).getReviewList().size();
//            for(int j=0;j<size2;j++) {            // remove all dish's reviews from their user's review list than delete review
//                int size3 = userList.size();
//                for (int k = 0; k < size3;k++) {
//                    if(restaurant.getDishList().get(i).getReviewList().get(j).userId.equals(userList.get(k).getId())){
//                        userList.get(k).deleteReview(restaurant.getDishList().get(i).getReviewList().get(j));
//                        dishReviewList.remove(restaurant.getDishList().get(i).getReviewList().get(j));
//                    }
//                }
//            }
//            dishList.remove(restaurant.getDishList().get(i));  // remove dishs
//        }
//        restaurantList.remove(restaurant); // remove restaurant
//    }
//    public void deleteUser(User user){
//        int size = user.getFriendsList().size(); // remove user from all of his friend's users list
//        for(int i=0;i<size;i++){
//            user.getFriendsList().get(i).getFriendsList().remove(user);
//        }
//        size = user.getDishReviewList().size();  // remove all user's reviews from their dishes
//        for(int i=0;i<size;i++){
//            int size2 =dishList.size();
//            String dish = user.getDishReviewList().get(i).getDishId();
//            for(int j=0;j<size2;j++) {
//                if(dishList.get(j).getId().equals(dish)){
//                    dishList.get(j).deleteReview(user.getDishReviewList().get(i));
//                }
//            }
//        }
//        userList.remove(user); // delete from Model user list
//    }

    public List<User> getAllUsersThatHaveReviewsOnRestaurantByRestaurantId(String restaurantId){
        List<User> result = new LinkedList<>();
        for(int i = 0; i< dishReviewList.size(); i++){
            if(dishReviewList.get(i).getRestaurantId().equals(restaurantId) && !result.contains(getUserByIdOld(dishReviewList.get(i).getUserId()))){
                result.add(getUserByIdOld(dishReviewList.get(i).getUserId()));

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
        List<DishReview> dishReviews = Model.instance.getUserDishReviewsList(userId);

        for(DishReview dishReview : dishReviews){
            if(dishReview.getUserId().equals(userId) && dishReview.getRestaurantId().equals(restaurantId) && !result.contains(getDishById(dishReview.getDishId()))){
                result.add(getDishById(dishReview.getDishId()));
            }
        }
        return result;
    }

    private List<DishReview> getUserDishReviewsList(String userId) {
        List<DishReview> result = new LinkedList<>();
        for(DishReview dr:dishReviewList){
            if(dr.getUserId().equals(userId)){
                result.add(dr);
            }
        }
        return result;
    }

    public DishReview getReviewOnDishByDishIdAndUserId(String dishId, String userId){
        List<DishReview> dishReviews = Model.instance.getUserDishReviewsList(userId);
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
        List<Dish> dishl = getAlldisheListOfRestaurant(resId);
        for(int i=0;i<dishl.size();i++){
            if(dishl.get(i).getName().equals(dishName)){
                return dishl.get(i).getId();
            }
        }
        return "No Such Dish";
    }

    private List<Dish> getAlldisheListOfRestaurant(String resId) {
        List<Dish> dishes= new LinkedList<>();
        for(Dish dish:dishList){
            if(dish.getRestaurantId().equals(resId)){
                dishes.add(dish);
            }
        }
        return dishes;
    }
    public boolean areFriends(String user1Id,String user2Id){
        for(FriendshipStatus fs:friendshipStatuses){
            if(((fs.getUser1Id().equals(user1Id) && fs.getUser2Id().equals(user2Id))
                || (fs.getUser1Id().equals(user2Id) && fs.getUser2Id().equals(user1Id)))
                    && fs.getStatus().equals("friends")
                        && fs.isDeleted()==false){
                return true;
            }
        }
        return false;
    }



    public interface GetFriendListListener{
       void  onComplete(List<User> friends);
    }
    public void getFriendsList(String user1Id,GetFriendListListener listener){
        modelFireBase.getFriendsList(user1Id, new GetFriendListListener() {
            @Override
            public void onComplete(List<User> friends) {
                listener.onComplete(friends);
            }
        });


//        List<User> res = new LinkedList<>();
//        for(User user:userList) {
//            if (!user1Id.equals(user.getId()) && areFriends(user1Id,user.getId()) && !res.contains(user)){
//                res.add(user);
//            }
//        }
//        return res;
    }
    public List<DishReview> getAllFriendsReviewsOnDishByDishId(String dishId){
        User signedUser = getSignedUser();
        String signedUserId = signedUser.getId();
        Dish dish = getDishById(dishId);
        //List<User> friends = getFriendsList(signedUserId);
        List<User> friends = new LinkedList<>();
        List<DishReview> reviews =  new LinkedList<>();
        List<DishReview> dishReviewsList =getAllReviewsOnADish(dishId);
        for(DishReview rev:dishReviewsList){
            for(User friend:friends){
                if(rev.getUserId().equals(friend.getId()) && !rev.getUserId().equals(signedUserId)){
                    reviews.add(rev);
                }
            }
        }
        return reviews;
    }
    public List<DishReview> getAllReviewsOnADish(String dishId){
        List<DishReview> result = new LinkedList<>();
        for (DishReview dr:dishReviewList) {
            if(dr.getDishId().equals(dishId)){
                result.add(dr);
            }
        }
        return result;
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
        //List<User> friends = getFriendsList(signedUser.getId());
        List<User> friends = new LinkedList<>();
        for (User user :userList) {
            if(!friends.contains(user) && user.getFirstName().contains(name) && !signedUser.getId().equals(user.getId())){
                result.add(user);
            }
        }
        return result;
    }

    public List<User> getNotFriendsUsersByEmail(String email) {
        List<User> result = new LinkedList<>();
        //List<User> friends = getFriendsList(signedUser.getId());
        List<User> friends = new LinkedList<>();
        for (User user : userList) {
            if (!friends.contains(user) && user.getEmail().contains(email)&& !user.getEmail().equals("No email address")) {
                result.add(user);
            }
        }
        return result;
    }
    public List<User> getNotFriendsUsersByNameAndEmail(String name,String email){
        List<User> result = new LinkedList<>();
        //List<User> friends = getFriendsList(signedUser.getId());
        List<User> friends = new LinkedList<>();
        for (User user :userList) {
            if(!friends.contains(user) && user.getFirstName().contains(name)&& user.getEmail().contains(email)){
                result.add(user);
            }
        }
        return result;
    }
    public List<User> peopleYouMayKnow(){
        List<User> signedUserFriends , result ,friendfriendsList;
        result = new LinkedList<>();
        //signedUserFriends = getFriendsList(signedUser.getId());
        signedUserFriends = new LinkedList<>();
        for (User friend: signedUserFriends) {
            //friendfriendsList = getFriendsList(friend.getId());
            friendfriendsList = new LinkedList<>();
            for (User friendfriends:friendfriendsList) {
                if(!result.contains(friendfriends) && !signedUserFriends.contains(friendfriends) && !friendfriends.getId().equals(signedUser.getId())) {
                    result.add(friendfriends);
                }
            }
        }
        return result;
    }

    public String getRestaurantRatingGivenByAUser(User user,String restaurantId){
        List<DishReview> dishReviews = getUserDishReviewsList(user.getId());
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
        List<DishReview> dishReviews = getUserDishReviewsList(userId);
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
        User user = getUserByIdOld(userId);
        List<DishReview> list = getUserDishReviewsList(userId);
        for (DishReview dishReview :list) {
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

    public interface GetFriendsRequestsListener {
        void onComplete(List<User> fr);
    }
    public void getFriendsRequests(String userId,GetFriendsRequestsListener listener){
        modelFireBase.getFriendsRequests(userId,listener);
    }
    public void friendRequestCancel(User user2){
        String signedUserId = signedUser.getId();
        String userId = user2.getId();
        for(FriendshipStatus fs:friendshipStatuses){
            if(fs.getUser1Id().equals(userId) && fs.getUser2Id().equals(signedUserId) && fs.getStatus().equals("pending")){
                fs.setDeleted(true);
            }
        }
    }
    public interface AddFriendRequestListener{
        void onComplete(FriendshipStatus fruendshipStatus);
    }
    public void friendRequestSendRequestToUser(User user2 ) throws JsonProcessingException {
            modelFireBase.addFriendRequest(user2.getId(),new AddFriendRequestListener() {
                @Override
                public void onComplete(FriendshipStatus friendshipStatus) {
                    //friendshipStatuses.add(friendshipStatus);
                }
            });
    }


    public interface FriendRequestConfirmedListener{
        void onComplete();
    }
    public void friendRequestConfirmed(String userId,FriendRequestConfirmedListener listener) throws JsonProcessingException {
        modelFireBase.friendRequestConfirmed(userId, new FriendRequestConfirmedListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    public interface FriendRequestUnConfirmed{
        void onComplete();
    }
    public void friendRequestUnConfirmed(User user,FriendRequestUnConfirmed listener) throws JsonProcessingException {
        modelFireBase.friendRequestUnConfirmed(user,listener);
    }
    public interface CancelFriendshipListener{
        void onComplete();
    }
    public void cancelFriendsihp(User user2,CancelFriendshipListener listener){
        modelFireBase.cancelFriendship(user2.getId(),listener);


//        for(FriendshipStatus fs:friendshipStatuses){
//            if(((fs.getUser1Id().equals(user1Id) && fs.getUser2Id().equals(user2Id))
//                    || (fs.getUser1Id().equals(user1Id) && fs.getUser2Id().equals(user2Id)))
//                    && fs.getStatus().equals("friends")
//                    && fs.isDeleted()==false){
//                fs.setDeleted(true);
//                break;
//            }
//        }
    }
    public void recoverFriendship(User user2) {
        String user1Id = signedUser.getId();
        String user2Id = user2.getId();
        for(FriendshipStatus fs:friendshipStatuses){
            if(((fs.getUser1Id().equals(user1Id) && fs.getUser2Id().equals(user2Id))
                    || (fs.getUser1Id().equals(user1Id) && fs.getUser2Id().equals(user2Id)))
                    && fs.getStatus().equals("friends")
                    && fs.isDeleted()==true){
                fs.setDeleted(false);
                break;
            }
        }
    }
    public void friendRequestIgnored(User user){
        String signedUserId = signedUser.getId();
        String userId = user.getId();
        for(FriendshipStatus fs:friendshipStatuses){
            if(fs.getUser1Id().equals(signedUserId) && fs.getUser2Id().equals(userId) && fs.getStatus().equals("pending")){
                fs.setStatus("ignored");
                break;
            }
        }
    }
    public void friendRequestCancelIgnore(User user){
        String signedUserId = signedUser.getId();
        String userId = user.getId();
        for(FriendshipStatus fs:friendshipStatuses){
            if(fs.getUser1Id().equals(signedUserId) && fs.getUser2Id().equals(userId) && fs.getStatus().equals("ignored")){
                fs.setStatus("pending");
                break;
            }
        }
    }


    public int getNumOfReviewsFromFriendsOnRestaurant(String restaurantId) {
        int counter=0;
        //List<User> friends = getFriendsList(signedUser.getId());
        List<User> friends = new LinkedList<>();
        for (User user: friends) {
            List<DishReview> reviews = getAllReviewsOnADish(user.getId());
            for (DishReview rev:reviews) {
                if(rev.getRestaurantId().equals(restaurantId)){
                    counter++;
                }
            }
        }
        return counter;
    }

    public Review getUserGeneralReview(String userId, String restaurantId) {
        for (Review rev: generalReviewList) {
            if(rev.getUserId().equals(userId) && rev.getRestaurantId().equals(restaurantId)){
                return rev;
            }
        }
        return new Review();
    }

    public List<User> getAllFriendsThatHaveReviewsOnRestaurantByRestaurantId(String restaurantId) {
        List<User> result = new LinkedList<>();
        //List<User> friends = getFriendsList(signedUser.getId());
        List<User> friends = new LinkedList<>();
        for(User user: friends){
            List<DishReview> reviews = getUserDishReviewsList(user.getId());
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
        //List<User> friends = getFriendsList(signedUser.getId());
        List<User> friends = new LinkedList<>();
        List<DishReview> dishReviews =  new LinkedList<>();
        List<DishReview> list = getAllReviewsOnADish(dishId);
        for(DishReview rev:list){
            for(User friend:friends){
                if(rev.getUserId().equals(friend.getId()) && !rev.getUserId().equals(userId) && !rev.getUserId().equals(signedUserId)){
                    dishReviews.add(rev);
                }
            }
        }
        return dishReviews;
    }


    public int getNumOfFriendVisitedRestaurant(String restaurantId){
        int res = 0;
        if(isSignedFlag()) {
            User user = getSignedUser();
            List<User> friends = new LinkedList<>();
          //  List<User> list = getFriendsList(user.getId());
            List<User> list = new LinkedList<>();
            for (User u : list) {
                friends.add(u);
            }
            for (DishReview rev : dishReviewList) {
                if (rev.getRestaurantId().equals(restaurantId) && friends.contains(getUserByIdOld(rev.getUserId()))) {
                    friends.remove(getUserByIdOld(rev.getUserId()));
                    res++;
                }
            }
        }
        return res;
    }
    public String getAFriendNameWhoVisitedARestaurant(String restaurantId){
        String res = "";
        if(isSignedFlag()) {
            User user = getSignedUser();
            List<User> friends = new LinkedList<>();
            //List<User> list = getFriendsList(user.getId());
            List<User> list = new LinkedList<>();
            for (User u : list) {
                friends.add(u);
            }
            for (DishReview rev : dishReviewList) {
                if (rev.getRestaurantId().equals(restaurantId) && friends.contains(getUserByIdOld(rev.getUserId()))) {
                    res = getUserByIdOld(rev.getUserId()).getFirstName();
                }
            }
        }
        return res;
    }

}
