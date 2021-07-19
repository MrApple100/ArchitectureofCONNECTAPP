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
import com.example.architectureofconnectapp.Cash.CreateTables.TableSocialNetwork;
import com.example.architectureofconnectapp.Cash.Daos.DaoSocialNetwork;
import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.ConstModel.ConstNetwork;
import com.example.architectureofconnectapp.ConstModel.ConstNetworks;
import com.example.architectureofconnectapp.Fragments.FragmentConnectNews;
import com.example.architectureofconnectapp.Fragments.FragmentConnectNewsfeed;
import com.example.architectureofconnectapp.Fragments.FragmentNavigationPanel;
import com.example.architectureofconnectapp.Fragments.FragmentNewConnectPost;
import com.example.architectureofconnectapp.Fragments.authandreg.Fragmententerlp;
import com.example.architectureofconnectapp.Fragments.authandreg.Fragmententernw;
import com.example.architectureofconnectapp.Twitter.TwitterEnter;
import com.example.architectureofconnectapp.Twitter.TwitterProcessRequest;
import com.example.architectureofconnectapp.Twitter.TwittergetFromJson;
import com.example.architectureofconnectapp.VK.VKEnter;
import com.example.architectureofconnectapp.VK.VKInterection;
import com.example.architectureofconnectapp.VK.VKProcessRequest;
import com.example.architectureofconnectapp.VK.VKgetFromJson;
import com.google.android.material.navigation.NavigationView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import twitter4j.TwitterException;

//MainActivity используется как главная активность для отображения и взаимодействия
//синглтон
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
        //проводим инициализацию или проверку подключения с соцсетями
        /*
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                new VKEnter().Enter(MainActivity.getActivity());
                new TwitterEnter().Enter(MainActivity.getActivity());

                //запуск первичного фрагмента и обязательно нижней навигации
                FragmentConnectNews ConnectNews = FragmentConnectNews.getInstance();
                FragmentNavigationPanel NavigationPanel = FragmentNavigationPanel.getInstance();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.MainScene,ConnectNews);
                ft.commit();
                ft = fm.beginTransaction();
                ft.add(R.id.Nav_footer,NavigationPanel);
                ft.commit();
            }
        };
        //Получаю с сервера информацию о хранящихся Соцсетях и пользователях в них
        new Network().getAll(handler);



         */
        //массив всех соц сетей которые есть в приложении
        ConstNetworks.getInstance().addConstNetwork((long)"VK".hashCode(),new ConstNetwork("VK").setEnter(new VKEnter())
                .setInterection(new VKInterection())
                .setNetFromJson(new VKgetFromJson())
                .setProcessNetRequest(new VKProcessRequest()));
        ConstNetworks.getInstance().addConstNetwork((long)"Twitter".hashCode(),new ConstNetwork("Twitter").setEnter(new TwitterEnter())
                .setInterection(null)
                .setNetFromJson(new TwittergetFromJson())
                .setProcessNetRequest(new TwitterProcessRequest()));

        Fragmententerlp fragmententerlp= Fragmententerlp.getInstance();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.MainScene,fragmententerlp);
        ft.commit();


    }
    //после автозахода по ВК переход осуществляется сюда
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                FragmentConnectNews.getInstance().onActivityResult( requestCode, resultCode, data);
            }
            @Override
            public void onError(VKError error) {
                FragmentConnectNews.getInstance().onActivityResult( requestCode, resultCode, data);
            }
        }))


        if(requestCode==FragmentNewConnectPost.getInstance().getRequest_Code()) {
            if (resultCode == MainActivity.RESULT_OK) {
                FragmentNewConnectPost.getInstance().onActivityResult( requestCode, resultCode, data);
            }
        }

    }

}
