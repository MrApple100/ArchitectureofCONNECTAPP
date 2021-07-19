package com.example.architectureofconnectapp;

import android.util.Log;

import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ConnectFeeds {

    //обновление страницы
    public void deleteforupdate();
    //получение готовых постов
    public List<ConnectPost> getPosts();
    //установка по сколько загружать за раз
    public void setCount(int count);
    //создание постов
    public void setPosts(ArrayList<IProcessNetRequest> iProcessNetRequests) ;
}
