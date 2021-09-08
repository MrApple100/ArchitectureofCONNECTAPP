package com.example.architectureofconnectapp.APIforServer;

import android.os.Handler;
import android.util.Log;

import com.example.architectureofconnectapp.Model.User;
import com.example.architectureofconnectapp.Model.Users;
import com.google.gson.Gson;

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
                .baseUrl("https://connectappserver.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(API.class);
    }

    public void getAll(Handler handler) {
        api.getAll().enqueue(new Callback<HashMap<Long, User>>() {
            @Override
            public void onResponse(Call<HashMap<Long,User>> call, Response<HashMap<Long,User>> response) {
                Gson gson=new Gson();
                Users.getInstance().setUsersofNet((HashMap<Long, User>) response.body());
                Log.d("TTT",response.body()+"");
                if(Users.getInstance().getUsersofNet().size()!=0)
                    Log.d("TTTG",Users.getInstance().getUsersofNet().get((long)"VK".hashCode()).getLast_name()+" "+"Twitter".hashCode());
                if(handler!=null) {
                    handler.sendEmptyMessage(1);
                }
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
                Log.d("TTT3",response.body()+"");
                getAll(null);
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
                getAll(null);
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
                getAll(null);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
