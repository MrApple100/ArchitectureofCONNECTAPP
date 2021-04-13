package com.example.architectureofconnectapp.VK;

import com.example.architectureofconnectapp.IInterection;
import com.example.architectureofconnectapp.R;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class VKInterection implements IInterection {
    @Override
    public void like(long Post_id) {
        VKRequest vkRequest =new VKRequest("likes.add", VKParameters.from("type","post","item_id",Post_id,VKApiConst.ACCESS_TOKEN, "e87f08b0e87f08b0e87f08b017e8082f7bee87fe87f08b0881b5d945c603573ca20e40d"));
        vkRequest.executeSyncWithListener(new VKRequest.VKRequestListener() {
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

    }

    @Override
    public void repost() {

    }

    @Override
    public void comment() {

    }
}
