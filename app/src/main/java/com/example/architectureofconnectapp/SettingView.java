package com.example.architectureofconnectapp;

public class SettingView {
    private Boolean text=false;
    private Boolean picture=false;
    private Boolean Video=false;
    private Boolean Audio=false;
    private Boolean like=false;
    private Boolean repost=false;
    private Boolean comment=false;

    public Boolean getText() {
        return text;
    }

    public Boolean getPicture() {
        return picture;
    }

    public Boolean getVideo() {
        return Video;
    }

    public Boolean getAudio() {
        return Audio;
    }

    public Boolean getLike() {
        return like;
    }

    public Boolean getRepost() {
        return repost;
    }

    public Boolean getComment() {
        return comment;
    }

    public void setText(Boolean text) {
        this.text = text;
    }

    public void setPicture(Boolean picture) {
        this.picture = picture;
    }

    public void setVideo(Boolean video) {
        Video = video;
    }

    public void setAudio(Boolean audio) {
        Audio = audio;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public void setRepost(Boolean repost) {
        this.repost = repost;
    }

    public void setComment(Boolean comment) {
        this.comment = comment;
    }
}
