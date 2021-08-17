package com.example.architectureofconnectapp.Model;

import com.example.architectureofconnectapp.ConnectThings.ConnectUser;

public class Token {

    private Long id;
    private String networkname;
    private String token;
    private ConnectUser user;

    public Token(String networkname, String token) {
        this.networkname = networkname;
        this.token = token;
    }

    public void setUser(ConnectUser user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getNetworkname() {
        return networkname;
    }

    public String getToken() {
        return token;
    }

    public ConnectUser getUser() {
        return user;
    }
}
