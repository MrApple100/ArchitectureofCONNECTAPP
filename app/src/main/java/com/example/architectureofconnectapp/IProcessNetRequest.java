package com.example.architectureofconnectapp;

import com.example.architectureofconnectapp.ConnectThings.ConnectPost;

import java.util.ArrayList;

public interface IProcessNetRequest {
    ArrayList<ConnectPost> makenextrequest(int count, String next_from);
    String sentNext_from();
}
