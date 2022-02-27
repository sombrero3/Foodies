package com.example.foodies.model;

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
