package com.example.architectureofconnectapp.APIforServer;

import android.net.SSLCertificateSocketFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.architectureofconnectapp.BuildConfig;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.AuthAndReg.DataUserReg;
import com.example.architectureofconnectapp.Model.AuthAndReg.RegResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;

public class NetworkReg {
    Retrofit retrofit;
    APIRegistration api;
    SpringBootClient client;
    Map<String, String> headers = new HashMap<>();
    private static NetworkReg instance;
    public static NetworkReg getInstance() {
        if (instance == null) {
            instance = new NetworkReg();
        }
        return instance;
    }
    private NetworkReg(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging =new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        OkHttpClient okHttpClient = builder.build();
        Gson gson=new GsonBuilder()
                .setLenient()
                .create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.7:8080")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        client = new SpringBootClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (client) {


                    headers = client.getheaders();

                    String csrf = client.getWebClient().getToken();
                    headers.put("_csrf", csrf);
                    headers.put("X-XSRF-TOKEN", csrf);
                    headers.put("Cookie", "XSRF-TOKEN=" + csrf + ";JSESSIONID=" + client.getWebClient().getSessionId());
                    headers.put("Content-Type", "application/json");
                }
            }
        }).start();




        this.api = retrofit.create(APIRegistration.class);
    }

    public void toserver(DataUserReg dataUserReg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (client) {
                    //System.out.println(identity.getTokenInfo());

                    // System.out.println(JSON.toJSONString(identity, SerializerFeature.PrettyFormat));
                    System.out.println("/////////////////////////////////");
                    System.out.println(client.postSecured(dataUserReg));
                    System.out.println("/////////////////////////////////");
                }

            }
        }).start();

    }
    public void check(Handler handler,DataUserReg dataUserReg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (client) {
                    Map<String, String> headers = new HashMap<>();

                    String csrf = client.getWebClient().getToken();
                    headers.put("_csrf", csrf);
                    headers.put("X-XSRF-TOKEN", csrf);
                    headers.put("Cookie", "XSRF-TOKEN=" + csrf + ";JSESSIONID=" + client.getWebClient().getSessionId());
                    headers.put("Content-Type", "application/json");
                    try {
                      Response<Map<String,String>> response =  api.check(headers, dataUserReg).execute();
                      Message message=new Message();
                      message.obj=response.body();
                      handler.sendMessage(message);
                    }catch (IOException e){

                    }
                }
            }
        }).start();

    }
    public void addUser(Handler handler,DataUserReg dataUserReg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (client) {
                    Map<String, String> headers = new HashMap<>();

                    String csrf = client.getWebClient().getToken();
                    headers.put("_csrf", csrf);
                    headers.put("X-XSRF-TOKEN", csrf);
                    headers.put("Cookie", "XSRF-TOKEN=" + csrf + ";JSESSIONID=" + client.getWebClient().getSessionId());
                    headers.put("Content-Type", "application/json");
                    Gson gson=new Gson();
                    System.out.println("Ymmy"+gson.toJson(headers));

                    try {
                       Response<String> regResponse = api.addUser(headers,dataUserReg).execute();
                        if(regResponse.isSuccessful()){
                            Message message=new Message();
                            message.obj=regResponse.body();
                            handler.sendMessage(message);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
            }
        }).start();


    }
    public void addUser() {

        api.addUser().enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Toast.makeText(MainActivity.getInstance(),"ok3: "+response+"",Toast.LENGTH_LONG).show();
                Log.d("TTT3",response.body()+"");

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(MainActivity.getInstance(),"bad: "+t+"",Toast.LENGTH_LONG).show();

            }
        });

    }
}

