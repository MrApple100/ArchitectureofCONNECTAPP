package com.example.architectureofconnectapp.ConnectThings;

import android.util.Log;

import com.example.architectureofconnectapp.IProcessNetRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ConnectNewsFeed {
    private static ConnectNewsFeed connectNewsFeed;
    private int Count=10;
    private ArrayList<String> next_from=new ArrayList<>(Arrays.asList("0","0"));
    private ArrayList<ConnectPost> posts=new ArrayList<>();
    private ConnectNewsFeed(){ }
    public static ConnectNewsFeed getInstance(){
        if(connectNewsFeed==null){
            connectNewsFeed=new ConnectNewsFeed();
        }
        return connectNewsFeed;
    }
    public void deleteforupdate(){
        //posts.clear();
        next_from.clear();
        next_from=new ArrayList<>(Arrays.asList("0","0"));
    }

    public List<ConnectPost> getPosts() {
        return posts;
    }

    public void setCount(int count) {
        Count = count;
    }
    public void setPosts(ArrayList<IProcessNetRequest> iProcessNetRequests) {
        ArrayList<ConnectPost> posttemps=new ArrayList<>();
        for(int i=0;i<iProcessNetRequests.size();i++) {
            posttemps.addAll(iProcessNetRequests.get(i).makenextrequest(Count, next_from.get(i)));
            //posts.addAll(gettedposts);
            next_from.set(i,iProcessNetRequests.get(i).sentNext_from());
            Log.d("NEXT",next_from+"");
            Log.d("NEXT",posts.size()+"");
        }
        posts=posttemps;
/*
        posts.sort(new Comparator<ConnectPost>() {
            @Override
            public int compare(ConnectPost o1, ConnectPost o2) {

                if(o1.getPostElements().getDatatime()>o2.getPostElements().getDatatime())
                    return -1;
                else
                    return 1;
            }
        });

 */
    }
}
