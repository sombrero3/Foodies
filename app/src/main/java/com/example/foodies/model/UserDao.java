package com.example.foodies.model;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("select * from User")
    List<User> getAll();
    @Query("select * from User where  email =:userEmail LIMIT 1")
    User getUserById(String userEmail);
    @Insert(onConflict = REPLACE)
    void insertAll(User... users);
    @Delete
    void delete(User user);
}
