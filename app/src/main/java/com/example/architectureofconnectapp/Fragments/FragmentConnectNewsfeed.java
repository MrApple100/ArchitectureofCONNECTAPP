package com.example.architectureofconnectapp.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.architectureofconnectapp.ConnectThings.AdapterConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;
import com.example.architectureofconnectapp.DiffUtilCallback;
import com.example.architectureofconnectapp.IProcessNetRequest;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.MySourceFactory;
import com.example.architectureofconnectapp.R;
import com.example.architectureofconnectapp.Twitter.TwitterProcessRequest;
import com.example.architectureofconnectapp.VK.VKProcessRequest;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;
import java.util.concurrent.Executors;


public class FragmentConnectNewsfeed extends Fragment {

    static final boolean[] refreshend = new boolean[1];

    static RecyclerView NewsFeed;
    static SwipeRefreshLayout LLswipe;
    static LiveData<PagedList<ConnectPost>> pagedListData;
    static AdapterConnectNewsFeed adapter;
    static Handler handler;
    private static FragmentConnectNewsfeed instance;

    public static FragmentConnectNewsfeed getInstance() {
        if (instance == null) {
            instance = new FragmentConnectNewsfeed();
        }else {
            //ConnectNewsFeed connectNewsFeed = ConnectNewsFeed.getInstance();
            //connectNewsFeed.deleteforupdate();

        }
        return instance;
    }
    public FragmentConnectNewsfeed() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                pagedListData.observe(MainActivity.getActivity(), new Observer<PagedList<ConnectPost>>() {
                    @Override
                    public void onChanged(PagedList<ConnectPost> connectPosts) {
                        adapter.submitList(connectPosts);
                    }
                });
                NewsFeed.setAdapter(adapter);
                refreshend[0] =true;
            }
        };

        ConnectNewsFeed connectNewsFeed = ConnectNewsFeed.getInstance();
        //Hmm if i delete this down string Newsfeed will be work well
        connectNewsFeed.deleteforupdate();
        MyNewsFeedThread myNewsFeedThread = new MyNewsFeedThread();
        myNewsFeedThread.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.connectnewsfeed,container,false);
        NewsFeed=(RecyclerView) view.findViewById(R.id.NewsFeed);
        LLswipe=(SwipeRefreshLayout) view.findViewById(R.id.LLswipe);
        LLswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("refresh",refreshend[0]+"");
                if(refreshend[0]) {
                    refreshend[0]=false;
                    ConnectNewsFeed connectNewsFeed = ConnectNewsFeed.getInstance();
                    connectNewsFeed.deleteforupdate();
                    MyNewsFeedThread myNewsFeedThread = new MyNewsFeedThread();
                    myNewsFeedThread.start();
                }
                Log.d("refresh",refreshend[0]+"");
                if(LLswipe.isRefreshing()){
                    LLswipe.setRefreshing(false);
                }

            }
        });
        System.out.println(ConnectNewsFeed.getInstance().getPosts().size());
        System.out.println(adapter.getItemCount());
        if (NewsFeed.getAdapter()==null)
            NewsFeed.setAdapter(adapter);

        return view;
    }
    class MyNewsFeedThread extends Thread {
        @Override
        public void run() {
            ConnectNewsFeed connectNewsFeed = ConnectNewsFeed.getInstance();
            Log.d("SSIIIZZZEEEE:", connectNewsFeed.getPosts().size()+"");
            VKProcessRequest vkProcessRequest=new VKProcessRequest();
            TwitterProcessRequest twitterProcessRequest=new TwitterProcessRequest();
            ArrayList<IProcessNetRequest> iProcessNetRequests=new ArrayList<>();
            iProcessNetRequests.add(vkProcessRequest);
            iProcessNetRequests.add(twitterProcessRequest);
            MySourceFactory mySourceFactory= new MySourceFactory(connectNewsFeed,iProcessNetRequests);
            PagedList.Config config = new PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(15)
                    .setPageSize(10)
                    .build();
            pagedListData =new LivePagedListBuilder<>(mySourceFactory,config)
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build();
            DiffUtilCallback diffutilcalback=new DiffUtilCallback();
            adapter = new AdapterConnectNewsFeed(diffutilcalback);
            handler.sendEmptyMessage(1);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
               // update();
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(MainActivity.getInstance(),"hiError",Toast.LENGTH_LONG).show();
            }
        }))
            super.onActivityResult(requestCode, resultCode, data);

    }
    private void update(){
        ConnectNewsFeed connectNewsFeed = ConnectNewsFeed.getInstance();
        VKProcessRequest vkProcessRequest=new VKProcessRequest();
        TwitterProcessRequest twitterProcessRequest=new TwitterProcessRequest();
        ArrayList<IProcessNetRequest> iProcessNetRequests=new ArrayList<>();
        iProcessNetRequests.add(vkProcessRequest);
        iProcessNetRequests.add(twitterProcessRequest);
        MySourceFactory mySourceFactory= new MySourceFactory(connectNewsFeed,iProcessNetRequests);
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(5)
                .setPageSize(10)
                .build();
        pagedListData =new LivePagedListBuilder<>(mySourceFactory,config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build();
        DiffUtilCallback diffutilcalback=new DiffUtilCallback();
        adapter = new AdapterConnectNewsFeed(diffutilcalback);

        pagedListData.observe(MainActivity.getActivity(), new Observer<PagedList<ConnectPost>>() {
            @Override
            public void onChanged(@Nullable PagedList<ConnectPost> connectPosts) {
                System.out.println("onChanged");
                adapter.submitList(connectPosts);

            }
        });
        NewsFeed.setAdapter(adapter);
    }
}
