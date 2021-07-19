package com.example.architectureofconnectapp.Twitter;

import com.example.architectureofconnectapp.IPostfromNet;
import com.example.architectureofconnectapp.VK.VKgetFromJson;

import org.json.JSONObject;

public class TwitterPost extends IPostfromNet {
    public static String NameNet="Twitter";
    public JSONObject jsongroup;
    TwitterPost(JSONObject twitterPostJsonInfo){
        TwittergetFromJson twittergetFromJson=new TwittergetFromJson();
        this.iNetFromJson=twittergetFromJson;
        this.id=twittergetFromJson.id(twitterPostJsonInfo);
        this.NameGroup=twittergetFromJson.NameGroup(twitterPostJsonInfo);
        this.NetPostJsonInfo=twitterPostJsonInfo;
        this.text=twittergetFromJson.Text(twitterPostJsonInfo);
        this.urlspick=twittergetFromJson.URLSPick(twitterPostJsonInfo);

               //Picture=twittergetFromJson.Picture(twitterPostJsonInfo);
                Video=twittergetFromJson.Video(twitterPostJsonInfo);
                Audio=twittergetFromJson.Audio(twitterPostJsonInfo);


        this.like=twittergetFromJson.like(twitterPostJsonInfo);
        this.repost=twittergetFromJson.repost(twitterPostJsonInfo);
        this.comments=twittergetFromJson.comments(twitterPostJsonInfo);
        this.views=twittergetFromJson.views(twitterPostJsonInfo);
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
    public void setDatatime(long datatime){
        this.datatime=datatime;
    }
}
