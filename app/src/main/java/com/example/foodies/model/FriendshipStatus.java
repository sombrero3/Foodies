package com.example.foodies.model;

import java.util.Map;

public class FriendshipStatus {
    String user1Id;
    String user2Id;
    String status;
    boolean deleted = false;
    Long updateDate = new Long(0);

    public FriendshipStatus(){

    }
    public FriendshipStatus(String user1Id,String user2Id,String status){
        this.user1Id=user1Id;
        this.user2Id=user2Id;
        this.status=status;
    }

    public FriendshipStatus(String user1Id, String user2Id, String status, boolean deleted, Long updateDate) {
        this.user1Id=user1Id;
        this.user2Id=user2Id;
        this.status=status;
        this.deleted=deleted;
        this.updateDate=updateDate;
    }

    public static FriendshipStatus create(Map<String, Object> json) {
        FriendshipStatus fs;
        if(json!=null) {
            String user1Id = (String) json.get("user1Id");
            String user2Id = (String) json.get("user2Id");
            String status = (String) json.get("status");
            boolean deleted = (boolean) json.get("deleted");
            Long updateDate= (Long) json.get("updateDate");
            String url = (String) json.get("avatarUrl");
            fs = new FriendshipStatus(user1Id,user2Id,status,deleted,updateDate);
        }else{
            fs = new FriendshipStatus();
        }
        return fs;

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

    public String getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(String user1Id) {
        this.user1Id = user1Id;
    }

    public String getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(String user2Id) {
        this.user2Id = user2Id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
