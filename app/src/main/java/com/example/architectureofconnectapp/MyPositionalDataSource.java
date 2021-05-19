package com.example.architectureofconnectapp;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;

import java.util.ArrayList;
import java.util.List;

public class MyPositionalDataSource extends PositionalDataSource<ConnectPost> {

    private final ConnectNewsFeed connectNewsFeed;
    private final ArrayList<IProcessNetRequest> iProcessNetRequests;

    public MyPositionalDataSource(ConnectNewsFeed connectNewsFeed, ArrayList<IProcessNetRequest> iProcessNetRequests) {
        this.connectNewsFeed = connectNewsFeed;
        this.iProcessNetRequests = iProcessNetRequests;
    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<ConnectPost> callback) {
        connectNewsFeed.setCount(params.requestedLoadSize);
        connectNewsFeed.setPosts(iProcessNetRequests);
        List<ConnectPost> connectPosts=connectNewsFeed.getPosts();
        System.out.println("loadRange, startPosition = " + params.requestedStartPosition + ", loadSize = " + params.requestedLoadSize);
        callback.onResult(connectPosts,params.requestedStartPosition);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<ConnectPost> callback) {
        connectNewsFeed.setCount(params.loadSize);
        connectNewsFeed.setPosts(iProcessNetRequests);
        List<ConnectPost> connectPosts=connectNewsFeed.getPosts();
        System.out.println("loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        callback.onResult(connectPosts);
    }
}
