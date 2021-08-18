package com.example.architectureofconnectapp.ConnectThings;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.architectureofconnectapp.APIforServer.NetworkToEnterSocialNetwork;
import com.example.architectureofconnectapp.ConstModel.ConstNetwork;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.SocialNetwork;
import com.example.architectureofconnectapp.Model.User;
import com.example.architectureofconnectapp.Model.Users;
import com.example.architectureofconnectapp.R;
import com.example.architectureofconnectapp.VK.VKEnter;
import com.vk.sdk.VKAccessToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AdapterNetsforExit extends RecyclerView.Adapter<AdapterNetsforExit.NetsforExitHolder> {
    LayoutInflater layoutInflater;

    ArrayList<SocialNetwork> usednetworks=new ArrayList<>();

    ArrayList<ConstNetwork> constnetworks=new ArrayList<>();

    public AdapterNetsforExit(Context context, ArrayList<SocialNetwork> usednetworks,ArrayList<ConstNetwork> constnetworks) {
        this.usednetworks = usednetworks;
        this.constnetworks = constnetworks;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public NetsforExitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NetsforExitHolder(layoutInflater.inflate(R.layout.onenetforexit, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterNetsforExit.NetsforExitHolder holder, int position) {
            holder.namenet.setText(constnetworks.get(position).NameNet);
            Log.d("unknownusers", Users.getInstance().getUsersofNet().keySet().toString());
            Log.d("unknownusers", usednetworks.toString());
            Log.d("unknownusers", constnetworks.toString());
            //Log.d("unknown",String.valueOf(usednetworks.get(position).getId()));
            User user = Users.getInstance().getUsersofNet().get(constnetworks.get(position).getidname());
            if (user != null) {
                holder.user.setText(user.getFirst_name() + " " + user.getLast_name());
            }else {
                holder.user.setText("");
            }
            boolean tf=false;
            for(String name:usednetworks.stream().map(sn ->sn.getNameofNetwork()).collect(Collectors.toList())){
                if(name.equals(constnetworks.get(position).NameNet)){
                    tf=true;
                    break;
                }
            }
            if(tf) {
                holder.ButExit.setText("Exit");
                holder.ButExit.setTextColor(MainActivity.getActivity().getColor(R.color.red));
                holder.ButExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (constnetworks.get(position).NameNet == "VK") {
                            new VKEnter().Exit();
                        }
                    }
                });


            }else {
                holder.ButExit.setText("Add");
                holder.ButExit.setTextColor(MainActivity.getActivity().getColor(R.color.teal_200));
                holder.ButExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (constnetworks.get(position).NameNet == "VK") {
                            new VKEnter().Enter(MainActivity.getActivity());
                        }
                    }
                });
            }



    }

    @Override
    public int getItemCount() {
        return constnetworks.size();
    }

    public static class NetsforExitHolder extends RecyclerView.ViewHolder{
        TextView namenet,user,ButExit;

        NetsforExitHolder(View itemView) {
            super(itemView);
            namenet = itemView.findViewById(R.id.TVNETforExit);
            user = itemView.findViewById(R.id.TVUser);
            ButExit = itemView.findViewById(R.id.butExitfromnet);
        }
    }
}
