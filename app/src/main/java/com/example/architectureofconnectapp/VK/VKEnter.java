package com.example.architectureofconnectapp.VK;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.architectureofconnectapp.APIforServer.Network;
import com.example.architectureofconnectapp.Model.Users;
import com.example.architectureofconnectapp.NETLOGIN;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.httpClient.VKMultipartEntity;
import com.vk.sdk.api.methods.VKApiBase;
import com.vk.sdk.api.methods.VKApiUsers;
import com.vk.sdk.api.methods.VKApiWall;
import com.vk.sdk.api.model.VKApiOwner;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.util.VKUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class VKEnter implements NETLOGIN {
    @Override
    public void Enter(Activity activity) {
        String[] scope = new String[]{VKScope.FRIENDS,VKScope.GROUPS,VKScope.WALL,VKScope.PHOTOS};
        //String[] fingerprints = VKUtil.getCertificateFingerprint(activity, activity.getPackageName());
        //System.out.println(fingerprints[0]);
        if(!VKAccessToken.currentToken().hasScope(VKScope.FRIENDS,VKScope.GROUPS,VKScope.WALL,VKScope.PHOTOS) || !Users.getInstance().getUsersofNet().containsKey("VK")){
            VKSdk.login(activity,scope);
            Network network=new Network();
            VKApiUsers vkApiUsers=new VKApiUsers();
            vkApiUsers.get().executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                    network.postUserInfo("VK",response.json);
                    System.out.println(response.json);
                    HashMap<String, JSONObject> VKs=new HashMap<String, JSONObject>();
                    try {
                        VKs.put("VK",response.json.getJSONArray("response").getJSONObject(0));
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                    Users.getInstance().setUsersofNet(VKs);
                }
            });
        }

    }
}
