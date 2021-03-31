package com.example.architectureofconnectapp;

import java.util.ArrayList;

public interface IProcessNetRequest {
    ArrayList<ConnectPost> makenextrequest(int count,String next_from);
    String sentNext_from();
}
