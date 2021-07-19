package com.example.architectureofconnectapp.Fragments.authandreg;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.architectureofconnectapp.APIforServer.SpringBootClient;
import com.example.architectureofconnectapp.APIforServer.SpringIdentity;
import com.google.gson.Gson;

public class ServiceAuthetication extends Service {
    public static final String PERMISSION="AUTH";
    public static final String IDENTITY="IDENTITY";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String login = intent.getStringExtra("LOGIN");
        String password = intent.getStringExtra("PASSWORD");
        MyThread myThread=new MyThread(login,password);
        myThread.start();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    class MyThread extends Thread{
        String login,password;
        public MyThread(String login,String password) {
            this.login=login;
            this.password=password;
            SpringIdentity identity= new SpringBootClient().login(login,password);
            Intent intent=new Intent(PERMISSION);
            Gson gson=new Gson();
            intent.putExtra(IDENTITY,gson.toJson(identity));
            sendBroadcast(intent);
        }

        @Override
        public void run() {
            super.run();

        }
    }
}

