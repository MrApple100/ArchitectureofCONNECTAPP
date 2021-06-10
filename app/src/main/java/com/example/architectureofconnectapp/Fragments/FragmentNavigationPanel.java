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
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureofconnectapp.ConnectThings.AdapterConnectNewsFeed;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class FragmentNavigationPanel extends Fragment {
    BottomNavigationItemView Create;
    BottomNavigationItemView Newsfeed;
    BottomNavigationItemView Profile;
    BottomNavigationItemView Search;
    static BottomNavigationView navigation;

    private static FragmentNavigationPanel instance;

    public static FragmentNavigationPanel getInstance() {
        if (instance == null) {
            instance = new FragmentNavigationPanel();
        }
        return instance;
    }


    public FragmentNavigationPanel() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigationpanel,container,false);
        navigation = (BottomNavigationView) view.findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setOnNavigationItemReselectedListener(mOnNavigationItemReselectedListener);
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
                   loadFragment(FragmentProfile.getInstance());
                    return true;
                case R.id.ToSearch:
                   // loadFragment(NotificationsFragment.newInstance());
                    return true;
            }
            return false;
        }

    };
    private BottomNavigationView.OnNavigationItemReselectedListener mOnNavigationItemReselectedListener = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.ToNewsFeed:
                    if(navigation.getSelectedItemId()==R.id.ToNewsFeed) {
                        if(FragmentConnectNewsfeed.NewsFeed.getChildAdapterPosition(FragmentConnectNewsfeed.NewsFeed.getChildAt(0))!=0)
                            FragmentConnectNewsfeed.NewsFeed.scrollToPosition(1);
                        FragmentConnectNewsfeed.NewsFeed.smoothScrollToPosition(0);

                    }
            }
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.alphaup,R.anim.alphadown);

        ArrayList<Fragment> fragments = (ArrayList<Fragment>) getFragmentManager().getFragments();
        for(Fragment tempfragment:fragments) {
            if(tempfragment.getId()!=FragmentNavigationPanel.getInstance().getId() && !tempfragment.isHidden())
                ft.hide(tempfragment);
        }
        ft.show(FragmentNavigationPanel.getInstance());
            if(!fragment.isHidden())
                ft.add(R.id.MainScene,fragment);
            else
                ft.show(fragment);

        ft.commit();
    }

}
