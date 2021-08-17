package com.example.architectureofconnectapp.APIforServer;

import com.example.architectureofconnectapp.ConnectThings.ConnectUser;
import com.example.architectureofconnectapp.Model.User;

import java.util.HashMap;
import java.util.Map;

public class SpringIdentity {
    private Map<String, Object> tokenInfo = new HashMap<>();
    private ConnectUser user;
    private String jwt;
    private String refreshToken;



    public SpringIdentity() {

    }

    public ConnectUser getUser() {
        return user;
    }

    public void setUser(ConnectUser user) {
        this.user = user;
    }


    public Map<String, Object> getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(Map<String, Object> tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public String getRefreshtoken() {
        return refreshToken;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshToken = refreshtoken;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}