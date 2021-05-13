package com.example.architectureofconnectapp.APIforServer;

import android.util.Log;

import com.example.architectureofconnectapp.Model.User;
import com.example.architectureofconnectapp.Model.Users;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    Retrofit retrofit;
    API api;
    public Network() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.155:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(API.class);
    }

    public void getAll() {
        api.getAll().enqueue(new Callback<HashMap<Long, User>>() {
            @Override
            public void onResponse(Call<HashMap<Long,User>> call, Response<HashMap<Long,User>> response) {
                Users.getInstance().setUsersofNet((HashMap<Long, User>) response.body());
                Log.d("TTT",response.body()+"");
                Log.d("TTT",Users.getInstance().getUsersofNet().get((long)"VK".hashCode()).toString());
            }

            @Override
            public void onFailure(Call<HashMap<Long,User>> call, Throwable t) {

            }
        });
    }



    public void postUserInfo(Long NetName, User UserInfo) {
        api.postUserInfo(NetName,UserInfo).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("TTT",response.body()+"");
                getAll();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public void deleteUserInfo(Long NetName) {
        api.deleteUserInfo(NetName).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("HashMap",response.body()+"");
                System.out.println("HHMMMMMMMMMAP: "+response.body()+"");
                getAll();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    public void updateUserInfo(Long NetName, User UserInfo) {

        api.updateUserInfo(NetName,UserInfo).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                getAll();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
