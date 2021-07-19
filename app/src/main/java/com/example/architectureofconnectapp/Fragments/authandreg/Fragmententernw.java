package com.example.architectureofconnectapp.Fragments.authandreg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureofconnectapp.ConstModel.ConstNetwork;
import com.example.architectureofconnectapp.ConstModel.ConstNetworks;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.R;

import java.util.ArrayList;

public class Fragmententernw extends Fragment {

    private static Fragmententernw instance;
    public static Fragmententernw getInstance(){
        if (instance==null){
            instance=new Fragmententernw();
        }

        return instance;
    }
    public Fragmententernw() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.enternwpage,container,false);
        RecyclerView allNetworks=view.findViewById(R.id.RVAllNetworks);
        allNetworks.setAdapter(new AdapterForAllNetworks(MainActivity.getInstance(),ConstNetworks.getInstance().getALConstNetworks()));
        ArrayList<ConstNetwork> networks = ConstNetworks.getInstance().getALConstNetworks();
        System.out.println("SIZE: " + networks.size());
        TextView toContinue=view.findViewById(R.id.toContinue);

        TextView toenterlp=view.findViewById(R.id.toenterlp);
        toenterlp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.tobottomexit,R.anim.tobottom);
                Fragmententerlp fragmententerlp = Fragmententerlp.getInstance();
                ft.hide(instance);
                if(!fragmententerlp.isAdded()){
                    ft.add(R.id.MainScene,fragmententerlp);
                }else{
                    ft.show(fragmententerlp);
                }
                ft.commit();
                ft.addToBackStack("toenterlp");
            }
        });
        return view;
    }
}
