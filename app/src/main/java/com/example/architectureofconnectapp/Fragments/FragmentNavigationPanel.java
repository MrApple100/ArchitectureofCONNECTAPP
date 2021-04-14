package com.example.architectureofconnectapp.Fragments;

import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.architectureofconnectapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

public class FragmentNavigationPanel extends Fragment {
    BottomNavigationItemView Create;
    BottomNavigationItemView Newsfeed;
    BottomNavigationItemView Profile;
    BottomNavigationItemView Search;
    public FragmentNavigationPanel() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigationpanel,container,false);
        Create = (BottomNavigationItemView) view.findViewById(R.id.ToCreate);
        Newsfeed = (BottomNavigationItemView) view.findViewById(R.id.ToNewsFeed);
        Profile = (BottomNavigationItemView) view.findViewById(R.id.ToProfile);
        Search = (BottomNavigationItemView) view.findViewById(R.id.ToSearch);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                FragmentNewConnectPost fragmentNewConnectPost = FragmentNewConnectPost.getInstance();
                if(!fragmentNewConnectPost.isInLayout()) {
                    ft.replace(R.id.MainScene,fragmentNewConnectPost);
                }
                ft.commit();
                fm.executePendingTransactions();
            }
        });
        Newsfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();


                if(!FragmentConnectNewsfeed.getInstance().isInLayout()) {
                    ft.replace(R.id.MainScene,FragmentConnectNewsfeed.getInstance());
                }
                ft.commit();
                fm.executePendingTransactions();
            }
        });
        return view;
    }
}
