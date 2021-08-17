package com.example.architectureofconnectapp.APIforServer;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.architectureofconnectapp.Model.AuthAndReg.DataUserReg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by chen0 on 19/8/2017.
 */

public class OkHttpComponent implements WebClient {

    private OkHttpClient client;
    private String token;
    private String sessionId;
    private Retrofit retrofit=null;
    private APItoken api;

    public OkHttpComponent() {
        client = new OkHttpClient.Builder().build();

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
        this.api = retrofit.create(APItoken.class);
    }

    private static final String REQUEST_MEDIA_TYPE_JSON = "application/json; charset=utf-8";
    private static final String REQUEST_MEDIA_TYPE_TEXT = "text/plain; charset=utf-8";

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public OkHttpClient getClient() {
        return this.client;
    }
    //отправка пост запроса в теле передаем обеъект, в headers передаем csrf данные
    @Override
    public SpringIdentity post( DataUserReg body, Map<String, String> headers) {

        SpringIdentity resp = null;
        try {
            //получение ответа
            //Response response = client.newCall(request).execute();
            System.out.println(body.getEmail());
            System.out.println(body.getUsername());
            System.out.println(body.getPassword());
            System.out.println(api.getSpringIdentity(headers,body).request().header("_csrf"));
            System.out.println(headers);
            retrofit2.Response<SpringIdentity> response =api.getSpringIdentity(headers,body).execute();

            Log.d("request2response","jjj"+response.body());
            if(response.isSuccessful()) {
                Log.d("request2responseheader","jkj"+response.headers().get("Set-Cookie"));
                String header = response.headers().get("Set-Cookie");
                if(header != null) {
                    String[] parts = header.split(";");
                    for(String part : parts) {
                        String[] pair = part.split("=");
                        String pair_name = pair[0];
                        String pair_value = "";
                        if(pair.length > 1) {
                            pair_value = pair[1];
                        }
                        System.out.println(pair_name);
                        if(pair_name.equals("XSRF-TOKEN")) {
                            token = pair_value;
                        } else if(pair_name.equals("JSESSIONID")) {
                            sessionId = pair_value;
                        }
                    }
                }

                resp = response.body();

            }

        } catch (IOException e) {

        }

        return resp;
    }
    public SpringIdentity posttoken(String tkn, Map<String, String> headers) {

        SpringIdentity resp = null;
        try {
            //получение ответа
            System.out.println(tkn);
            System.out.println(headers.toString());
            retrofit2.Response<SpringIdentity> response =api.getSpringIdentitybytoken(headers,tkn).execute();

            Log.d("request2response","jjj"+response.body());
            if(response.isSuccessful()) {
                Log.d("request2responseheader","jkj"+response.headers().get("Set-Cookie"));
                String header = response.headers().get("Set-Cookie");
                if(header != null) {
                    String[] parts = header.split(";");
                    for(String part : parts) {
                        String[] pair = part.split("=");
                        String pair_name = pair[0];
                        String pair_value = "";
                        if(pair.length > 1) {
                            pair_value = pair[1];
                        }
                        System.out.println(pair_name);
                        if(pair_name.equals("XSRF-TOKEN")) {
                            token = pair_value;
                        } else if(pair_name.equals("JSESSIONID")) {
                            sessionId = pair_value;
                        }
                    }
                }

                resp = response.body();

            }

        } catch (IOException e) {

        }

        return resp;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }
    //метод добавляет пустой hashMap(играет роль headers дальше) и перенаправляет в другой метод.
    //Возвращает из другого метода тело ответа пример {"_csrf.token":"XXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX","_csrf.parameterName":"_csrf","_csrf.header":"X-XSRF-TOKEN"}
    @Override
    public Map<String, String> get() throws Exception {
        return get( new HashMap<String, String>());
    }

    @Override
    public String put(String url, String body, Map<String, String> headers) {

        String type = REQUEST_MEDIA_TYPE_TEXT;
        if(headers.containsKey("Content-Type") && headers.get("Content-Type").equalsIgnoreCase("application/json")){
            type = REQUEST_MEDIA_TYPE_JSON;
        } else if(headers.containsKey("content-type") && headers.get("content-type").equalsIgnoreCase("application/json")){
            type = REQUEST_MEDIA_TYPE_JSON;
        }

        MediaType textPlainMT = MediaType.parse(type);

        Request.Builder requestBuilder = new Request.Builder().url(url)
                .put(RequestBody.create(textPlainMT, body));

        for(Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder.build();

        String resp = null;
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                ResponseBody b = response.body();
                if(b != null) {
                    resp = b.string();
                }
            }
            response.close();
        } catch (IOException e) {
            resp = e.getMessage();
        }

        return resp;
    }

    @Override
    public String delete(String url, Map<String, String> headers) {

        Request.Builder requestBuilder = new Request.Builder()
                .url(url).delete();

        for(Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder
                .build();

        String resp = null;
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                ResponseBody b = response.body();
                if(b != null) {
                    resp = b.string();
                }
            }
            response.close();
        } catch (IOException e) {
            resp = e.getMessage();
        }

        return resp;
    }
    //метод получения токена(token), а метод возвращает тело ответа
    @Override
    public Map<String,String> get( Map<String, String> headers) throws IOException {
        Map<String,String>  resp = null;
        try {
           // Response response = client.newCall(request).execute();
            retrofit2.Response<Map<String, String>> response = api.getheaders(new HashMap<String, String>()).execute();
            //получение токена
            if(response.isSuccessful()) {
                Log.d("requestresponse",response.toString());
                Log.d("requestheaders",response.headers().get("Set-Cookie"));
                String header = response.headers().get("Set-Cookie");
                if(header != null) {
                    String[] parts = header.split(";");
                    for(String part : parts) {
                        String[] pair = part.split("=");
                        String pair_name = pair[0];
                        String pair_value = "";
                        Log.d("pair",pair_name);
                        if(pair.length > 1) {
                            Log.d("pair",pair_value);
                            pair_value = pair[1];
                        }
                        System.out.println(pair_name);
                        //токен в паре с XSRF-TOKEN
                        if(pair_name.equals("XSRF-TOKEN")) {
                            token = pair_value;
                        }else if(pair_name.equals("JSESSIONID")) {
                            sessionId = pair_value;
                        }
                    }
                }

                resp = response.body();
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resp;
    }

    @Override
    public SpringIdentity jsonPost(Map<String, String> data) {
        Map<String, String> headers = new HashMap<>();
        String body = JSON.toJSONString(data);
        Gson gson=new Gson();
        DataUserReg dataUserReg=gson.fromJson(body,DataUserReg.class);
        headers.put("Content-Type", "application/json");
        return post( dataUserReg, headers);
    }


}