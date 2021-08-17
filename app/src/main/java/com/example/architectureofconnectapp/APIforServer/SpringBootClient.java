package com.example.architectureofconnectapp.APIforServer;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.architectureofconnectapp.Model.AuthAndReg.DataUserReg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SpringBootClient {

    private WebClient webClient = new OkHttpComponent();
    private static final Logger logger = LoggerFactory.getLogger(SpringBootClient.class);
    private SpringIdentity identity = null;
    private Map<String,String> headers=new HashMap<>();
    private static SpringBootClient instance;

    public static SpringBootClient getInstance(){
        if(instance==null){
            instance=new SpringBootClient();
        }
        return instance;
    }

    private SpringBootClient() {

    }

    //на вход подается ссылка
    //формируется headers из csrf
    //делается гет запрос
    public Map<String, String> getSecured() {
        Map<String, String> headers = new HashMap<>();

        String csrf = webClient.getToken();
        headers.put("_csrf", csrf);
        headers.put("X-XSRF-TOKEN", csrf);
        headers.put("Cookie", "XSRF-TOKEN=" + csrf + ";JSESSIONID=" + webClient.getSessionId());
        headers.put("Content-Type", "application/json");
        Map<String, String> properties=null;
        try{
           properties = webClient.get(headers);
        }catch (IOException e){

        }
        return properties;
    }
    //отправляет сначала get запрос на сервер для получения csrf данных
    //далее формирует headers и body для дальнейшего взаимодействия с сервером через post
    //отправляет post c body и headers дабы проникнуть через защиту
    //c сервера получаем личность(identity) и возвращаем из метода эту личность (если она авторизована то главный identity она тоже присваивается)
    public Map<String, String> getheaders() {

            try {
                //отправка get запроса на сервер для получения csrf (токен)
                Map<String, String> properties = webClient.get();


                String csrf = properties.get("_csrf.token").trim();
                Log.d("csrf ", csrf);


                headers.put("_csrf", csrf);
                headers.put("X-XSRF-TOKEN", csrf);
                headers.put("Cookie", "XSRF-TOKEN=" + csrf + ";JSESSIONID=" + webClient.getSessionId());
                headers.put("Content-Type", "application/json");

            } catch (Exception e) {
                e.printStackTrace();
            }

        System.out.println(headers);
        return headers;
    }
    public SpringIdentity login(String username,String password){
        DataUserReg loginObj = new DataUserReg();
        loginObj.setPassword(password);
        loginObj.setUsername(username);
        loginObj.setEmail("");

        Gson gson=new Gson();

        String body = JSON.toJSONString(loginObj, SerializerFeature.BrowserCompatible);
        //отправка post запроса на сервер
        SpringIdentity identity=null;
            if(headers.size()==0){
                identity = webClient.post(loginObj, getheaders());
            }else {
                identity = webClient.post(loginObj, headers);
                if(identity==null){
                    System.out.println("repeat");
                    identity = webClient.post(loginObj, getheaders());
                }
            }


        if(identity!=null) {
            if (identity.getUser().isActive()) {
                this.identity = identity;
            }
        }



        return identity;
    }
    public SpringIdentity login(String token){


        //отправка post запроса на сервер
        System.out.println("TKN: "+token);
        SpringIdentity identity=null;
        if(headers.size()==0){
            identity = webClient.posttoken(token, getheaders());
        }else
            identity = webClient.posttoken(token, headers);

        if(identity!=null) {
            if (identity.getUser().isActive()) {
                this.identity = identity;
            }
        }

        return identity;
    }
    public SpringIdentity postSecured( DataUserReg dataUserReg) {
        Map<String, String> headers = new HashMap<>();

        String csrf = webClient.getToken();
        headers.put("_csrf", csrf);
        headers.put("X-XSRF-TOKEN", csrf);
        headers.put("Cookie", "XSRF-TOKEN=" + csrf + ";JSESSIONID=" + webClient.getSessionId());
        headers.put("Content-Type", "application/json");



        String body = JSON.toJSONString(dataUserReg, SerializerFeature.BrowserCompatible);
        Log.d("body",body);
        return webClient.post(dataUserReg, headers);
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public SpringIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(SpringIdentity identity) {
        this.identity = identity;
    }
}

