package com.example.architectureofconnectapp.Twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterBASE {
    private Twitter twitter;
    static private TwitterBASE twitterBASE;
    static public TwitterBASE getinstance(){
        if(twitterBASE==null){
            twitterBASE=new TwitterBASE();
        }
        return twitterBASE;
    }
    private TwitterBASE() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setJSONStoreEnabled(true)
                .setOAuthConsumerKey("JUSDnViLF5Ds7eq0zpVPdLZNx")
                .setOAuthConsumerSecret("rsLQIfrFOkOcZpuVGdL6Hmk9nugk9Lt15J2QQvQT2sY9hKr7b6")
                .setOAuthAccessToken("979306866982318090-qoDYhTuNGfiU4qeD11BCGue3FTGEcRg")
                .setOAuthAccessTokenSecret("eBbQBaGwqqG8DA0ZrnPY84jUXfPi1jyQcb2VoxFiKfNqP");
        this.twitter = new TwitterFactory(cb.build()).getInstance();
    }

    public Twitter getTwitter() {
        return twitter;
    }
}
