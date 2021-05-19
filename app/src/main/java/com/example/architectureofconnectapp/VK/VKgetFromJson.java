package com.example.architectureofconnectapp.VK;

import android.graphics.Bitmap;
import android.net.UrlQuerySanitizer;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.architectureofconnectapp.INetFromJson;
import com.example.architectureofconnectapp.MainActivity;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.LogRecord;
//каждый метод должен быть в другом потоке, чтобы не загружать основной
//json - ссылка на пост
public class VKgetFromJson implements INetFromJson {
    @Override
    public long id(JSONObject json) {
        long id=0;
        try {
            id=json.getInt("post_id");
        }catch(JSONException e){
            Log.e("Error","id");
        }
        return id;
    }

    @Override
    public long datatime(JSONObject json) {
        long datatime=0l;
        try {
            datatime = json.getLong("date");
        }catch (JSONException e){}

        return datatime;
    }

    @Override
    public String NameGroup(JSONObject json) {
        String name=" ";
        try {
            name = json.getString("source_id").substring(1);
        }catch (JSONException jsonException){
            Log.e("Error","NameGroup");
        }
        return name;
    }

    public ArrayList<Bitmap> Picture(JSONObject json){
        ArrayList<Bitmap> bitmaps=new ArrayList<>();
        ArrayList<String> bitmapstring=new ArrayList<>();

        //получаем массив ссылок на картинки
        try {
            if (json.has("attachments")) {
                JSONArray jsonarrayattachments = (JSONArray) json.get("attachments");
                for(int j=0;j<jsonarrayattachments.length();j++) { ;
                    JSONObject jsonObject2 = (JSONObject) jsonarrayattachments.get(j);
                    if(jsonObject2.getString("type").equals("photo")) {
                        JSONObject jsonObject3 = (JSONObject) jsonObject2.get("photo");
                        bitmapstring.add(jsonObject3.getString("photo_807"));
                    }
                }
            }
        }catch(JSONException jsonException){
            Log.e("Error","in Picture/start");
        }

        //получение картинок
                for(int i=0;i<bitmapstring.size();i++){
                    try {
                        bitmaps.add(Picasso.with(MainActivity.getInstance()).load(bitmapstring.get(i)).get());
                    }catch(IOException ioException){
                        Log.e("Error","Bitmap/transform");
                    }
                }
        return bitmaps;
    }
    public ArrayList<MediaStore.Video> Video(JSONObject json){
        return null;
    }
    public String Text(JSONObject json){
        String text=null;
         try {
             text = json.getString("text");
         }catch(JSONException jsonException){
             Log.e("ERROR", "in jsonText");
         }
        return text;
    }
    public ArrayList<MediaStore.Audio> Audio(JSONObject json){
        return null;
    }

    @Override
    public int like(JSONObject json) {
        int likes=0;
        try {
            likes = ((JSONObject)json.get("likes")).getInt("count");
        }catch (JSONException jsonException){
            Log.e("Error","IntLike");
        }
        return likes;
    }

    @Override
    public int repost(JSONObject json) {
        int reposts=0;
        try {
            reposts = ((JSONObject)json.get("reposts")).getInt("count");
        }catch (JSONException jsonException){
            Log.e("Error","Intreports");
        }
        return reposts;
    }

    @Override
    public int comments(JSONObject json) {
        int comments=0;
        try {
            comments = ((JSONObject)json.get("comments")).getInt("count");
        }catch (JSONException jsonException){
            Log.e("Error","IntComments");
        }
        return comments;
    }

    @Override
    public int views(JSONObject json) {
        int views=0;
        try {

            views = (json.getJSONObject("views")).getInt("count");
        }catch (JSONException jsonException){
            Log.e("Error","Intviews");
        }
        return views;
    }
}
