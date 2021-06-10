package com.example.architectureofconnectapp;

import android.util.Log;

import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ConnectFeeds {


    public void deleteforupdate();

    public List<ConnectPost> getPosts();

    public void setCount(int count);
    public void setPosts(ArrayList<IProcessNetRequest> iProcessNetRequests) ;
}
