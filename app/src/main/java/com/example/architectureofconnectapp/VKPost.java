package com.example.architectureofconnectapp;

import android.graphics.Bitmap;
import android.provider.MediaStore;

import org.json.JSONObject;

public class VKPost extends IPostfromNet{
    public static String NameNet="VK";
    VKPost(JSONObject VkPostJsonInfo){
        VKgetFromJson vkgetFromJson=new VKgetFromJson();
        this.id=vkgetFromJson.id(VkPostJsonInfo);
        this.NetPostJsonInfo=VkPostJsonInfo;
        this.text=vkgetFromJson.Text(VkPostJsonInfo);
        this.Picture=vkgetFromJson.Picture(VkPostJsonInfo);
        this.Video=vkgetFromJson.Video(VkPostJsonInfo);
        this.Audio=vkgetFromJson.Audio(VkPostJsonInfo);
        this.like=vkgetFromJson.like(VkPostJsonInfo);
        this.repost=vkgetFromJson.repost(VkPostJsonInfo);
        this.comments=vkgetFromJson.comments(VkPostJsonInfo);
        this.views=vkgetFromJson.views(VkPostJsonInfo);

    }


    @Override
    String getNameNet() {
        return null;
    }
}