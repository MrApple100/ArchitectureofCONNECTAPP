package com.example.architectureofconnectapp.VK;

import android.app.Activity;
import android.content.Context;

import com.example.architectureofconnectapp.NETLOGIN;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.util.VKUtil;

public class VKEnter implements NETLOGIN {
    @Override
    public void Enter(Activity activity) {
        String[] scope = new String[]{VKScope.FRIENDS,VKScope.GROUPS,VKScope.WALL,VKScope.PHOTOS};
        //String[] fingerprints = VKUtil.getCertificateFingerprint(activity, activity.getPackageName());
        //System.out.println(fingerprints[0]);
        VKSdk.login(activity,scope);
    }
}
