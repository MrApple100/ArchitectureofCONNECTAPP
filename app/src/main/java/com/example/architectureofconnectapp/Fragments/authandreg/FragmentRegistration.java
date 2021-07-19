package com.example.architectureofconnectapp.Fragments.authandreg;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.architectureofconnectapp.APIforServer.NetworkReg;
import com.example.architectureofconnectapp.Model.AuthAndReg.DataUserReg;
import com.example.architectureofconnectapp.R;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentRegistration extends Fragment {

    TextView errorusername;
    TextView errorpassworddigit;
    TextView errorpasswordsmall;
    TextView errorpasswordbig;
    TextView errorpasswordsimvol;
    TextView errorpasswordlength;
    TextView erroremail;

    private NetworkReg networkReg=NetworkReg.getInstance();
    private static FragmentRegistration instance;
    public static FragmentRegistration getInstance(){
        if (instance==null){
            instance=new FragmentRegistration();
        }

        return instance;
    }
    public FragmentRegistration(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registrationpage,container,false);
         errorusername=view.findViewById(R.id.errorusername);
         errorpassworddigit = view.findViewById(R.id.errorpassworddigit);
         errorpasswordsmall = view.findViewById(R.id.errorpasswordsmall);
         errorpasswordbig = view.findViewById(R.id.errorpasswordbig);
         errorpasswordsimvol = view.findViewById(R.id.errorpasswordsimvol);
         errorpasswordlength = view.findViewById(R.id.errorpasswordlength);

         erroremail = view.findViewById(R.id.erroremail);
        TextView toenterlp=view.findViewById(R.id.toenterlp);
        toenterlp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.torightexit,R.anim.toright);
                Fragmententerlp fragmententerlp = Fragmententerlp.getInstance();
                ft.hide(instance);
                if(!fragmententerlp.isAdded()){
                    ft.add(R.id.MainScene,fragmententerlp);
                }else{
                    ft.show(fragmententerlp);
                }
                ft.commit();
                ft.addToBackStack("toenterlp");
            }
        });



        DataUserReg dataUserReg=new DataUserReg("0","0","0");

        TextView login=view.findViewById(R.id.ETreglogin);
        TextView email=view.findViewById(R.id.ETregemail);
        TextView passwordone=view.findViewById(R.id.ETregpasswordone);

        login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                login.focusSearch(View.FOCUS_DOWN);
                dataUserReg.setUsername(login.getText()+"");



            }
        });
        login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkemail(email.getText().toString());
                checkpassword(passwordone.getText().toString());
            }
        });



        passwordone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                passwordone.focusSearch(View.FOCUS_DOWN);
                dataUserReg.setPassword(passwordone.getText()+"");

            }

        });
        passwordone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkusername(login.getText().toString());
                checkemail(email.getText().toString());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                email.focusSearch(View.FOCUS_DOWN);
                dataUserReg.setEmail(email.getText()+"");

            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkpassword(passwordone.getText().toString());
                checkusername(login.getText().toString());
            }
        });

        TextView AccessReg=view.findViewById(R.id.AccessReg);
        AccessReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataUserReg.setUsername(login.getText()+"");
                dataUserReg.setPassword(passwordone.getText()+"");
                dataUserReg.setEmail(email.getText()+"");
                    Handler handler2=new Handler(){
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            String error=(String)msg.obj;
                            if(error.equals("ok")){
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.setCustomAnimations(R.anim.torightexit,R.anim.toright);
                                Fragmententerlp fragmententerlp = Fragmententerlp.getInstance();
                                ft.hide(instance);
                                if(!fragmententerlp.isAdded()){
                                    ft.add(R.id.MainScene,fragmententerlp);
                                }else{
                                    ft.show(fragmententerlp);
                                }
                                ft.commit();
                            }
                        }
                    };
                    //вывод ошибок
                    Handler handler=new Handler(){
                        @Override
                        public void handleMessage(@NonNull Message msg) {
                            super.handleMessage(msg);
                            Map<String,String> errors=(Map<String,String>) msg.obj;
                            if (errors.containsKey("username")) {
                                errorusername.setText("Такой пользователь уже существует");
                                errorusername.setVisibility(View.VISIBLE);
                            }else {
                                if (errors.containsKey("usernameothersimvol")) {
                                    errorusername.setText("Такой логин не подойдет");
                                    errorusername.setVisibility(View.VISIBLE);
                                }
                                if(errors.containsKey("usernamelength")){
                                    errorusername.setText("Укажите более короткий вариант логина до 32 символов");
                                    errorusername.setVisibility(View.VISIBLE);
                                }
                            }

                            if(errors.containsKey("passworddigit")){
                                errorpassworddigit.setTextColor(getContext().getColor(R.color.red));
                            }
                            if(errors.containsKey("passwordbigletter")){
                                errorpasswordbig.setTextColor(getContext().getColor(R.color.red));
                            }
                            if(errors.containsKey("passwordsmallletter")){
                                errorpasswordsmall.setTextColor(getContext().getColor(R.color.red));
                            }
                            if(errors.containsKey("passwordothersimvol")){
                                errorpasswordsimvol.setTextColor(getContext().getColor(R.color.red));
                            }if(errors.containsKey("passwordlength")){
                                errorpasswordlength.setTextColor(getContext().getColor(R.color.red));
                            }
                            if (errors.containsKey("email")) {
                                erroremail.setText("Такая почта уже используется");
                                erroremail.setVisibility(View.VISIBLE);
                            }else if (errors.containsKey("emailcorrect")){
                                erroremail.setText("Неправильный формат почты");
                                erroremail.setVisibility(View.VISIBLE);
                            }
                            if (errors.size()==0){
                                networkReg.addUser(handler2,dataUserReg);
                            }
                        }
                    };

                    networkReg.check(handler,dataUserReg);


            }
        });
        return view;
    }

    public void checkusername(String username){
        if(username.equals("")){
            errorusername.setVisibility(View.GONE);
            return;
        }
        if(!username.matches("[\\w]+")){
            errorusername.setText("Такой логин не подойдет");
            errorusername.setVisibility(View.VISIBLE);
        }else if(username.length()>31){
            errorusername.setText("Укажите более короткий вариант логина до 32 символов");
            errorusername.setVisibility(View.VISIBLE);
        }else{
            errorusername.setVisibility(View.INVISIBLE);
        }
    }
    public void checkpassword(String password){
        if(password.equals("")){
            errorpassworddigit.setTextColor(getContext().getColor(R.color.transpwhite));
            errorpasswordsmall.setTextColor(getContext().getColor(R.color.transpwhite));
            errorpasswordbig.setTextColor(getContext().getColor(R.color.transpwhite));
            errorpasswordsimvol.setTextColor(getContext().getColor(R.color.transpwhite));
            errorpasswordlength.setTextColor(getContext().getColor(R.color.transpwhite));
            return;
        }
        if(!password.matches(".*\\d+.*")){
            errorpassworddigit.setTextColor(getContext().getColor(R.color.red));
        }else{
            errorpassworddigit.setTextColor(getContext().getColor(R.color.teal_200));
        }
        if(!password.matches(".*[A-Z]+.*")){
            errorpasswordbig.setTextColor(getContext().getColor(R.color.red));
        }else{
            errorpasswordbig.setTextColor(getContext().getColor(R.color.teal_200));
        }
        if(!password.matches(".*[a-z]+.*")){
            errorpasswordsmall.setTextColor(getContext().getColor(R.color.red));
        }else {
            errorpasswordsmall.setTextColor(getContext().getColor(R.color.teal_200));
        }
        if(!password.matches(".*[\\W]+.*")){
            errorpasswordsimvol.setTextColor(getContext().getColor(R.color.red));
        }else{
            errorpasswordsimvol.setTextColor(getContext().getColor(R.color.teal_200));
        }
        if(!(password.length()>=13)){
            errorpasswordlength.setTextColor(getContext().getColor(R.color.red));
        }else{
            errorpasswordlength.setTextColor(getContext().getColor(R.color.teal_200));
        }

    }
    public void checkemail(String email){
        if(email.equals("")){
            erroremail.setVisibility(View.INVISIBLE);
            return;
        }
        Pattern patternemail = Pattern.compile("\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}");
            Matcher matcher = patternemail.matcher(email);
            if (!matcher.matches()){
                erroremail.setText("Неправильный формат почты");
                erroremail.setVisibility(View.VISIBLE);
            }else{
                erroremail.setVisibility(View.INVISIBLE);
            }

    }
}
