package com.example.architectureofconnectapp.APIforServer;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {

    @GET("User")
    Call<Map<String,JSONObject>> getAll();

    @GET("/User/{NetName}")
    Call<JSONObject> getUserInfo(@Path("NetName") String NetName);

    @POST("/User/{NetName}")
    Call<JSONObject> postUserInfo(@Path("NetName") String NetName,@Body JSONObject UserInfo);

    @DELETE("/User/{NetName}")
    Call<Boolean> deleteUserInfo(@Path("NetName") String NetName);

    @PUT("/User/{NetName}")
    Call<Boolean> updateUserInfo(@Path("NetName") String NetName, @Body JSONObject UserInfo);
}
