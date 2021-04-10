package com.example.architectureofconnectapp.VK;

import com.example.architectureofconnectapp.IPostfromNet;

import org.json.JSONObject;

public class VKPost extends IPostfromNet {
    public static String NameNet="VK";
    public JSONObject jsongroup;
    VKPost(JSONObject VkPostJsonInfo){
        VKgetFromJson vkgetFromJson=new VKgetFromJson();
        this.id=vkgetFromJson.id(VkPostJsonInfo);
        this.NameGroup=vkgetFromJson.NameGroup(VkPostJsonInfo);
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
    public String getNameNet() {
        return NameNet;
    }

    public void setJsongroup(JSONObject jsongroups) {
        this.jsongroup = jsongroups;
    }

    public void setNameGroup(String nameGroup) {
        NameGroup = nameGroup;
    }
    public JSONObject getGroupJsonInfo() {
        return jsongroup;
    }

}


