package com.example.architectureofconnectapp.ConnectThings;

import android.graphics.Bitmap;

import com.example.architectureofconnectapp.IProcessNetRequest;

import java.util.ArrayList;

public class ConnectPageCreatePost {
    String text;
    ArrayList<Bitmap> bitmaps;

    public ConnectPageCreatePost(String text, ArrayList<Bitmap> bitmaps) {
        this.text = text;
        this.bitmaps = bitmaps;
    }
    public void SentPost(IProcessNetRequest iProcessNetRequest){
        iProcessNetRequest.SentPost(text,bitmaps);
    }
}
