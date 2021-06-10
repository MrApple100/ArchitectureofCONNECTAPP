package com.example.architectureofconnectapp.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.architectureofconnectapp.Cash.CreateTables.TableSocialNetwork;
import com.example.architectureofconnectapp.Cash.Daos.DaoSocialNetwork;
import com.example.architectureofconnectapp.ConnectThings.AdapterConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;
import com.example.architectureofconnectapp.ConnectThings.ConnectProfilefeed;
import com.example.architectureofconnectapp.DiffUtilCallback;
import com.example.architectureofconnectapp.IProcessNetRequest;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.SocialNetwork;
import com.example.architectureofconnectapp.Model.SocialNetworks;
import com.example.architectureofconnectapp.MySourceFactory;
import com.example.architectureofconnectapp.R;
import com.example.architectureofconnectapp.Twitter.TwitterProcessRequest;
import com.example.architectureofconnectapp.VK.VKProcessRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executors;


public class FragmentProfile extends Fragment {
    private Fragment fragmentinprofilefeed;
    private static FragmentProfile instance;

    public static FragmentProfile getInstance() {
        if (instance == null) {
            instance = new FragmentProfile();
        } else {


        }
        return instance;
    }

    //BD for cash Networks
    TableSocialNetwork dataBase = (TableSocialNetwork) TableSocialNetwork.getInstance(MainActivity.getInstance(), "database").allowMainThreadQueries().build();
    DaoSocialNetwork SocialNetworkDao = dataBase.SocialNetworkDao();

    public FragmentProfile() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        BottomNavigationView BNNNetworks = (BottomNavigationView) view.findViewById(R.id.RWNetworks);
        BNNNetworks.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menunetworks=BNNNetworks.getMenu();

        ArrayList<SocialNetwork> socialNetworks=(ArrayList<SocialNetwork>) SocialNetworkDao.getAll();
        for(SocialNetwork socialNetwork:socialNetworks){
            Log.d("NET",socialNetwork.getNameofNetwork());
            menunetworks.add(socialNetwork.getNameofNetwork());
            FragmentConnectProfilefeed fragmentConnectProfilefeed=FragmentConnectProfilefeed.getInstance(socialNetwork.getNameofNetwork());
        }

        HashMap<Integer, FragmentConnectProfilefeed> fragmentConnectProfilefeed=FragmentConnectProfilefeed.getFragmentConnectProfilefeedArrayList();
        loadFragment(fragmentConnectProfilefeed.get(menunetworks.getItem(0).getTitle().hashCode()));
        return view;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String title =(String) item.getTitle();
            HashMap<Integer, FragmentConnectProfilefeed> fragmentConnectProfilefeed=FragmentConnectProfilefeed.getFragmentConnectProfilefeedArrayList();
            loadFragment(fragmentConnectProfilefeed.get(title.hashCode()));
            return true;
        }

    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.alphaup,R.anim.alphadown);
        if(fragmentinprofilefeed!=null)
            ft.hide(fragmentinprofilefeed);
        fragmentinprofilefeed=fragment;
        if(!fragment.isAdded()) {
            ft.add(R.id.ProfileFeed,fragment);
        }else{
            System.out.println(fragment.getId());
            ft.show(fragment);
        }

            ft.show(fragment);


        ft.commit();
    }
}
