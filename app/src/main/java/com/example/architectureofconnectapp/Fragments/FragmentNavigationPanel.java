package com.example.architectureofconnectapp.Fragments;

import android.content.ClipData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

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
        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        return view;

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.ToCreate:
                    loadFragment(FragmentNewConnectPost.getInstance());
                    return true;
                case R.id.ToNewsFeed:
                    loadFragment(FragmentConnectNewsfeed.getInstance());
                    return true;
                case R.id.ToProfile:
                   // loadFragment(NotificationsFragment.newInstance());
                    return true;
                case R.id.ToSearch:
                   // loadFragment(NotificationsFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.alphaup,R.anim.alphadown);
        ft.replace(R.id.MainScene, fragment);
        ft.commit();
    }

}
