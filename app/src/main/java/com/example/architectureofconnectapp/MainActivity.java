package com.example.architectureofconnectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.concurrent.Executors;

import javax.sql.DataSource;

public class MainActivity extends AppCompatActivity {
    static Context instance;
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static Context getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;

        RecyclerView NewsFeed=(RecyclerView) findViewById(R.id.NewsFeed);

        ConnectNewsFeed connectNewsFeed = ConnectNewsFeed.getInstance();
        VKProcessRequest vkProcessRequest=new VKProcessRequest();
        MySourceFactory mySourceFactory= new MySourceFactory(connectNewsFeed,vkProcessRequest);
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .build();
        LiveData<PagedList<ConnectPost>> pagedListData =new LivePagedListBuilder<>(mySourceFactory,config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build();
        DiffUtilCallback diffutilcalback=new DiffUtilCallback();
        AdapterConnectNewsFeed adapter=new AdapterConnectNewsFeed(diffutilcalback);
        pagedListData.observe(this, new Observer<PagedList<ConnectPost>>() {
            @Override
            public void onChanged(PagedList<ConnectPost> connectPosts) {
                adapter.submitList(connectPosts);
            }
        });

    }
}