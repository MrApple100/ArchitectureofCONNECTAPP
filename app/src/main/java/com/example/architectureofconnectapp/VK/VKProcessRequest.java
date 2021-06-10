package com.example.architectureofconnectapp.VK;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.architectureofconnectapp.ConnectThings.ConnectPost;
import com.example.architectureofconnectapp.IProcessNetRequest;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.MultipartUtility;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.httpClient.VKHttpClient;
import com.vk.sdk.api.httpClient.VKMultipartEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VKProcessRequest implements IProcessNetRequest {
    String next_fromtemp="0";
    Handler handler;
    String Owner_id;
    String id;
    @Override
    public ArrayList<ConnectPost> makenextrequest(int count, String next_from,String method) {
        checkNotMain();
        ArrayList<ConnectPost> connectPosts=new ArrayList<>();
        VKRequest vkRequest =null;
        if(method.equals("newsfeed")) {
            vkRequest = new VKRequest("newsfeed.get", VKParameters.from(VKApiConst.COUNT, count, "start_from", next_from, VKApiConst.FILTERS, "post,photos,notes,friends"));
        }else if(method.equals("userfeed")){
            vkRequest = new VKRequest("newsfeed.get", VKParameters.from(VKApiConst.COUNT, count, "start_from", next_from, VKApiConst.FILTERS, "post,photos,notes,friends"));
        }vkRequest.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                //получаем Connect посты
                try {
                    JSONObject jsonresponce = (JSONObject) response.json.get("response");
                    next_fromtemp=jsonresponce.getString("next_from");
                    JSONArray jsonitems = (JSONArray) jsonresponce.get("items");
                    JSONArray jsongroups = (JSONArray) jsonresponce.get("groups");
                    for (int i = 0; i < jsonitems.length(); i++) {
                        JSONObject jsonitem = (JSONObject) jsonitems.get(i);
                        VKPost vkPost=new VKPost(jsonitem);
                        String idgroup = jsonitem.getString("source_id").substring(1);
                        for(int j=0;j<jsongroups.length();j++) {
                            JSONObject jsongroup=(JSONObject) jsongroups.get(j);
                            if(jsongroup.getString("id").compareTo(idgroup)==0){
                                vkPost.setNameGroup(jsongroup.getString("name"));
                                vkPost.setJsongroup(jsongroup);
                            }
                        }
                        ConnectPost connectPost=new ConnectPost(vkPost);
                        connectPosts.add(connectPost);
                    }
                }catch (JSONException jsonException){
                    Log.e("ERROR","VKProcessRequest");
                }
            }
        });

        return connectPosts;
    }

    public void SentPost(String text, ArrayList<Bitmap> bitmaps){
        final URL[] url = new URL[1];
        VKRequest vkIDServer =new VKRequest("photos.getWallUploadServer");
        vkIDServer.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                //получаем id сервера
                try {
                    JSONObject jsonresponce = (JSONObject) response.json.get("response");
                    url[0] =new URL(jsonresponce.getString("upload_url"));
                } catch (JSONException | MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        File imagefile =null;
    handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.obj != null){
                ArrayList<String> strjson = (ArrayList<String>) msg.obj;
                JSONObject jsonresponse=null;
                try {
                    jsonresponse = new JSONObject(strjson.get(0));

                    VKRequest vkphotoRequest =new VKRequest("photos.saveWallPhoto",VKParameters.from("server",jsonresponse.getString("server"),"photo",jsonresponse.getString("photo"),"hash",jsonresponse.getString("hash")));
                    vkphotoRequest.executeSyncWithListener((new VKRequest.VKRequestListener() {
                        @Override
                        public void onComplete(VKResponse response) {
                            super.onComplete(response);
                            String Oid;
                            String jid;
                            try {
                                JSONArray jsonresponce = (JSONArray) response.json.get("response");

                                Owner_id = jsonresponce.getJSONObject(0).getString("owner_id");
                                id = jsonresponce.getJSONObject(0).getString("id");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(VKError error) {
                            super.onError(error);

                        }
                    }));
                    VKRequest vkRequest =new VKRequest("wall.post",VKParameters.from("message",text,"attachments","photo"+Owner_id+"_"+id));

                    vkRequest.executeSyncWithListener(new VKRequest.VKRequestListener() {
                        @Override
                        public void onComplete(VKResponse response) {
                            super.onComplete(response);
                            //получаем id поста
                            try {
                                JSONObject jsonresponce = (JSONObject) response.json.get("response");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException jsonException) {
                jsonException.printStackTrace();
                }
            }else{
                VKRequest vkRequest =new VKRequest("wall.post",VKParameters.from("message",text));

                vkRequest.executeSyncWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        //получаем id поста
                        try {
                            JSONObject jsonresponce = (JSONObject) response.json.get("response");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    };
        if(bitmaps.size()>0) {
            imagefile = bitmapToFile(MainActivity.getInstance().getApplicationContext(), bitmaps.get(0), "someimage.png");
        }
        SentThread sentThread=new SentThread(url[0],imagefile);
        sentThread.start();

    }

    public String sentNext_from() {
        return next_fromtemp;
    }

    static void checkNotMain() {
        if (isMain()) {
            throw new IllegalStateException("VKProcessRequest/Method call should not happen from the main thread.");
        }
    }


    static boolean isMain() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
    public static File bitmapToFile(Context context, Bitmap bitmap, String fileNameToSave) { // File name like "image.png"
        //create a file to write bitmap data
        File file = null;
        try {
            file = new File(context.getDataDir() ,"/" +fileNameToSave);
           if (!file.exists()) {

                file.createNewFile();
            }

//Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();


//write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);

            fos.flush();
            fos.close();
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return file; // it will return null
        }
    }
    class SentThread extends Thread{
        URL url;
        File uploadFile1;
        String charset = "UTF-8";
        public SentThread(URL url,File uploadFile1) {
            this.uploadFile1=uploadFile1;
            this.url=url;
        }

        @Override
        public void run() {
            super.run();
            MultipartUtility multipart = null;
            List<String> response = null;
            if(uploadFile1!=null) {
                try {
                    multipart = new MultipartUtility(url, charset);

                    multipart.addFilePart("photo", uploadFile1);
                    //multipart.addFilePart("fileUpload", uploadFile2);

                    response = multipart.finish();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            Message msg = new Message();
            msg.obj = response;
            handler.sendMessage(msg);

        }


    }
}



