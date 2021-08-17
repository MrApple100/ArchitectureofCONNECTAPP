package com.example.architectureofconnectapp.Cash.CreateTables;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.architectureofconnectapp.Cash.Daos.DaoConnectUser;
import com.example.architectureofconnectapp.ConnectThings.ConnectUser;
import com.example.architectureofconnectapp.Model.CashConnectPost;
import com.example.architectureofconnectapp.Model.CashConnectUser;

@Database(entities = {CashConnectUser.class}, version = 1)
public abstract class TableConnectUser  extends RoomDatabase {
    private static Builder instance;
    public TableConnectUser() { }
    public static  Builder getInstance(Context context, String namedatabase){
        if(instance==null) {
            instance = Room.databaseBuilder(context, TableConnectUser.class, namedatabase);

        }
        return instance;
    }

    public abstract DaoConnectUser ConnectUserDao();
}

