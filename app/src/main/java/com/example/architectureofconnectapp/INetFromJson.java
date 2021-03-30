package com.example.architectureofconnectapp;

import android.graphics.Bitmap;
import android.provider.MediaStore;

import org.json.JSONObject;

import java.util.ArrayList;

public interface INetFromJson {
    long id(JSONObject json);

    ArrayList<Bitmap> Picture(JSONObject json);

    ArrayList<MediaStore.Video> Video(JSONObject json);

    String Text(JSONObject json);

    ArrayList<MediaStore.Audio> Audio(JSONObject json);

    int like(JSONObject json);
    int repost(JSONObject json);
    int comments(JSONObject json);
    int views(JSONObject json);
   // List<Comment> comments(JSONObject json);

}
