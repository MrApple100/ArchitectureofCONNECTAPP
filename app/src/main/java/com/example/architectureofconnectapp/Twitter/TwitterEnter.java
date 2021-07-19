package com.example.architectureofconnectapp.Twitter;

import android.app.Activity;
import android.util.Log;

import com.example.architectureofconnectapp.APIforServer.Network;
import com.example.architectureofconnectapp.Cash.CreateTables.TableSocialNetwork;
import com.example.architectureofconnectapp.Cash.CreateTables.TableUser;
import com.example.architectureofconnectapp.Cash.Daos.DaoSocialNetwork;
import com.example.architectureofconnectapp.Cash.Daos.DaoUser;
import com.example.architectureofconnectapp.ConstModel.ConstNetwork;
import com.example.architectureofconnectapp.ConstModel.ConstNetworks;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.SocialNetwork;
import com.example.architectureofconnectapp.Model.SocialNetworks;
import com.example.architectureofconnectapp.Model.User;
import com.example.architectureofconnectapp.Model.Users;
import com.example.architectureofconnectapp.NETLOGIN;
import com.example.architectureofconnectapp.VK.VKEnter;
import com.example.architectureofconnectapp.VK.VKInterection;
import com.example.architectureofconnectapp.VK.VKPost;
import com.example.architectureofconnectapp.VK.VKProcessRequest;
import com.example.architectureofconnectapp.VK.VKgetFromJson;

import twitter4j.JSONObject;
import twitter4j.Scopes;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuth2Authorization;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterEnter implements NETLOGIN {

    //BD for cash Networks AND Users
    TableSocialNetwork dataBase = (TableSocialNetwork) TableSocialNetwork.getInstance(MainActivity.getInstance(), "database").allowMainThreadQueries().build();
    DaoSocialNetwork SocialNetworkDao = dataBase.SocialNetworkDao();
    TableUser dataUser = (TableUser) TableUser.getInstance(MainActivity.getInstance(), "dataUser").allowMainThreadQueries().build();
    DaoUser UserDao = dataUser.UserDao();

    //connection server to BD
    Network network=new Network();
    @Override
    public void Enter(Activity activity) {


        Twitter twitter = TwitterBASE.getinstance().getTwitter();
        // String accessToken = "979306866982318090-qoDYhTuNGfiU4qeD11BCGue3FTGEcRg";
        // String accessTokenSecret = "eBbQBaGwqqG8DA0ZrnPY84jUXfPi1jyQcb2VoxFiKfNqP";
        //AccessToken oathAccessToken = new AccessToken(accessToken, accessTokenSecret);
        //twitter.setOAuthAccessToken(oathAccessToken);
        if(!Users.getInstance().getUsersofNet().containsKey((long)"Twitter".hashCode())) {
            SocialNetworkDao.update(new SocialNetwork("Twitter"));
            Log.d("TTT","HIII");
            TwitterEnterThread twitterEnterThread = new TwitterEnterThread(twitter);
            twitterEnterThread.start();
        }else{
            Log.d("Newsfeed","TTT");


            if(SocialNetworkDao.getByid("Twitter".hashCode())==null){
                SocialNetworkDao.update(new SocialNetwork("Twitter"));
            }
            if(UserDao.getByid((long)"Twitter".hashCode())==null){
                User user1=Users.getInstance().getUsersofNet().get((long)"Twitter".hashCode());
                try {
                    user1.setToken(twitter.getOAuthAccessToken().getToken());
                    user1.setToken(twitter.getOAuthAccessToken().getTokenSecret());
                }catch(TwitterException e){}
                UserDao.update(user1);
            }
        }
    }

    public class TwitterEnterThread extends  Thread{
        Twitter twitter;
        public TwitterEnterThread(Twitter twitter) {
            this.twitter=twitter;
        }

        @Override
        public void run() {
            super.run();
            twitter4j.User twitteruser= null;
            try {
                twitteruser = twitter.showUser(twitter.getScreenName());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            User user1=new User(twitteruser.getName().split(" ")[0],(long)"Twitter".hashCode(),twitteruser.getName().split(" ")[1]);

            try {
                user1.setToken(twitter.getOAuthAccessToken().getToken());
                user1.setToken(twitter.getOAuthAccessToken().getTokenSecret());
            }catch(TwitterException e){}
            UserDao.update(user1);
            //раньше был post
            network.updateUserInfo((long)"Twitter".hashCode(),user1);
        }
    }
}
