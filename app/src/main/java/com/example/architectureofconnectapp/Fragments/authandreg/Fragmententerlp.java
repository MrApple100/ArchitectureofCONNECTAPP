package com.example.architectureofconnectapp.Fragments.authandreg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.architectureofconnectapp.APIforServer.SpringBootClient;
import com.example.architectureofconnectapp.APIforServer.SpringIdentity;
import com.example.architectureofconnectapp.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Fragmententerlp extends Fragment {
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
                /*
                Intent intent=new Intent(getContext(),ServiceAuthetication.class);
                intent.putExtra("LOGIN",login.getText()+"");
                intent.putExtra("PASSWORD",password.getText()+"");
                getContext().startService(intent);

                 */
                Handler handler=new Handler(){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        TextView error=enterlppage.findViewById(R.id.error);
                        error.setText("");
                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SpringIdentity identity = new SpringBootClient().login(login.getText()+"",password.getText()+"");
                        Message msg=new Message();
                        if(identity!=null) {
                            System.out.println(identity.getUsername());
                            System.out.println(identity.getTokenInfo());
                            System.out.println(identity.isAuthenticated());
                            msg.obj="";
                        }else{
                            msg.obj="Неправильный логин или пароль";
                        }
                        handler.sendMessage(msg);
                    }
                }).start();

            }
        });

        return enterlppage;
    }

    protected BroadcastReceiver broadcastReceiverAuth = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra(ServiceAuthetication.PERMISSION).equals("AUTH")){
                Gson gson=new Gson();
                SpringIdentity identity = gson.fromJson(intent.getStringExtra(ServiceAuthetication.IDENTITY),SpringIdentity.class);

            }
        }
    };
}
