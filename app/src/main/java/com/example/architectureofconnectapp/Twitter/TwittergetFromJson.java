package com.example.architectureofconnectapp.Twitter;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;

import com.example.architectureofconnectapp.INetFromJson;
import com.example.architectureofconnectapp.MainActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TwittergetFromJson  implements INetFromJson {
    @Override
    public long id(JSONObject json) {
        Long id=0l;
        try {
            id = json.getLong("id");
        }catch (JSONException e){ }
        return id;
    }

    @Override
    public long datatime(JSONObject json) {

        return 0;
    }

    @Override
    public String NameGroup(JSONObject json) {
        return null;
    }

    @Override
    public ArrayList<Bitmap> Picture(JSONObject json) {
        ArrayList<String> urls = new ArrayList<>();
        try {
            if(json.has("entities")) {
                JSONArray media = json.getJSONObject("entities").getJSONArray("media");
                for (int i = 0; i < media.length(); i++) {
                    if (media.getJSONObject(i).getString("type").equals("photo")) {
                        urls.add(media.getJSONObject(i).getString("media_url_https").replace("\\/","/"));
                        System.out.println("USRLL: "+urls.get(i));
                    }
                }
            }
        }catch (JSONException e){ }
        ArrayList<Bitmap> bitmaps=new ArrayList<>();
        for(int i=0;i<urls.size();i++){
            try {
                bitmaps.add(Picasso.with(MainActivity.getInstance()).load(urls.get(i)).get());
            }catch(IOException ioException){
                Log.e("Error","Bitmap/transform");
            }
        }
        return bitmaps;
    }

    @Override
    public ArrayList<MediaStore.Video> Video(JSONObject json) {
        return null;
    }

    @Override
    public String Text(JSONObject json) {
        String str="";
        try {
            str = json.getString("full_text");
        }catch (JSONException e){ }
        return str;
    }

    @Override
    public ArrayList<MediaStore.Audio> Audio(JSONObject json) {
        return null;
    }

    @Override
    public int like(JSONObject json) {
        int count=0;
        try {


            count = Integer.parseInt(json.getString("favorite_count"));
            System.out.println(count);
        }catch (JSONException e){}
        return count;
    }

    @Override
    public int repost(JSONObject json) {
        int count=0;

        try {
            count = Integer.parseInt(json.getString("retweet_count"));
            System.out.println(count);
        }catch (JSONException e){}
        return count;
    }

    @Override
    public int comments(JSONObject json) {
        return 0;
    }

    @Override
    public int views(JSONObject json) {
        return 0;
    }
}
