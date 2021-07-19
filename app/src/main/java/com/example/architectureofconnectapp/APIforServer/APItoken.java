package com.example.architectureofconnectapp.APIforServer;

import com.example.architectureofconnectapp.Model.AuthAndReg.DataUserReg;
import com.example.architectureofconnectapp.Model.AuthAndReg.RegResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface APItoken {
    @GET("/erp/login-api-json")
    Call<Map<String,String>> getheaders(@HeaderMap Map<String,String> headers);

    @POST("/postlogin-api-json")
    Call<SpringIdentity> getSpringIdentity(@HeaderMap Map<String,String> headers, @Body DataUserReg dataUserReg);


}
