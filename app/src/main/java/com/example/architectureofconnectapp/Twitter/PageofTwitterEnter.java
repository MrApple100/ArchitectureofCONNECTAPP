package com.example.architectureofconnectapp.Twitter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.architectureofconnectapp.Fragments.FragmentExit;
import com.example.architectureofconnectapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class PageofTwitterEnter extends FragmentActivity {
    static private PageofTwitterEnter pageofTwitterEnter;
    static private Activity activity;

    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

    private  static final String TWITTER_CALLBACK_URL ="oauth://t4jsample";
    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";

    private static RequestToken requestToken;
    private AccessToken accessToken;

    TwitterBASE twitterBASE ;
    static final Integer COUNT_VALIDATE_DIGIT = 7;
    private ArrayList<EditText> editTexts = new ArrayList<>();
    private static String validatekey;


    ConfigurationBuilder cb;

    static public PageofTwitterEnter getInstanse(){
        return pageofTwitterEnter;
    }
    static public Activity getActivity(){
        return activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pageofvalidateenter);
        pageofTwitterEnter=this;
        activity=this;
        createview(COUNT_VALIDATE_DIGIT);
        twitterBASE = TwitterBASE.getinstance();
        System.out.println("KOKOKOKOKO3");
        Subscription subscription = Observable.create(new Observable.OnSubscribe<TwitterBASE>() {
            @Override
            public void call(Subscriber<? super TwitterBASE> subscriber) {
                subscriber.onNext(twitterBASE.setuprequestToken());
                subscriber.onCompleted();
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends TwitterBASE>>() {
            @Override
            public Observable<? extends TwitterBASE> call(Throwable throwable) {
                Log.d("logintwitter",throwable.getMessage());
                return Observable.just(twitterBASE);
            }
        }).map(new Func1<TwitterBASE, RequestToken>() {
            @Override
            public RequestToken call(TwitterBASE twitterBASE) {
                RequestToken requestToken=TwitterBASE.getRequestToken();

                return requestToken;
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RequestToken>() {
                    @Override
                    public void call(RequestToken requestToken) {

                    }
                });
        System.out.println("KOKOKOKOKO");
    }

    private void createview(Integer kolpanel){

        LinearLayout LLvalidate = findViewById(R.id.LLofvalidatepanels);

        final float scale = getResources().getDisplayMetrics().density;
        System.out.println("scale"+scale);
        for(int i=0;i<kolpanel;i++){
            editTexts.add(new EditText(this));
            int finalI = i;
            editTexts.get(i).setLayoutParams(new ViewGroup.LayoutParams((int)(40*scale),(int)(80*scale)));
            editTexts.get(i).setTextSize((20*scale));
            editTexts.get(i).setSingleLine();
            editTexts.get(i).setEms(1);
            editTexts.get(i).setInputType(InputType.TYPE_CLASS_NUMBER);
            editTexts.get(i).setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
            editTexts.get(i).setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v,boolean hasFocus) {
                    if(hasFocus) {
                        if (((EditText) v).getText().length() == 0) {
                            if (finalI > 0) {
                                editTexts.get(finalI - 1).requestFocus();
                                editTexts.get(finalI - 1).setSelection(editTexts.get(finalI - 1).length());
                                InputMethodManager keyboard = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                keyboard.showSoftInput(editTexts.get(finalI - 1), 0);
                            }
                        }
                    }
                }
            });
            editTexts.get(i).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length()==0) {
                        if(finalI>0){
                            editTexts.get(finalI-1).requestFocus();
                            editTexts.get(finalI-1).setSelection(editTexts.get(finalI-1).length());
                        }
                    }
                    if(s.length()==2) {
                        if (finalI < editTexts.size() - 1) {
                            String dig = editTexts.get(finalI).getText().toString().substring(1);
                            editTexts.get(finalI).setText(editTexts.get(finalI).getText().toString().substring(0,1));
                            editTexts.get(finalI+1).setText(dig);
                            editTexts.get(finalI+1).requestFocus();
                            editTexts.get(finalI+1).setSelection(editTexts.get(finalI+1).length());
                        }else{
                            editTexts.get(finalI).setText(editTexts.get(finalI).getText().toString().substring(0,1));
                        }
                    }
                    System.out.println(s);
                    if(s.length()==1){
                        if(finalI >=editTexts.size()-1) {
                            getStringfromEditTexts();
                            Subscription subscription=Observable.create(new Observable.OnSubscribe<TwitterBASE>() {
                                @Override
                                public void call(Subscriber<? super TwitterBASE> subscriber) {
                                    subscriber.onNext(twitterBASE.login(validatekey));
                                    subscriber.onCompleted();
                                }
                            }).onErrorResumeNext(new Func1<Throwable, Observable<? extends TwitterBASE>>() {
                                @Override
                                public Observable<? extends TwitterBASE> call(Throwable throwable) {
                                    System.out.println("Error twitterbase");
                                    return Observable.just(twitterBASE);
                                }
                            }).map(new Func1<TwitterBASE, Twitter>() {
                                @Override
                                public Twitter call(TwitterBASE twitterBASE) {
                                    return twitterBASE.getTwitter();
                                }
                            }).subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Action1<Twitter>() {
                                        @Override
                                        public void call(Twitter twitter) {
                                            Intent data =new Intent();
                                            setResult(-1,data);
                                            PageofTwitterEnter.this.finish();
                                        }
                                    });
                        }
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            LLvalidate.addView(editTexts.get(i),i);

        }
    }
    private void getStringfromEditTexts(){
        StringBuilder key = new StringBuilder();
        for(EditText editText:editTexts){
            key.append(editText.getText());
            System.out.println(editText.getText());
        }
        validatekey = key.toString();
    }

}
