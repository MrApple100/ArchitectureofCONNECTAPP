package com.example.architectureofconnectapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.architectureofconnectapp.IInterection;
import com.example.architectureofconnectapp.INetFromJson;
import com.example.architectureofconnectapp.IProcessNetRequest;
@Entity
public class SocialNetwork {
    @PrimaryKey
    private int id;
    private String NameofNetwork;
    private String token;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SocialNetwork(String NameofNetwork, String token) {
        this.NameofNetwork = NameofNetwork;
        this.id=NameofNetwork.hashCode();
        this.token=token;
    }

    public String getNameofNetwork() {
        return NameofNetwork;
    }

    public void setNameofNetwork(String nameofNetwork) {
        NameofNetwork = nameofNetwork;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
