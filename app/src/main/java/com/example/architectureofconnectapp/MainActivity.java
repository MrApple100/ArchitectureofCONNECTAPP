package com.example.architectureofconnectapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.example.architectureofconnectapp.APIforServer.Network;
import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.Fragments.FragmentConnectNewsfeed;
import com.example.architectureofconnectapp.Fragments.FragmentNavigationPanel;
import com.example.architectureofconnectapp.Fragments.FragmentNewConnectPost;
import com.example.architectureofconnectapp.Twitter.TwitterEnter;
import com.example.architectureofconnectapp.VK.VKEnter;
import com.google.android.material.navigation.NavigationView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import twitter4j.TwitterException;


public class MainActivity extends FragmentActivity {
    static Context instance;
    static MainActivity activity;
    Handler handler;

    public static Context getInstance() {
        return instance;
    }

    public static MainActivity getActivity() {
        return activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
        activity=MainActivity.this;

        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                new VKEnter().Enter(MainActivity.getActivity());
                new TwitterEnter().Enter(MainActivity.getActivity());
            }
        };
        new Network().getAll(handler);

        FragmentConnectNewsfeed ConnectNewsFeed = FragmentConnectNewsfeed.getInstance();
        FragmentNavigationPanel NavigationPanel = FragmentNavigationPanel.getInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.MainScene,ConnectNewsFeed);
        ft.commit();
        ft = fm.beginTransaction();
        ft.add(R.id.Nav_footer,NavigationPanel);
        ft.commit();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                FragmentConnectNewsfeed.getInstance().onActivityResult( requestCode, resultCode, data);
            }
            @Override
            public void onError(VKError error) {
                FragmentConnectNewsfeed.getInstance().onActivityResult( requestCode, resultCode, data);
            }
        }))


        if(requestCode==FragmentNewConnectPost.getInstance().getRequest_Code()) {
            if (resultCode == MainActivity.RESULT_OK) {
                FragmentNewConnectPost.getInstance().onActivityResult( requestCode, resultCode, data);
            }
        }

    }

}
