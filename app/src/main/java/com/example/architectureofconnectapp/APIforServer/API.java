package com.example.architectureofconnectapp.APIforServer;

import com.example.architectureofconnectapp.Model.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Tag;

public interface API {

    @GET("/Users")
    Call<HashMap<Long,User>> getAll();

    @POST("/Users/{NetName}")
    Call<User> postUserInfo(@Path("NetName") Long NetName, @Body User UserInfo);

    @DELETE("/Users/{NetName}")
    Call<User> deleteUserInfo(@Path("NetName") Long NetName);

    @PUT("/Users/{NetName}")
    Call<User> updateUserInfo(@Path("NetName") Long NetName, @Body User UserInfo);
}
