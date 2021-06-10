package com.example.architectureofconnectapp.Cash.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.architectureofconnectapp.Model.SocialNetwork;

import java.util.List;

@Dao
public interface DaoSocialNetwork {
    @Query("SELECT * FROM SocialNetwork")
    List<SocialNetwork> getAll();

    @Query("SELECT *FROM SocialNetwork WHERE id = :id")
    SocialNetwork getByid(int id);

    @Insert
    void insert(SocialNetwork socialNetwork);
    @Update
    void update(SocialNetwork socialNetwork);
    @Delete
    void delete(SocialNetwork socialNetwork);
}