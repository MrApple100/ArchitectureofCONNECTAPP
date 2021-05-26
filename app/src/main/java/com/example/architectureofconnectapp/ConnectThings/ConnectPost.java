package com.example.architectureofconnectapp.ConnectThings;


import com.example.architectureofconnectapp.IPostfromNet;

import java.util.Comparator;

public class ConnectPost extends IPostfromNet {
    private String NameNet;
    private IPostfromNet post;
    private SettingView settingView = new SettingView();
    public ConnectPost(IPostfromNet post){

        this.id=post.getId();
        this.NameNet=post.getNameNet();
        this.post=post;
        this.NetPostJsonInfo=post.getNetPostJsonInfo();
        SettingBuilder();

    }


    private void SettingBuilder(){
        if(post.getText()!=null && !post.getText().equals("")){
            settingView.setText(true);
        }
        if(post.getAudio()!=null){
            settingView.setAudio(true);
        }
        if(post.getVideo()!=null){
            settingView.setVideo(true);
        }
        if(post.getPicture(null)!=null){
            settingView.setPicture(true);
        }
        if(post.getLike()!=0){
            settingView.setLike(true);
        }
        if(post.getRepost()!=0){
            settingView.setRepost(true);
        }
        if(post.getComments()!=0){
            settingView.setComment(true);
        }
        if(post.getViews()!=0){
            settingView.setViews(true);
        }

    }

    public SettingView getSettingView() {
        return settingView;
    }
    @Override
    public String getNameNet() {
        return NameNet;
    }

    public IPostfromNet getPostElements() {
        return post;
    }


}
