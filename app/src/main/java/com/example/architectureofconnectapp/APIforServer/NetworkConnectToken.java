package com.example.architectureofconnectapp.APIforServer;

import android.os.Handler;
import android.util.Log;

import com.example.architectureofconnectapp.Model.TokenRefreshResponse;
import com.example.architectureofconnectapp.Model.User;
import com.example.architectureofconnectapp.Model.Users;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkConnectToken {
    Retrofit retrofit;
    APItoken api;
    public NetworkConnectToken() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.155:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(APItoken.class);
    }

    public TokenRefreshResponse updatetokens(String rftkn){
        Response response=null;
        try {
            System.out.println(rftkn);
            response = api.gettokens(SpringBootClient.getInstance().getheaders(),rftkn).execute();

        } catch (IOException ioException) {
            System.out.println(ioException.getStackTrace());
            return null;
        }
        return (TokenRefreshResponse) response.body();
    }
}
