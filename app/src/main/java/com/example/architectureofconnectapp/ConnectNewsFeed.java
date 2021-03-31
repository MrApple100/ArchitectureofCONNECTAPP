package com.example.architectureofconnectapp;

import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class ConnectNewsFeed {
    private static ConnectNewsFeed connectNewsFeed;
    private int Count=10;
    private String next_from="0";
    private List<ConnectPost> posts=new ArrayList<>();
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

    public void setCount(int count) {
        Count = count;
    }
    public void setPosts(IProcessNetRequest iProcessNetRequest) {
            posts = iProcessNetRequest.makenextrequest(Count,next_from);

            //posts.addAll(gettedposts);
            next_from=iProcessNetRequest.sentNext_from();
    }
}
