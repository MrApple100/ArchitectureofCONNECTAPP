package com.example.architectureofconnectapp.ConstModel;

import android.util.Log;

import java.security.AlgorithmConstraints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class ConstNetworks {
    private static ConstNetworks instance;

    public static ConstNetworks getInstance(){
        if(instance==null){
            instance=new ConstNetworks();
        }
        return instance;
    }

    private ConstNetworks() { }

    HashMap<Long,ConstNetwork> constNetworks=new HashMap<>();

    public HashMap<Long, ConstNetwork> getHMConstNetworks() {
        return constNetworks;
    }
    public ArrayList<ConstNetwork> getALConstNetworks() {

        ArrayList<ConstNetwork> ALconstNetworks=new ArrayList<>();
        ALconstNetworks =(ArrayList) constNetworks.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());

        return ALconstNetworks;
    }

    public void setConstNetworks(HashMap<Long, ConstNetwork> constNetworks) {
        this.constNetworks = constNetworks;
    }
    public void addConstNetwork(Long id, ConstNetwork constNetwork) {
        constNetworks.put(id,constNetwork);
    }
}
