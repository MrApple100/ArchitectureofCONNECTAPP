package com.example.architectureofconnectapp.Cash.CreateTables;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.architectureofconnectapp.Cash.Daos.DaoSocialNetwork;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.SocialNetwork;


@Database(entities = {SocialNetwork.class}, version = 2)
public abstract class TableSocialNetwork  extends RoomDatabase {
    private static Builder instance;
    public TableSocialNetwork() { }
    public static  Builder getInstance(Context context, String namedatabase){
        if(instance==null) {
            instance = Room.databaseBuilder(context, TableSocialNetwork.class, namedatabase);


        }
        return instance;
    }

    public abstract DaoSocialNetwork SocialNetworkDao();
}

