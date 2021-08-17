package com.example.architectureofconnectapp.APIforServer;

import com.example.architectureofconnectapp.Model.EntertoSocialNetwork.RequestEntertoSocialNetwork;
import com.example.architectureofconnectapp.Model.User;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APItoEnterSocialNetwork {


        @POST("/EnterToSocialNetwork")
        Call<SpringIdentity> Enter(@HeaderMap Map<String,String> headers, @Body RequestEntertoSocialNetwork requestEntertoSocialNetwork);


}
