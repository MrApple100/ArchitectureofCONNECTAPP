package com.example.architectureofconnectapp.Cash.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.architectureofconnectapp.Model.SocialNetwork;
import com.example.architectureofconnectapp.Model.User;

import java.util.List;

@Dao
public interface DaoUser {
    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("SELECT *FROM User WHERE id = :id")
    User getByid(long id);

    @Insert
    void insert(User user);
    @Update
    void update(User user);
    @Delete
    void delete(User user);
}