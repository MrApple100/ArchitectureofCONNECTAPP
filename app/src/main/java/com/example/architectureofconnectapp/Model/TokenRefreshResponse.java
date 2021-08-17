package com.example.architectureofconnectapp.Model;

public class TokenRefreshResponse {
    public String token;
    public String refreshtoken;

    public TokenRefreshResponse(String token, String refreshtoken) {
        this.token=token;
        this.refreshtoken=refreshtoken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }
}
