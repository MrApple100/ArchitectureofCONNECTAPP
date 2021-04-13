package com.example.architectureofconnectapp;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import com.example.architectureofconnectapp.Fragments.FragmentConnectNewsfeed;
import com.example.architectureofconnectapp.VK.VKEnter;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;


public class MainActivity extends FragmentActivity {
    static Context instance;
    static MainActivity activity;

    public static Context getInstance() {
        return instance;
    }

    public static MainActivity getActivity() {
        return activity;
    }

    Fragment ConnectNewsFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
        activity=MainActivity.this;

        new VKEnter().Enter(this);

        ConnectNewsFeed = new FragmentConnectNewsfeed();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.MainScene,ConnectNewsFeed);
        ft.commit();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                ConnectNewsFeed.onActivityResult( requestCode, resultCode, data);
            }
            @Override
            public void onError(VKError error) {
                ConnectNewsFeed.onActivityResult( requestCode, resultCode, data);
            }
        }))
            super.onActivityResult(requestCode, resultCode, data);

    }
}