package com.example.architectureofconnectapp;

import android.graphics.Bitmap;

import com.example.architectureofconnectapp.ConnectThings.ConnectPost;

import java.util.ArrayList;

import twitter4j.TwitterException;

public interface IProcessNetRequest {
    ArrayList<ConnectPost> makenextrequest(int count, String next_from,String method);
    String sentNext_from();
    void SentPost(String text, ArrayList<Bitmap> bitmaps);
}
