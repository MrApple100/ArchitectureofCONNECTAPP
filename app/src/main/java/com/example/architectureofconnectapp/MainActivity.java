package com.example.architectureofconnectapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.util.VKUtil;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

public class MainActivity extends AppCompatActivity {
    static Context instance;
    static MainActivity activity;
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public static Context getInstance() {
        return instance;
    }

    public static MainActivity getActivity() {
        return activity;
    }

    RecyclerView NewsFeed;
    LiveData<PagedList<ConnectPost>> pagedListData;
    AdapterConnectNewsFeed adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
        activity=MainActivity.this;

        new VKEnter().Enter(this);

        NewsFeed=(RecyclerView) findViewById(R.id.NewsFeed);



    }
    class MyNewsFeedTask extends AsyncTask<Void,Void,Void>  {
        LiveData<PagedList<ConnectPost>> pagedListData;
        AdapterConnectNewsFeed adapter;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            ConnectNewsFeed connectNewsFeed = ConnectNewsFeed.getInstance();
            VKProcessRequest vkProcessRequest=new VKProcessRequest();
            MySourceFactory mySourceFactory= new MySourceFactory(connectNewsFeed,vkProcessRequest);
            PagedList.Config config = new PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setPageSize(10)
                    .build();
            pagedListData =new LivePagedListBuilder<>(mySourceFactory,config)
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build();
            DiffUtilCallback diffutilcalback=new DiffUtilCallback();
            adapter = new AdapterConnectNewsFeed(diffutilcalback);
            return null;
        }
        @Override
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);
            pagedListData.observe(MainActivity.getActivity(), new Observer<PagedList<ConnectPost>>() {
                @Override
                public void onChanged(PagedList<ConnectPost> connectPosts) {
                    adapter.submitList(connectPosts);
                }
            });
            NewsFeed.setAdapter(adapter);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                ConnectNewsFeed connectNewsFeed = ConnectNewsFeed.getInstance();
                VKProcessRequest vkProcessRequest=new VKProcessRequest();
                MySourceFactory mySourceFactory = new MySourceFactory(connectNewsFeed,vkProcessRequest);
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(4)
                        .setPrefetchDistance(2)
                        .setPageSize(5)
                        .build();
                pagedListData =new LivePagedListBuilder<>(mySourceFactory,config)
                        .setFetchExecutor(Executors.newSingleThreadExecutor())
                        .build();
                DiffUtilCallback diffutilcalback=new DiffUtilCallback();
                adapter = new AdapterConnectNewsFeed(diffutilcalback);

                pagedListData.observe(MainActivity.getActivity(), new Observer<PagedList<ConnectPost>>() {
                    @Override
                    public void onChanged(@Nullable PagedList<ConnectPost> connectPosts) {
                        System.out.println("onChanged");
                        adapter.submitList(connectPosts);
                    }
                });
                NewsFeed.setAdapter(adapter);
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(),"hiError",Toast.LENGTH_LONG).show();
            }
        }))
            super.onActivityResult(requestCode, resultCode, data);

    }
}