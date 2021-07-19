package com.example.architectureofconnectapp.ConstModel;

import com.example.architectureofconnectapp.IInterection;
import com.example.architectureofconnectapp.INetFromJson;
import com.example.architectureofconnectapp.IPostfromNet;
import com.example.architectureofconnectapp.IProcessNetRequest;
import com.example.architectureofconnectapp.NETLOGIN;

public class ConstNetwork implements IoneConstNetwork{
    public Long idname;
    public String NameNet;
    public NETLOGIN netlogin;
    public INetFromJson iNetFromJson;
    public IInterection iInterection;
    public IProcessNetRequest iProcessNetRequest;
    public IPostfromNet iPostfromNet;

    @Override
    public Long getidname() {
        return idname;
    }

    public ConstNetwork(String NameNet) {
        this.NameNet = NameNet;
        this.idname = (long)NameNet.hashCode();
    }

    @Override
    public NETLOGIN getEnter() {
        return netlogin;
    }
    public ConstNetwork setEnter(NETLOGIN netlogin) {
        this.netlogin=netlogin;
        return this;
    }

    @Override
    public INetFromJson getFromJson() {
        return iNetFromJson;
    }
    public ConstNetwork setNetFromJson( INetFromJson iNetFromJson) {
        this.iNetFromJson=iNetFromJson;
        return this;
    }

    @Override
    public IInterection getInterection() {
        return iInterection;
    }
    public ConstNetwork setInterection( IInterection iInterection) {
        this.iInterection=iInterection;
        return this;
    }

    @Override
    public IProcessNetRequest getProcessNetRequest() {
        return iProcessNetRequest;
    }
    public ConstNetwork setProcessNetRequest( IProcessNetRequest iProcessNetRequest) {
        this.iProcessNetRequest=iProcessNetRequest;
        return this;
    }

}
