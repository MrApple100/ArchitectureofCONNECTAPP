package com.example.architectureofconnectapp.Cash.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.architectureofconnectapp.Model.CashConnectPost;
import com.example.architectureofconnectapp.Model.SocialNetwork;

import java.util.List;

@Dao
public interface DaoProfileConnectPost {
    @Query("SELECT * FROM CashConnectPost WHERE network=:network")
    List<CashConnectPost> getAll(int network);

    @Query("SELECT * FROM CashConnectPost WHERE id = :id")
    CashConnectPost getByid(long id);

    @Query("SELECT * FROM CashConnectPost WHERE network=:network AND type = :type")
    List<CashConnectPost> getBynetworkandtype(int network,int type);

    @Insert
    void insert(CashConnectPost cashConnectPost);
    @Update
    void update(CashConnectPost cashConnectPost);
    @Delete
    void delete(CashConnectPost cashConnectPost);

    @Query("DELETE FROM CashConnectPost")
    void deleteall();
}
