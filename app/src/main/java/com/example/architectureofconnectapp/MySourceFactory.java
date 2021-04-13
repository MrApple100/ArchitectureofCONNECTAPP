package com.example.architectureofconnectapp;

import androidx.paging.DataSource;

import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;

public class MySourceFactory extends DataSource.Factory<Integer, ConnectPost> {
    ConnectNewsFeed connectNewsFeed;
    IProcessNetRequest iProcessNetRequest;
    public MySourceFactory(ConnectNewsFeed connectNewsFeed, IProcessNetRequest iProcessNetRequest){
        this.connectNewsFeed=connectNewsFeed;
        this.iProcessNetRequest=iProcessNetRequest;
    }

    @Override
    public DataSource<Integer, ConnectPost> create() {
        return new MyPositionalDataSource(connectNewsFeed,iProcessNetRequest);
    }
}
