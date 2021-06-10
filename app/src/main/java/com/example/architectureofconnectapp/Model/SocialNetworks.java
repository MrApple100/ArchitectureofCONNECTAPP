package com.example.architectureofconnectapp.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class SocialNetworks {
    static SocialNetworks instance;
    private ArrayList<SocialNetwork> SocialNetworks=new ArrayList<>();
    public static SocialNetworks getInstance(){
        if(instance==null){
            instance=new SocialNetworks();
        }
        return  instance;
    }
    private SocialNetworks() {}

    public ArrayList<SocialNetwork> getSocialNetworks() {

        return SocialNetworks;
    }

    public void setSocialNetworks(ArrayList<SocialNetwork> usersofNet) {

        SocialNetworks = usersofNet;
    }
    public void addSocialNetwork(SocialNetwork socialNetwork) {

        SocialNetworks.add(socialNetwork);
    }
}