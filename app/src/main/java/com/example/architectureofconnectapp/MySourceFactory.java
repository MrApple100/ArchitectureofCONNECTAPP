package com.example.architectureofconnectapp;

import androidx.paging.DataSource;

public class MySourceFactory extends DataSource.Factory<Integer, ConnectPost> {
    ConnectNewsFeed connectNewsFeed;
    IProcessNetRequest iProcessNetRequest;
    MySourceFactory(ConnectNewsFeed connectNewsFeed,IProcessNetRequest iProcessNetRequest){
        this.connectNewsFeed=connectNewsFeed;
        this.iProcessNetRequest=iProcessNetRequest;
    }

    @Override
    public DataSource<Integer, ConnectPost> create() {
        return new MyPositionalDataSource(connectNewsFeed,iProcessNetRequest);
    }
}
