package com.example.architectureofconnectapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureofconnectapp.R;

public class FragmentNewConnectPost extends Fragment {
    static Button Cancel;
    static RecyclerView PhotoPlaces;
    static EditText TextPlace;
    private static FragmentNewConnectPost instance;

    public static FragmentNewConnectPost getInstance() {
        if (instance == null) {
            instance = new FragmentNewConnectPost();
        }
        return instance;
    }

    public FragmentNewConnectPost() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newconnectpost,container,false);
        Cancel = (Button) view.findViewById(R.id.Cancel);
        PhotoPlaces = (RecyclerView) view.findViewById(R.id.PhotosPlace);
        TextPlace = (EditText) view.findViewById(R.id.TextPlace);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPlaces.removeAllViews();
                TextPlace.setText("");
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.MainScene,FragmentConnectNewsfeed.getInstance());
                ft.commit();
                fm.executePendingTransactions();
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
