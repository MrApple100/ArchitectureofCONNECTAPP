package com.example.architectureofconnectapp.Fragments.authandreg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.architectureofconnectapp.APIforServer.SpringBootClient;
import com.example.architectureofconnectapp.APIforServer.SpringIdentity;
import com.example.architectureofconnectapp.Cash.CreateTables.TableConnectUser;
import com.example.architectureofconnectapp.Cash.CreateTables.TableSocialNetwork;
import com.example.architectureofconnectapp.Cash.Daos.DaoConnectUser;
import com.example.architectureofconnectapp.Cash.Daos.DaoSocialNetwork;
import com.example.architectureofconnectapp.ConnectThings.ConnectUser;
import com.example.architectureofconnectapp.Fragments.FragmentConnectNews;
import com.example.architectureofconnectapp.Fragments.FragmentNavigationPanel;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.CashConnectUser;
import com.example.architectureofconnectapp.Model.SocialNetworks;
import com.example.architectureofconnectapp.Model.Token;
import com.example.architectureofconnectapp.Model.User;
import com.example.architectureofconnectapp.Model.Users;
import com.example.architectureofconnectapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Fragmententerlp extends Fragment {


    //BD for cash Networks
    TableConnectUser tableConnectUser = (TableConnectUser) TableConnectUser.getInstance(MainActivity.getInstance(), "connectuser").allowMainThreadQueries().build();
    DaoConnectUser daoConnectUser = tableConnectUser.ConnectUserDao();

    private static Fragmententerlp instance;
    public static Fragmententerlp getInstance(){
        if (instance==null){
            instance=new Fragmententerlp();
        }

        return instance;
    }
    public Fragmententerlp() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View enterlppage =inflater.inflate(R.layout.enterlppage,container,false);

        TextView auththrouthnws= enterlppage.findViewById(R.id.butauththrouthnws);
        auththrouthnws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.totop,R.anim.totopexit);
                Fragmententernw fragmententernw = Fragmententernw.getInstance();
                ft.hide(instance);
                if(!fragmententernw.isAdded()){
                    ft.add(R.id.MainScene,fragmententernw);
                }else{
                    ft.show(fragmententernw);
                }
                ft.commit();
                ft.addToBackStack("toenternw");

            }
        });

        TextView toRegistration = enterlppage.findViewById(R.id.toRegistration);
        toRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.toleft,R.anim.toleftexit);
                FragmentRegistration fragmentRegistration = FragmentRegistration.getInstance();
                ft.hide(instance);
                if(!fragmentRegistration.isAdded()){
                    ft.add(R.id.MainScene,fragmentRegistration);
                }else{
                    ft.show(fragmentRegistration);
                }
                ft.commit();
                ft.addToBackStack("toRegistration");
            }
        });
        EditText login=enterlppage.findViewById(R.id.ETlogin);
        EditText password=enterlppage.findViewById(R.id.ETpassword);

        TextView butlogin = enterlppage.findViewById(R.id.butEnter);
        butlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handlererror=new Handler(){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        TextView error=enterlppage.findViewById(R.id.error);
                        error.setText(msg.obj.toString());
                    }
                };
                Handler handlergood=new Handler(){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        vhod(((SpringIdentity)msg.obj).getUser());
                        CashConnectUser cashConnectUser=new CashConnectUser(((SpringIdentity)msg.obj).getUser());
                        cashConnectUser.setConnecttoken(((SpringIdentity)msg.obj).getJwt());
                        cashConnectUser.setConnectrefreshtoken(((SpringIdentity)msg.obj).getRefreshtoken());
                        saveconnectuserincash(cashConnectUser);
                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SpringIdentity identity = SpringBootClient.getInstance().login(login.getText()+"",password.getText()+"");
                        Message msg=new Message();
                        if(identity!=null) {
                            //добавление соцсетей для показа в newsfeed
                            //хранятся соцсети в токене в виде имени соцсети и токена(позже нужно хранить токен только на сервере)
                            SocialNetworks socialNetworks=SocialNetworks.getInstance();
                            Log.d("tkn","tokenswork");
                            for (Token tkn : identity.getUser().getTokens()) {
                                Log.d("tkn",tkn.getNetworkname()+"/"+tkn.getToken());
                                socialNetworks.addSocialNetwork(tkn.getNetworkname(), tkn.getToken());
                            }
                            System.out.println(identity.getUser());
                            System.out.println(identity.getTokenInfo());
                            msg.obj="";
                            Message msg2=new Message();
                            msg2.obj=identity;
                            handlergood.sendMessage(msg2);
                        }else{
                            msg.obj="Неправильный логин или пароль";
                        }
                        handlererror.sendMessage(msg);

                    }
                }).start();

            }
        });

        return enterlppage;
    }


    private void vhod(ConnectUser connectuser){
        SocialNetworks socialNetworks=SocialNetworks.getInstance();
        for(Token tkn:connectuser.getTokens()){
            socialNetworks.addSocialNetwork(tkn.getNetworkname(),tkn.getToken());
        }
        FragmentConnectNews ConnectNews = FragmentConnectNews.getInstance();
        FragmentNavigationPanel NavigationPanel = FragmentNavigationPanel.getInstance();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.MainScene,ConnectNews);
        ft.commit();
        ft = fm.beginTransaction();
        ft.add(R.id.Nav_footer,NavigationPanel);
        ft.commit();
    }
    private void saveconnectuserincash(CashConnectUser cashconnectUser){
        if(daoConnectUser.getByid(cashconnectUser.getId())==null){
            System.out.println("boomshakalaka");
            daoConnectUser.insert(cashconnectUser);
        }else{
            daoConnectUser.update(cashconnectUser);
        }
    }
}
