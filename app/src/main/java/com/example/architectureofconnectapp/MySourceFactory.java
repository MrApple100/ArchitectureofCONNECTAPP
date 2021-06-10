package com.example.architectureofconnectapp;

import androidx.paging.DataSource;

import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;

import java.util.ArrayList;

public class MySourceFactory extends DataSource.Factory<Integer, ConnectPost> {
    ConnectFeeds connectNewsFeed;
    ArrayList<IProcessNetRequest> iProcessNetRequests;
    public MySourceFactory(ConnectFeeds connectNewsFeed, ArrayList<IProcessNetRequest> iProcessNetRequests){
        this.connectNewsFeed=connectNewsFeed;
        this.iProcessNetRequests=iProcessNetRequests;
    }

    @Override
    public DataSource<Integer, ConnectPost> create() {
        return new MyPositionalDataSource(connectNewsFeed,iProcessNetRequests);
    }
}
