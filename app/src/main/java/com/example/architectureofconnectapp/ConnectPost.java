package com.example.architectureofconnectapp;



public class ConnectPost extends IPostfromNet{
    private String NameNet;
    private IPostfromNet post;
    private SettingView settingView = new SettingView();
    ConnectPost(IPostfromNet post){

        this.id=post.getId();
        this.NameNet=post.getNameNet();
        this.post=post;
        this.NetPostJsonInfo=post.getNetPostJsonInfo();
        SettingBuilder();

    }


    private void SettingBuilder(){
        if(post.text!=null){
            settingView.setText(true);
        }
        if(post.Audio!=null){
            settingView.setAudio(true);
        }
        if(post.Video!=null){
            settingView.setVideo(true);
        }
        if(post.Picture!=null){
            settingView.setPicture(true);
        }
        if(post.like!=0){
            settingView.setLike(true);
        }
        if(post.repost!=0){
            settingView.setRepost(true);
        }
        if(post.comments!=0){
            settingView.setComment(true);
        }
        if(post.views!=0){
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
