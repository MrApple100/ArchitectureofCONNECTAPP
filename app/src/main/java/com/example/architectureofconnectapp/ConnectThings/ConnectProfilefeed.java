package com.example.architectureofconnectapp.ConnectThings;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.architectureofconnectapp.Cash.CreateTables.TableProfileConnectPost;

import com.example.architectureofconnectapp.Cash.Daos.DaoProfileConnectPost;
import com.example.architectureofconnectapp.Cash.Daos.DaoUser;
import com.example.architectureofconnectapp.ConnectFeeds;
import com.example.architectureofconnectapp.Fragments.FragmentConnectProfilefeed;
import com.example.architectureofconnectapp.IProcessNetRequest;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.CashConnectPost;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConnectProfilefeed implements ConnectFeeds {



    static HashMap<Integer,ConnectProfilefeed> ConnectProfilefeedArrayList=new HashMap<Integer, ConnectProfilefeed>();
    private String NameProfileNetwork;

    private int Count=10;
    private ArrayList<String> next_from=new ArrayList<>(Arrays.asList("0"));
    private ArrayList<ConnectPost> posts=new ArrayList<>();

    static public ConnectProfilefeed getInstance(String nameProfileNetwork){
        if(ConnectProfilefeedArrayList.get(nameProfileNetwork.hashCode())==null){
            ConnectProfilefeedArrayList.put(nameProfileNetwork.hashCode(),new ConnectProfilefeed(nameProfileNetwork));

        }
        return ConnectProfilefeedArrayList.get(nameProfileNetwork.hashCode());
    }
    public ConnectProfilefeed(String nameProfileNetwork){this.NameProfileNetwork=nameProfileNetwork; }

    public void deleteforupdate(){
        //posts.clear();
        next_from.clear();
        next_from=new ArrayList<>(Arrays.asList("0"));
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
            posttemps.addAll(iProcessNetRequests.get(i).makenextrequest(Count, next_from.get(i),"userfeed"));
            //posts.addAll(gettedposts);
            next_from.set(i,iProcessNetRequests.get(i).sentNext_from());
            Log.d("NEXT",next_from+"");
            Log.d("NEXT",posts.size()+"");
        }
        posts=posttemps;


    }
    private byte[] bitmaptobytes(Bitmap bitmap){
        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos); // YOU can also save it in JPEG
        byte[] bitmapdata = bos.toByteArray();
        return  bitmapdata;
    }
}
