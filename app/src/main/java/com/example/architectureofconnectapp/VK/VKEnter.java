package com.example.architectureofconnectapp.VK;

import android.app.Activity;
import android.content.Context;

import com.example.architectureofconnectapp.NETLOGIN;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.httpClient.VKMultipartEntity;
import com.vk.sdk.api.methods.VKApiWall;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.util.VKUtil;

public class VKEnter implements NETLOGIN {
    @Override
    public void Enter(Activity activity) {
        String[] scope = new String[]{VKScope.FRIENDS,VKScope.GROUPS,VKScope.WALL,VKScope.PHOTOS};
        //String[] fingerprints = VKUtil.getCertificateFingerprint(activity, activity.getPackageName());
        //System.out.println(fingerprints[0]);
        if(!VKAccessToken.currentToken().hasScope(VKScope.FRIENDS,VKScope.GROUPS,VKScope.WALL,VKScope.PHOTOS)){
            VKSdk.login(activity,scope);
        }
    }
}
