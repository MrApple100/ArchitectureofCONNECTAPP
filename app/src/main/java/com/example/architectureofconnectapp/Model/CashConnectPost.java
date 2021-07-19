package com.example.architectureofconnectapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.architectureofconnectapp.IPostfromNet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

@Entity
public class CashConnectPost {

    @PrimaryKey
    private long id;
    private Long datatime;
    private String NameNet;
    private int network;
    private int type;
    private String NetPostJsonInfo;
    private String Text;
    private String photo;
    private byte[] audio;
    private byte[] video;
    protected int like=0;
    protected int repost=0;
    protected int comments=0;
    protected int views=0;

    public CashConnectPost(long id) {
        this.id = id;
    }


    public Long getDatatime() {
        return datatime;
    }

    public void setDatatime(Long datatime) {
        this.datatime = datatime;
    }

    public JSONObject getJSONNetPostJsonInfo() {
        try {
            return new JSONObject(NetPostJsonInfo);
        }catch (Exception e){

        }
        return null;
    }

    public String getNetPostJsonInfo(){

        return NetPostJsonInfo;
    }

    public void setNetPostJsonInfo(String netPostJsonInfo) {
        NetPostJsonInfo = netPostJsonInfo;
    }

    public void setNetPostJsonInfo(JSONObject netPostJsonInfo) {
        NetPostJsonInfo = netPostJsonInfo.toString();
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getRepost() {
        return repost;
    }

    public void setRepost(int repost) {
        this.repost = repost;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public int getNetwork() {
        return network;
    }

    public void setNetwork(int network) {
        this.network = network;
    }


    public String getNameNet() {
        return NameNet;
    }

    public void setNameNet(String nameNet) {
        NameNet = nameNet;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public byte[] getAudio() {
        return audio;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }

    public byte[] getVideo() {
        return video;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }
}
