package com.example.architectureofconnectapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class User {
    @PrimaryKey
    Long id;
    String first_name;
    String last_name;
    String token;
    String secrettoken;


    public User(String first_name, Long id, String last_name) {
        this.first_name = first_name;
        this.id = id;
        this.last_name = last_name;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public String getSecrettoken() {
        return secrettoken;
    }

    public void setSecrettoken(String secrettoken) {
        this.secrettoken = secrettoken;
    }
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        last_name = last_name;
    }
}