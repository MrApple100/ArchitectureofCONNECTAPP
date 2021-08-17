package com.example.architectureofconnectapp.Cash.Daos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.architectureofconnectapp.ConnectThings.ConnectUser;
import com.example.architectureofconnectapp.Model.CashConnectUser;
import com.example.architectureofconnectapp.Model.User;

import java.util.List;

@Dao
public interface DaoConnectUser {
    @Query("SELECT * FROM CashConnectUser")
    List<CashConnectUser> getAll();

    @Query("SELECT *FROM CashConnectUser WHERE id = :id")
    CashConnectUser getByid(long id);

    @Insert
    void insert(CashConnectUser user);
    @Update
    void update(CashConnectUser user);
    @Delete
    void delete(CashConnectUser user);

}
