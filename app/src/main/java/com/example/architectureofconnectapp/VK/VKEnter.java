package com.example.architectureofconnectapp.VK;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.architectureofconnectapp.APIforServer.Network;
import com.example.architectureofconnectapp.Cash.CreateTables.TableSocialNetwork;
import com.example.architectureofconnectapp.Cash.CreateTables.TableUser;
import com.example.architectureofconnectapp.Cash.Daos.DaoSocialNetwork;
import com.example.architectureofconnectapp.Cash.Daos.DaoUser;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.SocialNetwork;
import com.example.architectureofconnectapp.Model.SocialNetworks;
import com.example.architectureofconnectapp.Model.User;
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

import twitter4j.TwitterException;

public class VKEnter implements NETLOGIN {
    //BD for cash Networks AND Users
    TableSocialNetwork dataSocialNetwork = (TableSocialNetwork) TableSocialNetwork.getInstance(MainActivity.getInstance(), "database").allowMainThreadQueries().build();
    DaoSocialNetwork SocialNetworkDao = dataSocialNetwork.SocialNetworkDao();
    TableUser dataUser = (TableUser) TableUser.getInstance(MainActivity.getInstance(), "dataUser").allowMainThreadQueries().build();
    DaoUser UserDao = dataUser.UserDao();

    @Override
    public void Enter(Activity activity) {
        String[] scope = new String[]{VKScope.FRIENDS,VKScope.GROUPS,VKScope.WALL,VKScope.PHOTOS};
        //String[] fingerprints = VKUtil.getCertificateFingerprint(activity, activity.getPackageName());
        //System.out.println(fingerprints[0]);


        if(Users.getInstance().getUsersofNet().get((long)"VK".hashCode())==null || !VKAccessToken.currentToken().hasScope(VKScope.FRIENDS,VKScope.GROUPS,VKScope.WALL,VKScope.PHOTOS)){
            SocialNetworkDao.update(new SocialNetwork("VK"));
            Log.d("TOKEN",UserDao.getByid((long)"VK".hashCode()).getToken()+" / "+VKAccessToken.currentToken().accessToken);
            VKSdk.login(activity,scope);

            Network network=new Network();
            VKApiUsers vkApiUsers=new VKApiUsers();
            vkApiUsers.get().executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);

                    try {
                        JSONObject jsonUser = response.json.getJSONArray("response").getJSONObject(0);
                        User user1=new User(jsonUser.getString("first_name"),(long)"VK".hashCode(),jsonUser.getString("last_name"));
                        user1.setToken(VKAccessToken.currentToken().accessToken);
                        user1.setSecrettoken(VKAccessToken.currentToken().secret);
                        UserDao.update(user1);
                        network.updateUserInfo((long)"VK".hashCode(),user1);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }


                }
            });
        }else{
            if(SocialNetworkDao.getByid("VK".hashCode())==null){
                SocialNetworkDao.insert(new SocialNetwork("Twitter"));
            }
            if(UserDao.getByid("VK".hashCode())==null || !UserDao.getByid((long)"VK".hashCode()).getToken().equals(VKAccessToken.currentToken().accessToken)){
                User user1=Users.getInstance().getUsersofNet().get((long)"Twitter".hashCode());
                user1.setToken(VKAccessToken.currentToken().accessToken);
                user1.setSecrettoken(VKAccessToken.currentToken().secret);
                UserDao.insert(user1);
            }else{
                Log.d("TOKEN", UserDao.getByid((long)"VK".hashCode()).getToken()+" / "+VKAccessToken.currentToken().accessToken);
            }
        }

    }
}
