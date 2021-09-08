package com.example.architectureofconnectapp.Twitter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.architectureofconnectapp.APIforServer.Network;
import com.example.architectureofconnectapp.APIforServer.NetworkToEnterSocialNetwork;
import com.example.architectureofconnectapp.Cash.CreateTables.TableSocialNetwork;
import com.example.architectureofconnectapp.Cash.CreateTables.TableUser;
import com.example.architectureofconnectapp.Cash.Daos.DaoSocialNetwork;
import com.example.architectureofconnectapp.Cash.Daos.DaoUser;
import com.example.architectureofconnectapp.Fragments.FragmentExit;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.SocialNetwork;
import com.example.architectureofconnectapp.Model.User;
import com.example.architectureofconnectapp.Model.Users;
import com.example.architectureofconnectapp.NETLOGIN;
import com.google.gson.Gson;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKServiceActivity;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import twitter4j.Twitter;
import twitter4j.TwitterBase;
import twitter4j.TwitterException;

public class TwitterEnter implements NETLOGIN {

    //BD for cash Networks AND Users
    TableSocialNetwork dataBase = (TableSocialNetwork) TableSocialNetwork.getInstance(MainActivity.getInstance(), "data1").allowMainThreadQueries().build();
    DaoSocialNetwork SocialNetworkDao = dataBase.SocialNetworkDao();
    TableUser dataUser = (TableUser) TableUser.getInstance(MainActivity.getInstance(), "dataUser").allowMainThreadQueries().build();
    DaoUser UserDao = dataUser.UserDao();

    //connection server to BD
    Network network=new Network();
    @Override
    public void Enter(Activity activity) {


        // String accessToken = "979306866982318090-qoDYhTuNGfiU4qeD11BCGue3FTGEcRg";
        // String accessTokenSecret = "eBbQBaGwqqG8DA0ZrnPY84jUXfPi1jyQcb2VoxFiKfNqP";
        //AccessToken oathAccessToken = new AccessToken(accessToken, accessTokenSecret);

        if(!Users.getInstance().getUsersofNet().containsKey((long)"Twitter".hashCode())) {
            SocialNetworkDao.update(new SocialNetwork("Twitter",null));
            Log.d("TTT","HIII");
            //TwitterEnterThread twitterEnterThread = new TwitterEnterThread();
            //twitterEnterThread.start();
            Intent intent = new Intent(MainActivity.getInstance(), PageofTwitterEnter.class);
            FragmentExit.getInstance().startActivityForResult(intent,1);

//            Log.d("twitter",String.valueOf(TwitterBASE.getinstance().getAccessToken().getUserId()));

            //(new NetworkToEnterSocialNetwork()).Enter("Twitter",new Gson().toJson(VKAccessToken.currentToken()),);

        }else{
            Log.d("Newsfeed","TTT");


            if(SocialNetworkDao.getByid("Twitter".hashCode())==null){
                SocialNetworkDao.update(new SocialNetwork("Twitter",null));
            }
            if(UserDao.getByid((long)"Twitter".hashCode())==null){
                /*
                User user1=Users.getInstance().getUsersofNet().get((long)"Twitter".hashCode());
                try {
                    user1.setToken(twitter.getOAuthAccessToken().getToken());
                    user1.setToken(twitter.getOAuthAccessToken().getTokenSecret());
                }catch(TwitterException e){}
                UserDao.update(user1);

                 */
            }
        }

    }

    public class TwitterEnterThread extends  Thread{

        public TwitterEnterThread() {

        }

        @Override
        public void run() {
            super.run();
            Twitter twitter = TwitterBASE.getinstance()
                    //.login()
                    .getTwitter();
            twitter.setOAuthAccessToken(TwitterBASE.getinstance().getAccessToken());
            try {
                Log.d("twitter",twitter.showUser(TwitterBASE.getinstance().getAccessToken().getUserId()).getName());
            } catch (TwitterException e) {
                e.printStackTrace();
            }


            twitter4j.User twitteruser= null;
            try {
                twitteruser = twitter.showUser(TwitterBASE.getinstance().getAccessToken().getUserId());
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
           // network.updateUserInfo((long)"Twitter".hashCode(),user1);
        }
    }
}
