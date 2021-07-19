package com.example.architectureofconnectapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;

import java.util.ArrayList;
import java.util.List;

public class MyPositionalDataSource extends PositionalDataSource<ConnectPost> {

    private final ConnectFeeds connectNewsFeed;
    private final ArrayList<IProcessNetRequest> iProcessNetRequests;

    public MyPositionalDataSource(ConnectFeeds connectNewsFeed, ArrayList<IProcessNetRequest> iProcessNetRequests) {
        this.connectNewsFeed = connectNewsFeed;
        this.iProcessNetRequests = iProcessNetRequests;
    }

    //первая загрузка
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<ConnectPost> callback) {
        connectNewsFeed.setCount(params.requestedLoadSize);
        connectNewsFeed.setPosts(iProcessNetRequests);
        List<ConnectPost> connectPosts=connectNewsFeed.getPosts();
        System.out.println("HUH-"+ connectPosts.size());
        System.out.println("loadRange, startPosition = " + params.requestedStartPosition + ", loadSize = " + connectPosts.size());
        Log.e("sentNext","loadRange, startPosition = " + params.requestedStartPosition + ", loadSize = " + connectPosts.size());
        callback.onResult(connectPosts,params.requestedStartPosition);
    }
    //последующая загрузка
    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<ConnectPost> callback) {
        connectNewsFeed.setCount(params.loadSize);
        connectNewsFeed.setPosts(iProcessNetRequests);
        List<ConnectPost> connectPosts=connectNewsFeed.getPosts();
        System.out.println("HUH2-"+connectPosts.size());
        System.out.println("loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        Log.e("sentNext","loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);
        callback.onResult(connectPosts);
    }
}
