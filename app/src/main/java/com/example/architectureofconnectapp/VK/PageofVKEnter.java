package com.example.architectureofconnectapp.VK;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.architectureofconnectapp.MainActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class PageofVKEnter  extends android.app.Application{
        @Override
        public void onCreate() {
            super.onCreate();
            VKSdk.initialize(this);
            tokenTracker.startTracking();
        }

        private VKAccessTokenTracker tokenTracker = new VKAccessTokenTracker() {
            @Override
            public void onVKAccessTokenChanged(@Nullable VKAccessToken oldToken, @Nullable VKAccessToken newToken) {
                if(newToken==null){
                    Log.d("TOKEN", oldToken.accessToken+" / "+newToken.accessToken);
                    Intent intent=new Intent(PageofVKEnter.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }

        };


    }

