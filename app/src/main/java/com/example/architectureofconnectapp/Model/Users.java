package com.example.architectureofconnectapp.Model;

import android.util.Log;

import com.example.architectureofconnectapp.APIforServer.Network;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Users {
    static Users instance;
    private HashMap<Long,User> UsersofNet=new HashMap<>();
    public static Users getInstance(){
        if(instance==null){
            instance=new Users();
        }
        return  instance;
    }
    private Users() {}

    public HashMap<Long,User> getUsersofNet() {

        return UsersofNet;
    }

    public void setUsersofNet(HashMap<Long,User> usersofNet) {

        UsersofNet = usersofNet;
    }
}
