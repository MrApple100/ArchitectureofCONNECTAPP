package com.example.architectureofconnectapp;

import org.json.JSONObject;

public interface IInterection {
    void like(JSONObject json, long Post_id);
    void repost();
    void comment();
}
