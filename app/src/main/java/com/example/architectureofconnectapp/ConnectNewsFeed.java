package com.example.architectureofconnectapp;

import android.widget.Switch;

import java.util.List;

public class ConnectNewsFeed {
    private static ConnectNewsFeed connectNewsFeed;
    private int Count=10;
    private String next_from="0";
    private List<ConnectPost> posts;
    private ConnectNewsFeed(){ }
    public static ConnectNewsFeed getInstance(){
        if(connectNewsFeed==null){
            connectNewsFeed=new ConnectNewsFeed();
        }
        return connectNewsFeed;
    }
    public void update(){

    }

    public List<ConnectPost> getPosts() {
        return posts;
    }

    public void setPosts(IProcessNetRequest iProcessNetRequest) {

            posts = iProcessNetRequest.makenextrequest(Count,next_from);
            next_from+=Count;
    }
}
