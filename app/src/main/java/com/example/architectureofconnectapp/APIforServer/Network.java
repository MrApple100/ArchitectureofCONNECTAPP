package com.example.architectureofconnectapp.APIforServer;

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
        api.getAll().enqueue(new Callback<Map<String, JSONObject>>() {
            @Override
            public void onResponse(Call<Map<String,JSONObject>> call, Response<Map<String,JSONObject>> response) {
                Users.getInstance().setUsersofNet((HashMap<String, JSONObject>) response.body());
            }

            @Override
            public void onFailure(Call<Map<String,JSONObject>> call, Throwable t) {

            }
        });
    }
    public JSONObject getUserInfo(String NetName) {
        final JSONObject[] json = new JSONObject[1];
        api.getUserInfo(NetName).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                json[0] =response.body();
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
        return json[0];
    }


    public void postUserInfo(String NetName, JSONObject UserInfo) {
        api.postUserInfo(NetName,UserInfo).enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                getAll();
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });

    }

    public boolean deleteUserInfo(String NetName) {
        final Boolean[] resbool = new Boolean[1];
        api.deleteUserInfo(NetName).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                resbool[0] = response.body();
                getAll();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        return resbool[0];
    }


    public boolean updateUserInfo(String NetName, JSONObject UserInfo) {
        final Boolean[] resbool = new Boolean[1];
        api.updateUserInfo(NetName,UserInfo).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                resbool[0] = response.body();
                getAll();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        return resbool[0];
    }
}
