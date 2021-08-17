package com.example.architectureofconnectapp.Fragments;

import android.content.Intent;
import android.icu.number.FormattedNumberRange;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.architectureofconnectapp.ConstModel.ConstNetwork;
import com.example.architectureofconnectapp.ConstModel.ConstNetworks;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.SocialNetwork;
import com.example.architectureofconnectapp.Model.SocialNetworks;
import com.example.architectureofconnectapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.ArrayList;

public class FragmentConnectNews extends Fragment {
    private static FragmentConnectNews instance;
    private static FragmentConnectNewsfeed activefragmentnewsfeed;
    public static FragmentConnectNews getInstance(){
        if (instance==null){
            instance=new FragmentConnectNews();
        }else{
            FragmentManager fm=instance.getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            if(!activefragmentnewsfeed.isAdded()){
                ft.add(R.id.MainFrameNewsFeed,activefragmentnewsfeed);
            }else{
                ft.show(activefragmentnewsfeed);
            }
            ft.commit();
        }
        return instance;
    }
    public FragmentConnectNews() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.connectnews,container,false);
        BottomNavigationView BNVGroups = (BottomNavigationView) view.findViewById(R.id.BNVGroups);
        //BNVGroups.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Menu menunetworks=BNVGroups.getMenu();
        menunetworks.add("ALL");
        menunetworks.add("+");
        //выбираем из всех соцсетей только те, которые используются
        ArrayList<ConstNetwork> constNetwork = ConstNetworks.getInstance().getALConstNetworks();
        ArrayList<ConstNetwork> usedfromconstNetwork=new ArrayList<>();
        ArrayList<SocialNetwork> usedsocialnetworks= SocialNetworks.getInstance().getSocialNetworks();
        for(SocialNetwork s:usedsocialnetworks){
            for(ConstNetwork c:constNetwork){
                Log.d("usedsocnet",s.getNameofNetwork()+"/"+c.NameNet);
                if(s.getNameofNetwork().equals(c.NameNet)){
                    usedfromconstNetwork.add(c);
                    break;
                }
            }
        }
        FragmentConnectNewsfeed fragmentConnectNewsfeed = FragmentConnectNewsfeed.getInstance(usedfromconstNetwork,null);
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.MainFrameNewsFeed,fragmentConnectNewsfeed);
        //ft.commit();
        ft.commitAllowingStateLoss();
        activefragmentnewsfeed=fragmentConnectNewsfeed;
        return view;

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
}
