package com.example.architectureofconnectapp;

import org.json.JSONObject;

public interface IInterection {
    //поставить лайк
    void like(JSONObject json, long Post_id);
    //сделать репост
    void repost();
    //сделать коммент
    void comment();
}
