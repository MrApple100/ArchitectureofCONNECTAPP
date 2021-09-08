package com.example.architectureofconnectapp.VK;

import com.example.architectureofconnectapp.IInterection;
import com.example.architectureofconnectapp.R;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPost;

import org.json.JSONException;
import org.json.JSONObject;

public class VKInterection implements IInterection {
    @Override
    public void like(JSONObject jsonpost,long Post_id) {
        String source_id="";
        try{
            source_id=jsonpost.getString("source_id");
        }catch(JSONException e){

        }
        VKRequest vkRequest =new VKRequest("likes.add", VKParameters.from("type","post",VKApiConst.OWNER_ID,source_id,"item_id",Post_id,VKApiConst.VERSION,"5.131"));
        System.out.println("REQUEST: "+vkRequest.toString());
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                System.out.println("RESPONSE"+response);
                try {
                    JSONObject jsonresponce = (JSONObject) response.json.get("response");
                    System.out.println(jsonresponce);
                }catch (JSONException e){
                    System.out.println(response);
                }
            }
        });
        VKApiPost vkApiPost = new VKApiPost();

    }

    @Override
    public void repost() {

    }

    @Override
    public void comment() {

    }
}
