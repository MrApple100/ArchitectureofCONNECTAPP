package com.example.architectureofconnectapp;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;

import java.util.List;

public class MyPositionalDataSource extends PositionalDataSource<ConnectPost> {

    private final ConnectNewsFeed connectNewsFeed;
    private final IProcessNetRequest iProcessNetRequest;

    public MyPositionalDataSource(ConnectNewsFeed connectNewsFeed,IProcessNetRequest iProcessNetRequest) {
        this.connectNewsFeed = connectNewsFeed;
        this.iProcessNetRequest = iProcessNetRequest;
    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<ConnectPost> callback) {
        connectNewsFeed.setCount(params.requestedLoadSize);
        connectNewsFeed.setPosts(iProcessNetRequest);
        List<ConnectPost> connectPosts=connectNewsFeed.getPosts();
        System.out.println("loadRange, startPosition = " + params.requestedStartPosition + ", loadSize = " + params.requestedLoadSize);
        callback.onResult(connectPosts,params.requestedStartPosition);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<ConnectPost> callback) {
        connectNewsFeed.setCount(params.loadSize);
        connectNewsFeed.setPosts(iProcessNetRequest);
        List<ConnectPost> connectPosts=connectNewsFeed.getPosts();
        System.out.println("loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        callback.onResult(connectPosts);
    }
}
