package com.example.architectureofconnectapp.Model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Users {
    static Users instance;
    private HashMap<String,JSONObject> UsersofNet=new HashMap<>();
    public static Users getInstance(){
        if(instance==null){
            instance=new Users();
        }
        return  instance;
    }
    private Users() {}

    public HashMap<String,JSONObject> getUsersofNet() {
        return UsersofNet;
    }

    public void setUsersofNet(HashMap<String,JSONObject> usersofNet) {
        UsersofNet = usersofNet;
    }
}
