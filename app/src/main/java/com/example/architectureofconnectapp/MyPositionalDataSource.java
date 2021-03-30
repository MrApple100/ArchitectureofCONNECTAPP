package com.example.architectureofconnectapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PositionalDataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MyPositionalDataSource extends PositionalDataSource<ConnectPost> {

    private final ConnectNewsFeed connectNewsFeed;
    private final IProcessNetRequest iProcessNetRequest;

    public MyPositionalDataSource(ConnectNewsFeed connectNewsFeed,IProcessNetRequest iProcessNetRequest) {
        this.connectNewsFeed = connectNewsFeed;
        this.iProcessNetRequest = iProcessNetRequest;
    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<ConnectPost> callback) {
        connectNewsFeed.setPosts(iProcessNetRequest);
        List<ConnectPost> connectPosts=connectNewsFeed.getPosts();
        callback.onResult(connectPosts,params.requestedStartPosition);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<ConnectPost> callback) {
        connectNewsFeed.setPosts(iProcessNetRequest);
        List<ConnectPost> connectPosts=connectNewsFeed.getPosts();
        callback.onResult(connectPosts);
    }
}
