package com.example.architectureofconnectapp;

import android.graphics.Bitmap;
import android.provider.MediaStore;

import org.json.JSONObject;

import java.util.ArrayList;

public abstract class IPostfromNet {
    protected long id;

    public long getId() {
        return id;
    }

    protected String text;
    protected ArrayList<Bitmap> Picture;
    protected ArrayList<MediaStore.Video> Video;
    protected ArrayList<MediaStore.Audio> Audio;
    protected JSONObject NetPostJsonInfo;
    protected int like=0;
    protected int repost=0;
    protected int comments=0;

    abstract String getNameNet();

    public String getText() {
        return text;
    }

    public ArrayList<Bitmap> getPicture() {
        return Picture;
    }

    public ArrayList<MediaStore.Video> getVideo() {
        return Video;
    }

    public ArrayList<MediaStore.Audio> getAudio() {
        return Audio;
    }

    public JSONObject getNetPostJsonInfo() {
        return NetPostJsonInfo;
    }

    public int getLike() {
        return like;
    }

    public int getRepost() {
        return repost;
    }

    public int getComments() {
        return comments;
    }
}
