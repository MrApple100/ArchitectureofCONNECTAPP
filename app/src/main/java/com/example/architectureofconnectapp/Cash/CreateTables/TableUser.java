package com.example.architectureofconnectapp.Cash.CreateTables;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.architectureofconnectapp.Cash.Daos.DaoSocialNetwork;
import com.example.architectureofconnectapp.Cash.Daos.DaoUser;
import com.example.architectureofconnectapp.Model.SocialNetwork;
import com.example.architectureofconnectapp.Model.User;

@Database(entities = {User.class}, version = 2)
public abstract class TableUser  extends RoomDatabase {
    private static Builder instance;
    public TableUser() { }
    public static  Builder getInstance(Context context, String namedatabase){
        if(instance==null) {
            instance = Room.databaseBuilder(context, TableUser.class, namedatabase);


        }
        return instance;
    }

    public abstract DaoUser UserDao();
}
