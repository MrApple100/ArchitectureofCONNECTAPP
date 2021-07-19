package com.example.architectureofconnectapp.VK;

import com.example.architectureofconnectapp.IPostfromNet;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class VKPost extends IPostfromNet {
    public static String NameNet="VK";
    public JSONObject jsongroup;
    VKPost(JSONObject VkPostJsonInfo){
        VKgetFromJson vkgetFromJson=new VKgetFromJson();
        this.id=vkgetFromJson.id(VkPostJsonInfo);
        this.iNetFromJson=vkgetFromJson;
        this.NameGroup=vkgetFromJson.NameGroup(VkPostJsonInfo);
        this.NetPostJsonInfo=VkPostJsonInfo;
        this.text=vkgetFromJson.Text(VkPostJsonInfo);
        this.urlspick=vkgetFromJson.URLSPick(VkPostJsonInfo);

               // Picture=vkgetFromJson.Picture(VkPostJsonInfo);
                Video=vkgetFromJson.Video(VkPostJsonInfo);
                Audio=vkgetFromJson.Audio(VkPostJsonInfo);

        this.like=vkgetFromJson.like(VkPostJsonInfo);
        this.repost=vkgetFromJson.repost(VkPostJsonInfo);
        this.comments=vkgetFromJson.comments(VkPostJsonInfo);
        this.views=vkgetFromJson.views(VkPostJsonInfo);
        this.datatime=vkgetFromJson.datatime(VkPostJsonInfo);
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


