package com.example.architectureofconnectapp;

import android.graphics.Bitmap;

import com.example.architectureofconnectapp.ConnectThings.ConnectPost;

import java.util.ArrayList;

import twitter4j.TwitterException;

public interface IProcessNetRequest {
    //обработка запросов из feeds
    ArrayList<ConnectPost> makenextrequest(int count, String next_from,String method);
    //чтобы узнать номер поста с которого продолжать загрузку
    String sentNext_from();
    //отправка поста в соцсеть
    void SentPost(String text, ArrayList<Bitmap> bitmaps);
}
