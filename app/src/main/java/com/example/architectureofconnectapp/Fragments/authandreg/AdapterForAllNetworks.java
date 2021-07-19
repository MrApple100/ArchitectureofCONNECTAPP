package com.example.architectureofconnectapp.Fragments.authandreg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureofconnectapp.APIforServer.Network;
import com.example.architectureofconnectapp.ConnectThings.AdapterNetsforExit;
import com.example.architectureofconnectapp.ConstModel.ConstNetwork;
import com.example.architectureofconnectapp.R;

import java.util.ArrayList;


public class AdapterForAllNetworks extends RecyclerView.Adapter<AdapterForAllNetworks.AdapterNetworkHolder> {
    LayoutInflater layoutInflater;

    private ArrayList<ConstNetwork> networks;
    private ArrayList<Boolean> isAdded=new ArrayList<>();

    public AdapterForAllNetworks(Context context, ArrayList<ConstNetwork> networks) {
        this.networks = networks;

        this.layoutInflater = LayoutInflater.from(context);
        for(int i=0;i<networks.size();i++){
            isAdded.add(false);
        }
    }

    public ArrayList<Boolean> getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(int position, Boolean isAdded) {
        this.isAdded.set(position,isAdded);
    }

    @NonNull
    @Override
    public AdapterNetworkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterNetworkHolder(layoutInflater.inflate(R.layout.onenetworkfromenternw, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNetworkHolder holder, int position) {
        ConstNetwork network=networks.get(position);
        System.out.println("SIZZE: " + networks.size());
        holder.name.setText(network.NameNet);
        holder.addornot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAdded.set(position,isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return networks.size();
    }

    public static class AdapterNetworkHolder extends RecyclerView.ViewHolder{
        ImageView logo;
        TextView name;
        Switch addornot;
        public AdapterNetworkHolder(View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.IVlogo);
            name = itemView.findViewById(R.id.TVname);
            addornot = itemView.findViewById(R.id.Saddornot);

        }
    }
}
