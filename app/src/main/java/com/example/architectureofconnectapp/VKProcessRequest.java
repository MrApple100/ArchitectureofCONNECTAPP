package com.example.architectureofconnectapp;

import android.util.Log;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VKProcessRequest implements IProcessNetRequest{

    @Override
    public ArrayList<ConnectPost> makenextrequest(int count,String next_from)throws IllegalThreadStateException {
        ArrayList<ConnectPost> connectPosts=new ArrayList<>();
        VKRequest vkRequest =new VKRequest("newsfeed.get", VKParameters.from(VKApiConst.COUNT,count,"start_from",next_from,VKApiConst.FILTERS,"post,photos,notes,friends"));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                //получаем Connect посты
                try {
                    JSONObject jsonresponce = (JSONObject) response.json.get("response");
                    JSONArray jsonitems = (JSONArray) jsonresponce.get("items");
                    for (int i = 0; i < jsonitems.length(); i++) {
                        JSONObject jsonitem = (JSONObject) jsonitems.get(i);
                        connectPosts.add(new ConnectPost(new VKPost(jsonitem)));
                    }
                }catch (JSONException jsonException){
                    Log.e("ERROR","VKProcessRequest");
                }
            }
        });

        return connectPosts;
    }
}
