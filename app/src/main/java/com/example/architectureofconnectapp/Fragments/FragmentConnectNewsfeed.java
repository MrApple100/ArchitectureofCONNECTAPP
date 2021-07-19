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
import com.example.architectureofconnectapp.ConstModel.ConstNetwork;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class FragmentConnectNewsfeed extends Fragment {

    static final boolean[] refreshend = new boolean[1];

    static RecyclerView NewsFeed;
    static SwipeRefreshLayout LLswipe;
    static LiveData<PagedList<ConnectPost>> pagedListData;
    static AdapterConnectNewsFeed adapter;
    static Handler handler;
    private static ArrayList<FragmentConnectNewsfeed> fragmentConnectNewsfeeds=new ArrayList<>();
    //массив сетей которые сейчас есть в этом фрагменте (только они отображаются)
    private ArrayList<ConstNetwork> constNetworks=new ArrayList<>();
    //массив групп,сообществ, или другой контент, который нужно показывать именно в этой соцсети(только они отображаются)
    private HashMap<Long,ArrayList<Long>> idofgroups=new HashMap<>();

    public static FragmentConnectNewsfeed getInstance( ArrayList<ConstNetwork> constNetworks, HashMap<Long,ArrayList<Long>> idofgroups) {

        //проверка на то,есть ли этот фрагмент уже
        for(FragmentConnectNewsfeed fragmentConnectNewsfeed:fragmentConnectNewsfeeds){
            if(fragmentConnectNewsfeed.constNetworks!=null) {
                if (fragmentConnectNewsfeed.constNetworks.containsAll(constNetworks) && fragmentConnectNewsfeed.constNetworks.size() == constNetworks.size()) {
                    //массив имен сетей групп, которые хотят добавить
                    //если указан нулевой массив групп, то это newsfeed-ы всех сетей
                    if (idofgroups == null) {
                        idofgroups = new HashMap<>();
                        for (ConstNetwork constNetwork : constNetworks) {
                            ArrayList<Long> temparray = new ArrayList<>();
                            temparray.add(0l);
                            idofgroups.put(constNetwork.idname, temparray);
                        }
                    }

                    ArrayList<Long> idsofnet = (ArrayList<Long>) idofgroups.entrySet().stream().map(e -> e.getKey()).collect(Collectors.toList());
                    for (Long tempid : idsofnet) {
                        //сравнение групп
                        if (fragmentConnectNewsfeed.idofgroups != null) {
                            if (fragmentConnectNewsfeed.idofgroups.get(tempid).containsAll(idofgroups.get(tempid)) && idofgroups.size() == fragmentConnectNewsfeed.idofgroups.size()) {
                                return fragmentConnectNewsfeed;
                            }
                        }
                    }

                }
            }
        }
        //если не нашлось совпадений
        FragmentConnectNewsfeed tempinstance = new FragmentConnectNewsfeed( constNetworks,idofgroups);
        fragmentConnectNewsfeeds.add(tempinstance);

        return tempinstance;
    }

    public FragmentConnectNewsfeed() { }
    private FragmentConnectNewsfeed(ArrayList<ConstNetwork> constNetworks,HashMap<Long,ArrayList<Long>> idofgroups) {
        this.constNetworks=constNetworks;
        this.idofgroups=idofgroups;
    }

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
                System.out.println(ConnectNewsFeed.getInstance().getPosts().size());
                System.out.println(adapter.getItemCount());
                if (NewsFeed.getAdapter()==null)
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
        NewsFeed=(RecyclerView) view.findViewById(R.id.RWNewsFeed);
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

        return view;
    }
    class MyNewsFeedThread extends Thread {
        @Override
        public void run() {
            ConnectNewsFeed connectNewsFeed = ConnectNewsFeed.getInstance();
            Log.d("Newsfeed",constNetworks.size()+"");
            ArrayList<IProcessNetRequest> iProcessNetRequests=new ArrayList<>();
            for(ConstNetwork constNetwork:constNetworks){
                iProcessNetRequests.add(constNetwork.iProcessNetRequest);
            }
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

}
