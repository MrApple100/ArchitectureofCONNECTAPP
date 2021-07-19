package com.example.architectureofconnectapp.ConstModel;

import com.example.architectureofconnectapp.IInterection;
import com.example.architectureofconnectapp.INetFromJson;
import com.example.architectureofconnectapp.IPostfromNet;
import com.example.architectureofconnectapp.IProcessNetRequest;
import com.example.architectureofconnectapp.NETLOGIN;

public interface IoneConstNetwork {
    public Long getidname();
    public NETLOGIN getEnter();
    public INetFromJson getFromJson();
    public IInterection getInterection();
    public IProcessNetRequest getProcessNetRequest();

}
