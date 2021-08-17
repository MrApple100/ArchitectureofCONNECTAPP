package com.example.architectureofconnectapp.APIforServer;

import com.example.architectureofconnectapp.Model.AuthAndReg.DataUserReg;
import com.example.architectureofconnectapp.Model.TokenRefreshResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface APItoken {
    @GET("/api/auth/headerscsrf")
    Call<Map<String,String>> getheaders(@HeaderMap Map<String,String> headers);

    @POST("/api/auth/signin")
    Call<SpringIdentity> getSpringIdentity(@HeaderMap Map<String,String> headers, @Body DataUserReg dataUserReg);

    @POST("/api/auth/signintoken")
    Call<SpringIdentity> getSpringIdentitybytoken(@HeaderMap Map<String,String> headers, @Body String tkn);

    @POST("/api/auth/refreshtoken")
    Call<TokenRefreshResponse> gettokens(@HeaderMap Map<String,String> headers, @Body String rftkn);


}
