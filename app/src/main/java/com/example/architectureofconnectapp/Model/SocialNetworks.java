package com.example.architectureofconnectapp.Model;

import java.util.ArrayList;
import java.util.HashMap;
//соцсети которыми пользуется пользователь тут!!!
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
    public void addSocialNetwork(String networkname, String token) {

        SocialNetworks.add(new SocialNetwork(networkname,token));
    }
    public void deleteAllSocialNetworks(){
        SocialNetworks.clear();
    }
    public void deleteSocialNetworkByNameofNetwork(String NameofNetwork){
        SocialNetworks.removeIf(sn ->{if(sn.getNameofNetwork().equals(NameofNetwork)){return true;}else{return false;}});
    }
}
