package com.example.architectureofconnectapp.Cash.CreateTables;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.architectureofconnectapp.Cash.Daos.DaoProfileConnectPost;
import com.example.architectureofconnectapp.Cash.Daos.DaoSocialNetwork;
import com.example.architectureofconnectapp.Model.CashConnectPost;
import com.example.architectureofconnectapp.Model.SocialNetwork;

@Database(entities = {CashConnectPost.class}, version = 2)
public abstract class TableProfileConnectPost extends RoomDatabase {
    private static Builder instance;
    public TableProfileConnectPost() { }
    public static  Builder getInstance(Context context, String namedatabase){
        if(instance==null) {
            instance = Room.databaseBuilder(context, TableProfileConnectPost.class, namedatabase);
        }
        return instance;
    }

    public abstract DaoProfileConnectPost ProfileConnectPostDao();
}
