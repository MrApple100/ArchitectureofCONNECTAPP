package com.example.architectureofconnectapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.architectureofconnectapp.ConnectThings.ConnectUser;
import com.google.gson.Gson;

import java.util.Map;
@Entity
public class CashConnectUser {
    @PrimaryKey
    private Long id;
    private String Principal;
    private String connecttoken;
    private String connectrefreshtoken;
    private String email;

    public CashConnectUser(ConnectUser user) {
        this.id=user.getId();
        this.Principal=user.getPrincipal();
        this.email=user.getEmail();
    }

    public CashConnectUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getConnecttoken() {
        return this.connecttoken;
    }

    public void setConnecttoken(Map<String, Object> tkn) {
        Gson gson=new Gson();
        this.connecttoken=gson.toJson(tkn);
    }

    public void setConnecttoken(String connecttoken) {
        this.connecttoken = connecttoken;
    }

    public String getConnectrefreshtoken() {
        return connectrefreshtoken;
    }

    public void setConnectrefreshtoken(String connectrefreshtoken) {
        this.connectrefreshtoken = connectrefreshtoken;
    }

    public String getPrincipal() {
        return Principal;
    }

    public void setPrincipal(String principal) {
        Principal = principal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
