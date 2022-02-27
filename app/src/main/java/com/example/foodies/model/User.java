package com.example.foodies.model;

import android.media.Image;

import java.util.LinkedList;
import java.util.List;

public class User {
    String id;
    String firstName;
    String lastName;
    String totalReviews;
    String email;
    String password;
    String imageUrl;
    boolean deleted = false;
    Long updateDate = new Long(0);
    String totalRestaurantsVisited;
    //List<User> friendsList,friendRequestList,ignoredList,confirmedList;


    //-------Constructors-------//
    public User(){
        id = Integer.toString(IdGenerator.instance.getNextId());
        firstName = "";
        lastName = "";
        email ="No email address";
        totalReviews ="0";
        password = "";
        totalRestaurantsVisited = "0";
//        friendsList = new LinkedList<>();
//        friendRequestList = new LinkedList<>();
//        ignoredList = new LinkedList<>();
//        confirmedList = new LinkedList<>();
    }
    public User(String firstName,String password){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.firstName = firstName;
        this.lastName = password;
        email ="No email address";
        totalReviews ="0";
        this.password = password;
        totalRestaurantsVisited = "0";
//        friendsList = new LinkedList<>();
//        friendRequestList = new LinkedList<>();
//        ignoredList = new LinkedList<>();
//        confirmedList = new LinkedList<>();
    }
    public User(String firstName,String password,String email){
        id = Integer.toString(IdGenerator.instance.getNextId());
        this.firstName = firstName;
        this.lastName = password;
        this.email = email;
        totalReviews ="0";
        this.password = password;
//        friendsList = new LinkedList<>();
//        friendRequestList = new LinkedList<>();
//        ignoredList = new LinkedList<>();
//        confirmedList = new LinkedList<>();
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
    //    public void setFriendsList(List<User> friendsList) {
//        this.friendsList = friendsList;
//    }
    //    public List<User> getFriendRequestList() {
//        return friendRequestList;
//    }
//    public void setFriendRequestList(List<User> friendRequestList) {
//        this.friendRequestList = friendRequestList;
//    }
//    public List<User> getIgnoredList() {
//        return ignoredList;
//    }
//    public void setIgnoredList(List<User> ignoredList) {
//        this.ignoredList = ignoredList;
//    }
//    public List<User> getFriendsList() {
//        updateFriendLists();
//        return friendsList;
//    }
//    public List<User> getConfirmedList() {
//        return confirmedList;
//    }
//    public void setConfirmedList(List<User> confirmedList) {
//        this.confirmedList = confirmedList;
//    }
    //---------------------------------//

    public void increaseTotalRestaurantsVisited() {
        int num = Integer.parseInt((totalRestaurantsVisited))+1;
        totalRestaurantsVisited = Integer.toString(num);
    }

    public void increaseTotalReviews() {
        int num = Integer.parseInt((totalReviews))+1;
        totalReviews = Integer.toString(num);
    }
//    public void addFriend(User friend){
//        if(!friendsList.contains(friend)&& !friend.getId().equals(id)) {
//            friendsList.add(friend);
//        }
//    }
//    public void deleteFriend(User friend){
//        friendsList.remove(friend);
//    }
//    public void friendRequestToConfirm(User user){
//        if(!friendRequestList.contains(user)) {
//            friendRequestList.add(user);
//        }
//    }
//    public void friendRequestConfirmed(User user){
//        if(!confirmedList.contains(user)){confirmedList.add(user);}
//    }
//    public void cancelFriendship(User user){
//        deleteFriend(user);
//    }
//    public void friendRequestDelete(User user){
//        friendRequestList.remove(user);
//    }
//    public void friendRequestIgnored(User user){if(!ignoredList.contains(user)){ignoredList.add(user);} }
//    public void friendRequestCancelIgnore(User user){ignoredList.remove(user); }
//    public void friendRequestUnConfirmed(User user){confirmedList.remove(user); }
//    public void updateFriendLists() {
//        for (User user:confirmedList) {
//            if(!friendsList.contains(user)) {
//                friendsList.add(user);
//            }
//            friendRequestList.remove(user);
//            confirmedList.remove(user);
//        }
//        for (User user:ignoredList) {
//            friendRequestList.remove(user);
//            ignoredList.remove(user);
//        }
//        for (User user:friendsList) {
//            if(user.id.equals(id)){
//                friendsList.remove(user);
//            }
//        }
//
//    }
}
