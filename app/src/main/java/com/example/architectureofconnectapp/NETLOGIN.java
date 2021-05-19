package com.example.architectureofconnectapp;

import android.app.Activity;
import android.content.Context;

import twitter4j.TwitterException;

public interface NETLOGIN {
    void Enter(Activity activity) throws TwitterException;
}
