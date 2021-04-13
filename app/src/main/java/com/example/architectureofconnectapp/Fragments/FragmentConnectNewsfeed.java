package com.example.architectureofconnectapp.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureofconnectapp.ConnectThings.AdapterConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;
import com.example.architectureofconnectapp.DiffUtilCallback;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.MySourceFactory;
import com.example.architectureofconnectapp.R;
import com.example.architectureofconnectapp.VK.VKProcessRequest;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.concurrent.Executors;

public class FragmentConnectNewsfeed extends Fragment {

    RecyclerView NewsFeed;
    LiveData<PagedList<ConnectPost>> pagedListData;
    AdapterConnectNewsFeed adapter;

    public FragmentConnectNewsfeed() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.connectnewsfeed,container,false);
        NewsFeed=(RecyclerView) view.findViewById(R.id.NewsFeed);
        return view;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                ConnectNewsFeed connectNewsFeed = ConnectNewsFeed.getInstance();
                VKProcessRequest vkProcessRequest=new VKProcessRequest();
                MySourceFactory mySourceFactory = new MySourceFactory(connectNewsFeed,vkProcessRequest);
                PagedList.Config config = new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setPrefetchDistance(5)
                        .setPageSize(10)
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
                Toast.makeText(MainActivity.getInstance(),"hiError",Toast.LENGTH_LONG).show();
            }
        }))
            super.onActivityResult(requestCode, resultCode, data);

    }
}
