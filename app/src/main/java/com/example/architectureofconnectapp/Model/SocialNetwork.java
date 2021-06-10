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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SocialNetwork(String NameofNetwork) {
        this.NameofNetwork = NameofNetwork;
        this.id=NameofNetwork.hashCode();
    }

    public String getNameofNetwork() {
        return NameofNetwork;
    }


}
