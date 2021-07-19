package com.example.architectureofconnectapp;

import android.app.Activity;
import android.content.Context;

import twitter4j.TwitterException;

public interface NETLOGIN {
    //метод входа
    void Enter(Activity activity) throws TwitterException;
}
