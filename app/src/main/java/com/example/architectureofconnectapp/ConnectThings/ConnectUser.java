package com.example.architectureofconnectapp.ConnectThings;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.architectureofconnectapp.Model.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConnectUser {

    private Long id;
    private String Principal;
    private String tag;
    private String email;
    private boolean active;

    private ArrayList<Token> tokens=new ArrayList<>();
    private Set<ConnectRole> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<ConnectRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<ConnectRole> roles) {
        this.roles = roles;
    }
    public void addNetworkToken(String networkname, String token){
        Token tkn = new Token(networkname,token);
        tkn.setUser(this);
        tokens.add(tkn);
    }
    public void  removeNetworkToken(String networkname){
        for (Token tkn:tokens) {
            if(tkn.getNetworkname()==networkname){
                tokens.remove(tkn);
            }
        }
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }
}
