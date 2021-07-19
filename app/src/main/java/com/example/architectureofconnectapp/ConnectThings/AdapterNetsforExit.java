package com.example.architectureofconnectapp.ConnectThings;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureofconnectapp.ConstModel.ConstNetwork;
import com.example.architectureofconnectapp.Model.User;
import com.example.architectureofconnectapp.Model.Users;
import com.example.architectureofconnectapp.R;

import java.util.ArrayList;

public class AdapterNetsforExit extends RecyclerView.Adapter<AdapterNetsforExit.NetsforExitHolder> {
    LayoutInflater layoutInflater;

    ArrayList<ConstNetwork> networks=new ArrayList<>();

    public AdapterNetsforExit(Context context, ArrayList<ConstNetwork> networks) {
        this.networks = networks;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public NetsforExitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NetsforExitHolder(layoutInflater.inflate(R.layout.onenetforexit, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterNetsforExit.NetsforExitHolder holder, int position) {
        holder.namenet.setText(networks.get(position).NameNet);
        User user = Users.getInstance().getUsersofNet().get(networks.get(position).idname);
        if(user!=null) {
            holder.user.setText(user.getFirst_name() + " " + user.getLast_name());
        }



    }

    @Override
    public int getItemCount() {
        return networks.size();
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
