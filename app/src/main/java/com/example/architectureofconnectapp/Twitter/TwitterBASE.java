package com.example.architectureofconnectapp.Twitter;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureofconnectapp.Fragments.FragmentExit;
import com.example.architectureofconnectapp.MainActivity;

import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterBASE {
    private Twitter twitter;
    static private TwitterBASE twitterBASE;
    private Paging paging=new Paging(1,10);

    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

    private  static final String TWITTER_CALLBACK_URL ="oauth://t4jsample";
    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";

    private static RequestToken requestToken;
    private AccessToken accessToken;

    // Shared Preferences
    private static SharedPreferences mSharedPreferences;

    ConfigurationBuilder cb;
    Configuration configuration;

    static public TwitterBASE getinstance(){
        if(twitterBASE==null){
            twitterBASE=new TwitterBASE();
        }
        return twitterBASE;
    }

    private TwitterBASE() {
        // Shared Preferences
        mSharedPreferences = PageofTwitterEnter.getInstanse().getSharedPreferences(
                "MyPref", 0);
        cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setJSONStoreEnabled(true)
                .setOAuthConsumerKey("t23AzZdPOa0fqRSRhpAIF0NEk")
                .setOAuthConsumerSecret("GIkUcJWl7QSxdfibWelfBF0Wqv53vhqGnUUhgB385NlH0ZpiW1");
                /*.setOAuthAccessToken("979306866982318090-Ae3FWO244axcDeinJtS2kyc37wvD0Bw")
                .setOAuthAccessTokenSecret("alK8Pd1jXHJjULysF8sNKPbc9ODO7qHAnPnSBthdK5KFU");
                 */
    }


    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Check user already logged in your application using twitter Login flag is
     * fetched from Shared Preferences
     * */
    public TwitterBASE setuprequestToken(){
        System.out.println(isTwitterLoggedInAlready());
        if (!isTwitterLoggedInAlready()) {

            configuration = cb.build();
            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
            try {

                requestToken = twitter
                        //.getOAuthRequestToken("oauth://com.example.twitter");
                        .getOAuthRequestToken();
                PageofTwitterEnter.getInstanse().startActivity(new Intent(Intent.ACTION_VIEW, Uri
                        .parse(requestToken.getAuthenticationURL())));
                Log.d("requestTokenTw", requestToken.getAuthenticationURL());
                Log.d("requestTokenTw", requestToken.getAuthorizationURL());
                Log.d("requestTokenTw", requestToken.getToken());
                Log.d("requestTokenTw", requestToken.getTokenSecret());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //}
            // }).start();
        }
        /*

         */
        return this;
    }

    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        mSharedPreferences.edit().putBoolean(PREF_KEY_TWITTER_LOGIN, false).apply();

        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }

    public static RequestToken getRequestToken() {
        return requestToken;
    }
    public TwitterBASE login(String validate){
        if (!isTwitterLoggedInAlready()) {
                Log.d("validatekey",validate);


                final String verifier = validate;
                System.out.println(verifier);
                try {

                    try {

                        // Get the access token
                        TwitterBASE.getinstance().accessToken = twitter.getOAuthAccessToken(
                                requestToken, verifier);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("asdf","asdf");

                    // Shared Preferences
                    SharedPreferences.Editor e = mSharedPreferences.edit();

                    // After getting access token, access token secret
                    // store them in application preferences
                    e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                    e.putString(PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret());
                    // Store login status - true
                    e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
                    e.apply(); // save changes

                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());

                    twitter = new TwitterFactory(configuration).getInstance(accessToken);

                    System.out.println("Screen"+twitter.getScreenName());

                    // Getting user details from twitter
                    // For now i am getting his name only
                    long userID = accessToken.getUserId();


                } catch (Exception e) {
                    // Check log for login errors
                    Log.e("Twitter Login Error", "> " + e.getMessage());
                    e.printStackTrace();
                }
        }
        return this;
    }

}
