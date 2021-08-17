package com.example.architectureofconnectapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureofconnectapp.ConnectThings.AdapterNetsforExit;
import com.example.architectureofconnectapp.ConstModel.ConstNetwork;
import com.example.architectureofconnectapp.ConstModel.ConstNetworks;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.SocialNetwork;
import com.example.architectureofconnectapp.Model.SocialNetworks;
import com.example.architectureofconnectapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FragmentExit extends Fragment {
    private static FragmentExit instance;
    public static FragmentExit getInstance(){
        if(instance==null){
            instance=new FragmentExit();
        }
        return instance;
    }

    public FragmentExit() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exit, container, false);

        RecyclerView netsforexit = view.findViewById(R.id.RWNetsforExit);
        ArrayList<SocialNetwork> socialNetworks = SocialNetworks.getInstance().getSocialNetworks();
        ArrayList<ConstNetwork> constNetworks = ConstNetworks.getInstance().getALConstNetworks();
        netsforexit.setAdapter(new AdapterNetsforExit(MainActivity.getInstance(),socialNetworks,constNetworks));

        TextView cancel = view.findViewById(R.id.CancelExit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitfunc();

            }
        });

        LinearLayout LLEmpty = view.findViewById(R.id.LLEmpty);
        LLEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitfunc();
            }
        });
        LinearLayout LLExitpanel = view.findViewById(R.id.LLExitpanel);
        LLExitpanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nothing
                //clicable is false
            }
        });

        return view;
    }


    private void exitfunc(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        FragmentNavigationPanel fragmentNavigationPanel=FragmentNavigationPanel.getInstance();
        ft.hide(instance);
        ft.commit();
    }
}
