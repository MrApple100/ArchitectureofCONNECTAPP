package com.example.architectureofconnectapp.Fragments;

import android.content.Intent;
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

import com.example.architectureofconnectapp.Cash.CreateTables.TableProfileConnectPost;
import com.example.architectureofconnectapp.Cash.Daos.DaoProfileConnectPost;
import com.example.architectureofconnectapp.ConnectThings.AdapterConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;
import com.example.architectureofconnectapp.ConnectThings.ConnectProfilefeed;
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
import java.util.HashMap;
import java.util.concurrent.Executors;

public class FragmentConnectProfilefeed extends Fragment {

    TableProfileConnectPost dataConnectProfilefeed = (TableProfileConnectPost) TableProfileConnectPost.getInstance(MainActivity.getInstance(), "ConnectProfilefeed").allowMainThreadQueries().build();
    DaoProfileConnectPost daoProfileConnectPost = dataConnectProfilefeed.ProfileConnectPostDao();

    static HashMap<Integer,FragmentConnectProfilefeed> fragmentConnectProfilefeedArrayList=new HashMap<>();
    private String NameProfileNetwork;
    final boolean[] refreshend = new boolean[1];

    private RecyclerView ProfileFeed;
    private SwipeRefreshLayout LLswipe;
    private LiveData<PagedList<ConnectPost>> pagedListData;
    private AdapterConnectNewsFeed adapter;
    private Handler handler;

    static public FragmentConnectProfilefeed getInstance(String nameProfileNetwork){
        if(fragmentConnectProfilefeedArrayList.get(nameProfileNetwork.hashCode())==null){
            fragmentConnectProfilefeedArrayList.put(nameProfileNetwork.hashCode(),new FragmentConnectProfilefeed(nameProfileNetwork));
        }
        return fragmentConnectProfilefeedArrayList.get(nameProfileNetwork.hashCode());
    }
    public FragmentConnectProfilefeed(String nameProfileNetwork) {this.NameProfileNetwork=nameProfileNetwork; }

    static public HashMap<Integer, FragmentConnectProfilefeed> getFragmentConnectProfilefeedArrayList() {
        return fragmentConnectProfilefeedArrayList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                pagedListData.observe(MainActivity.getActivity(), new Observer<PagedList<ConnectPost>>() {
                    @Override
                    public void onChanged(PagedList<ConnectPost> connectPosts) {
                        adapter.submitList(connectPosts);
                    }
                });
                ProfileFeed.setAdapter(adapter);
                refreshend[0] = true;
            }
        };

        ConnectProfilefeed connectProfilefeed = ConnectProfilefeed.getInstance(NameProfileNetwork);
        //Hmm if i delete this down string Newsfeed will be work well
        connectProfilefeed.deleteforupdate();

            MyNewsFeedThread myNewsFeedThread = new MyNewsFeedThread();
            myNewsFeedThread.start();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.connectprofilefeed,container,false);
        ProfileFeed=(RecyclerView) view.findViewById(R.id.RWProfileFeed);
        LLswipe=(SwipeRefreshLayout) view.findViewById(R.id.LLProfileswipe);
        LLswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("refresh",refreshend[0]+"");
                if(refreshend[0]) {
                    refreshend[0]=false;
                    ConnectProfilefeed connectProfilefeed = ConnectProfilefeed.getInstance(NameProfileNetwork);
                    connectProfilefeed.deleteforupdate();
                    MyNewsFeedThread myNewsFeedThread = new MyNewsFeedThread();
                    myNewsFeedThread.start();
                }
                Log.d("refresh",refreshend[0]+"");
                if(LLswipe.isRefreshing()){
                    LLswipe.setRefreshing(false);
                }

            }
        });
        System.out.println(ConnectProfilefeed.getInstance(NameProfileNetwork).getPosts().size());
        System.out.println(adapter.getItemCount());
        if (ProfileFeed.getAdapter()==null)
            ProfileFeed.setAdapter(adapter);

        return view;
    }
    class MyNewsFeedThread extends Thread {
        @Override
        public void run() {
            ConnectProfilefeed connectProfilefeed = ConnectProfilefeed.getInstance(NameProfileNetwork);
            Log.d("SSIIIZZZEEEE:", connectProfilefeed.getPosts().size()+"");
            ArrayList<IProcessNetRequest> iProcessNetRequests=new ArrayList<>();
            if(NameProfileNetwork.equals("VK")){
                VKProcessRequest vkProcessRequest=new VKProcessRequest();
                iProcessNetRequests.add(vkProcessRequest);
            }
            else if(NameProfileNetwork.equals("Twitter")) {
                TwitterProcessRequest twitterProcessRequest = new TwitterProcessRequest();
                iProcessNetRequests.add(twitterProcessRequest);
            }
            MySourceFactory mySourceFactory= new MySourceFactory(connectProfilefeed,iProcessNetRequests);
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

}
