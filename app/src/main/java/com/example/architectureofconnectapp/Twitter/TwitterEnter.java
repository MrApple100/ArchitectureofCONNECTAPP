package com.example.architectureofconnectapp.Twitter;

import android.app.Activity;
import android.util.Log;

import com.example.architectureofconnectapp.APIforServer.Network;
import com.example.architectureofconnectapp.Model.User;
import com.example.architectureofconnectapp.Model.Users;
import com.example.architectureofconnectapp.NETLOGIN;

import twitter4j.JSONObject;
import twitter4j.Scopes;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuth2Authorization;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterEnter implements NETLOGIN {
    @Override
    public void Enter(Activity activity) {

        Twitter twitter = TwitterBASE.getinstance().getTwitter();
        String accessToken = "979306866982318090-qoDYhTuNGfiU4qeD11BCGue3FTGEcRg";
        String accessTokenSecret = "eBbQBaGwqqG8DA0ZrnPY84jUXfPi1jyQcb2VoxFiKfNqP";
        AccessToken oathAccessToken = new AccessToken(accessToken, accessTokenSecret);
        twitter.setOAuthAccessToken(oathAccessToken);
        if(!Users.getInstance().getUsersofNet().containsKey((long)"Twitter".hashCode())) {
            Log.d("TTT","HIII");
            TwitterEnterThread twitterEnterThread = new TwitterEnterThread(twitter);
            twitterEnterThread.start();
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
            Network network=new Network();
            twitter4j.User twitteruser= null;
            try {
                twitteruser = twitter.showUser(twitter.getScreenName());
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            User user1=new User(twitteruser.getName(),(long)"Twitter".hashCode(),twitteruser.getScreenName());
            network.postUserInfo((long)"Twitter".hashCode(),user1);
        }
    }
}
