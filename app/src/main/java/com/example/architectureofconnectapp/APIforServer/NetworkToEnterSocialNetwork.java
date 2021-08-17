package com.example.architectureofconnectapp.APIforServer;

import android.util.Log;
import android.widget.Toast;

import com.example.architectureofconnectapp.BuildConfig;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.EntertoSocialNetwork.RequestEntertoSocialNetwork;
import com.example.architectureofconnectapp.Model.SocialNetworks;
import com.example.architectureofconnectapp.Model.Token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkToEnterSocialNetwork {
    Retrofit retrofit;
    APItoEnterSocialNetwork api;
    SpringBootClient client=SpringBootClient.getInstance();

    private static NetworkToEnterSocialNetwork instance;
    public static NetworkToEnterSocialNetwork getInstance() {
        if (instance == null) {
            instance = new NetworkToEnterSocialNetwork();
        }
        return instance;
    }
    public NetworkToEnterSocialNetwork(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder.build();
        Gson gson=new GsonBuilder()
                .setLenient()
                .create();
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.155:8080")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();


        this.api = retrofit.create(APItoEnterSocialNetwork.class);
    }

    public void Enter(String nameNet,String accesstokenofanothersn) {
        Log.d("TTT2",client.getheaders()+" "+nameNet);
        RequestEntertoSocialNetwork requestEntertoSocialNetwork=new RequestEntertoSocialNetwork(client.getIdentity().getJwt(),nameNet,accesstokenofanothersn);
        api.Enter(client.getheaders(),requestEntertoSocialNetwork).enqueue(new Callback<SpringIdentity>() {
            @Override
            public void onResponse(Call<SpringIdentity> call, Response<SpringIdentity> response) {
                Toast.makeText(MainActivity.getInstance(),"ok3: "+response+"",Toast.LENGTH_LONG).show();
                SpringIdentity identity= response.body();
                SpringBootClient.getInstance().setIdentity(identity);
                SocialNetworks socialNetworks=SocialNetworks.getInstance();
                for (Token tkn : identity.getUser().getTokens()) {
                    socialNetworks.addSocialNetwork(tkn.getNetworkname(), tkn.getToken());
                }

                Log.d("TTT2",response.body()+"");
            }
            @Override
            public void onFailure(Call<SpringIdentity> call, Throwable t) {
                //сделать обработку всех ошибок и исключений в одном файле
                //в частности сделать обработку старого токена
                //код можно взять из mainactivity
                Toast.makeText(MainActivity.getInstance(),"bad: "+t+"",Toast.LENGTH_LONG).show();
            }
        });
    }
}
