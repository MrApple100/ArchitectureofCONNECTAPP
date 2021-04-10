package com.example.architectureofconnectapp.VK;

import android.os.Looper;
import android.util.Log;

import com.example.architectureofconnectapp.ConnectPost;
import com.example.architectureofconnectapp.IProcessNetRequest;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VKProcessRequest implements IProcessNetRequest {
    String next_fromtemp="0";
    @Override
    public ArrayList<ConnectPost> makenextrequest(int count, String next_from) {
        checkNotMain();
        ArrayList<ConnectPost> connectPosts=new ArrayList<>();

        VKRequest vkRequest =new VKRequest("newsfeed.get", VKParameters.from(VKApiConst.COUNT,count,"start_from",next_from,VKApiConst.FILTERS,"post,photos,notes,friends"));
        System.out.println("REQUEST:"+vkRequest.context);
        vkRequest.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                //получаем Connect посты
                try {
                    JSONObject jsonresponce = (JSONObject) response.json.get("response");
                    next_fromtemp=jsonresponce.getString("next_from");
                    JSONArray jsonitems = (JSONArray) jsonresponce.get("items");
                    JSONArray jsongroups = (JSONArray) jsonresponce.get("groups");
                    for (int i = 0; i < jsonitems.length(); i++) {
                        JSONObject jsonitem = (JSONObject) jsonitems.get(i);
                        VKPost vkPost=new VKPost(jsonitem);
                        String idgroup = jsonitem.getString("source_id").substring(1);
                        for(int j=0;j<jsongroups.length();j++) {
                            JSONObject jsongroup=(JSONObject) jsongroups.get(j);
                            if(jsongroup.getString("id").compareTo(idgroup)==0){
                                vkPost.setNameGroup(jsongroup.getString("name"));
                                vkPost.setJsongroup(jsongroup);
                                System.out.println(vkPost.getNameGroup());
                            }
                        }
                        ConnectPost connectPost=new ConnectPost(vkPost);
                        connectPosts.add(connectPost);
                    }
                }catch (JSONException jsonException){
                    Log.e("ERROR","VKProcessRequest");
                }
            }
        });

        return connectPosts;
    }

    public String sentNext_from() {
        return next_fromtemp;
    }

    static void checkNotMain() {
        if (isMain()) {
            throw new IllegalStateException("VKProcessRequest/Method call should not happen from the main thread.");
        }
    }


    static boolean isMain() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
