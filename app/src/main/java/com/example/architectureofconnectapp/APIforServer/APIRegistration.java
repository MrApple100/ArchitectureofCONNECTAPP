package com.example.architectureofconnectapp.APIforServer;

import com.example.architectureofconnectapp.Model.AuthAndReg.DataUserReg;
import com.example.architectureofconnectapp.Model.AuthAndReg.RegResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface APIRegistration {

    @POST("/reqistrationcheck")
    Call<Map<String,String>> check(@HeaderMap Map<String,String> headers, @Body DataUserReg dataUserReg);

    @POST("/registration")
    Call<String> addUser(@HeaderMap Map<String,String> headers, @Body DataUserReg dataUserReg);

    @GET("/registration")
    Call<Boolean> addUser();
}
