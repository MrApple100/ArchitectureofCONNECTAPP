package com.example.architectureofconnectapp.Model.EntertoSocialNetwork;

public class RequestEntertoSocialNetwork {
    String accesstoken;
    String nameofSocialNetwork;
    String accesstokenofanothersn;

    public RequestEntertoSocialNetwork(String accesstoken, String nameofSocialNetwork, String accesstokenofanothersn) {
        this.accesstoken = accesstoken;
        this.nameofSocialNetwork = nameofSocialNetwork;
        this.accesstokenofanothersn = accesstokenofanothersn;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getNameofSocialNetwork() {
        return this.nameofSocialNetwork;
    }

    public void setNameofSocialNetwork(String nameofSocialNetwork) {
        this.nameofSocialNetwork = nameofSocialNetwork;
    }

    public String getAccesstokenofanothersn() {
        return accesstokenofanothersn;
    }

    public void setAccesstokenofanothersn(String accesstokenofanothersn) {
        this.accesstokenofanothersn = accesstokenofanothersn;
    }
}
