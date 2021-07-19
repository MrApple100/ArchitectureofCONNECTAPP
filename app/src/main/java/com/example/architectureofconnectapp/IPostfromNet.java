package com.example.architectureofconnectapp;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.example.architectureofconnectapp.Model.CashConnectPost;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

public abstract class IPostfromNet {
    protected long id;
    protected INetFromJson iNetFromJson;
    private CashConnectPost cashConnectPost;

    public long getId() {
        return id;
    }
    protected Long datatime;
    protected String NameGroup;
    protected String text;
    protected ArrayList<String> urlspick=new ArrayList<>();
    protected ArrayList<Bitmap> Picture=new ArrayList<>();
    protected ArrayList<MediaStore.Video> Video=new ArrayList<>();
    protected ArrayList<MediaStore.Audio> Audio=new ArrayList<>();
    protected JSONObject NetPostJsonInfo;
    protected int like=0;
    protected int repost=0;
    protected int comments=0;
    protected int views=0;

    public void setCashConnectPost(CashConnectPost cashConnectPost) {
        this.cashConnectPost = cashConnectPost;
    }

    public CashConnectPost getCashConnectPost() {
        return cashConnectPost;
    }

    public abstract String getNameNet();

    public String getNameGroup(){ return NameGroup;};

    public String getText() {
        return text;
    }

    public INetFromJson getiNetFromJson() {
        return iNetFromJson;
    }

    public ArrayList<String> getPicture(Handler handler) {
        if(handler!=null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (Picture.size() == 0)
                        Picture = iNetFromJson.Picture(NetPostJsonInfo);
                    Message message = new Message();
                    message.obj = Picture;
                    handler.sendMessage(message);
                }
            }).start();
        }
        return urlspick;
    }
    public ArrayList<Bitmap> getPicture() {
        return Picture;
    }

    public ArrayList<MediaStore.Video> getVideo() {
        return Video;
    }

    public ArrayList<String> getUrlspick() {
        return urlspick;
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
    public int getViews() {
        return views;
    }

    public Long getDatatime() {
        return datatime;
    }
}
