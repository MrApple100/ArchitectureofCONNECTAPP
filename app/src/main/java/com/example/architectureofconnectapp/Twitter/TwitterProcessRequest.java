package com.example.architectureofconnectapp.Twitter;

import android.content.Context;
import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.architectureofconnectapp.Cash.CreateTables.TableProfileConnectPost;
import com.example.architectureofconnectapp.Cash.Daos.DaoProfileConnectPost;
import com.example.architectureofconnectapp.ConnectThings.ConnectPost;
import com.example.architectureofconnectapp.IProcessNetRequest;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.CashConnectPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;
import twitter4j.UploadedMedia;
import twitter4j.json.DataObjectFactory;

public class TwitterProcessRequest implements IProcessNetRequest {

    TableProfileConnectPost dataConnectProfilefeed = (TableProfileConnectPost) TableProfileConnectPost.getInstance(MainActivity.getInstance(), "CashConnectProfilefeeds").allowMainThreadQueries().build();
    DaoProfileConnectPost daoProfileConnectPost = dataConnectProfilefeed.ProfileConnectPostDao();


    String next_fromtemp="0";
    int count=0;
    @Override
    public ArrayList<ConnectPost> makenextrequest(int count, String next_from,String method) {
        next_fromtemp=next_from;
        Twitter twitter=TwitterBASE.getinstance().getTwitter();
        ArrayList<ConnectPost> connectPosts=new ArrayList<>();
        TwitterPost twitterPost=null;
        try {
            System.out.println(twitter.getHomeTimeline().getRateLimitStatus().getLimit());
            System.out.println(twitter.getHomeTimeline().getRateLimitStatus().getSecondsUntilReset());
            Paging paging=TwitterBASE.getinstance().getPaging();
            if(next_from.compareTo("0")==0)
                paging.setCount(30);
            else {
                int iii=Integer.parseInt(next_fromtemp)+3;
                next_fromtemp=iii+"";
                paging.setCount(10);
            }

            paging.setPage(Integer.parseInt(next_fromtemp)+1);
            TwitterBASE.getinstance().setPaging(paging);

            next_fromtemp = (Integer.parseInt(next_fromtemp)+1)+"";
            /*
            if(next_fromtemp.compareTo("0")!=0)
                paging.setSinceId(Long.parseLong(next_fromtemp));

             */
            ArrayList<Status> statuses =null;

            if(method.equals("newsfeed")) {
                statuses = (ArrayList<Status>) twitter.getHomeTimeline(TwitterBASE.getinstance().getPaging());
            }else if(method.equals("userfeed")){
                statuses = (ArrayList<Status>) twitter.getUserTimeline(TwitterBASE.getinstance().getPaging());
            }
            Log.d("OKLMN", "wearehere");
            Log.d("OKLMN", statuses.size()+"");

                for (Status status : statuses) {
                    Date date = status.getCreatedAt();
                    long unixTime = (long) date.getTime() / 1000;

                    Log.d("UNIXTIME: ", date + "");
                    Log.d("UNIXTIME: ", unixTime + "");

                    JSONObject jsonbase = new JSONObject(TwitterObjectFactory.getRawJSON(status));
                    System.out.println(jsonbase.toString() + "");
                    //next_fromtemp=jsonbase.getString("id");
                    twitterPost = new TwitterPost(jsonbase);

                    twitterPost.setJsongroup(jsonbase.getJSONObject("user"));
                    twitterPost.setNameGroup(jsonbase.getJSONObject("user").getString("name"));
                    twitterPost.setDatatime(unixTime);
                    ConnectPost connectPost = new ConnectPost(twitterPost);
                    connectPosts.add(connectPost);
                }

        }catch(TwitterException | JSONException e) {

            try {
                Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
                field.setAccessible(true);
                field.set(null, 100 * 1024 * 1024);//100mb
            } catch (Exception ee) {

            }


            Log.d("OKLMN", "test2");
            ArrayList<CashConnectPost> cashConnectPosts =(ArrayList<CashConnectPost>) daoProfileConnectPost.getBynetworkandtype("Twitter".hashCode(),method.hashCode());
            Log.d("OKLMN", method.hashCode()+"");
            Log.d("OKLMN", cashConnectPosts.size()+"");
            if(cashConnectPosts.size()<=(Integer.parseInt(next_from)+count)) {
                for (int i = cashConnectPosts.size() - Integer.parseInt(next_from) - 1; i >= 0; i--) {
                    ConnectPost connectPost = new ConnectPost(cashConnectPosts.get(i));
                    connectPosts.add(connectPost);
                }
                next_fromtemp=cashConnectPosts.size()+"";
            }else {

                for (int i = cashConnectPosts.size() - Integer.parseInt(next_from) - 1; i >= cashConnectPosts.size() -  (Integer.parseInt(next_from)+count) ; i--) {
                    ConnectPost connectPost = new ConnectPost(cashConnectPosts.get(i));
                    connectPosts.add(connectPost);

                }
                next_fromtemp=(Integer.parseInt(next_from)+count)+"";
            }
            Log.d("OKLMN", connectPosts.size()+"");
            return connectPosts;


        }
        Log.e("onBind","Twitter"+connectPosts.size());
        return connectPosts;
    }
    public class MakeRequestThread extends Thread {
        public MakeRequestThread() {

        }

        @Override
        public void run() {
            super.run();

        }
    }
    @Override
    public String sentNext_from() {

        Log.e("sentNext",next_fromtemp);
        return next_fromtemp;
    }

    @Override
    public void SentPost(String text, ArrayList<Bitmap> bitmaps){
        String statusmessage=text;

        Twitter twitter=TwitterBASE.getinstance().getTwitter();
        SentPostThread sentPostThread=new SentPostThread(twitter,text,bitmaps);
        sentPostThread.start();
    }

    public class SentPostThread extends Thread{
        Twitter twitter;
        String text;
        ArrayList<Bitmap> bitmaps=new ArrayList<>();
        public SentPostThread(Twitter twitter,String text,ArrayList<Bitmap> arrayList) {
            this.twitter=twitter;
            this.text=text;
            this.bitmaps=arrayList;
        }

        @Override
        public void run() {
            super.run();
            try {

                StatusUpdate status=new StatusUpdate(text);
                if(bitmaps.size()>0) {
                    File file = bitmapToFile(MainActivity.getInstance(), bitmaps.get(0), "photo.png");
                    UploadedMedia media = null;
                    if(file.length() > 1000000){//did this because for files above 1mb you need to use chunked uploading
                        media = twitter.uploadMediaChunked(file.getName(), new BufferedInputStream(new FileInputStream(file)));
                    }else{
                        media = twitter.uploadMedia(file);
                    }
                    Log.d("Post",media.getSize()+"");
                    if(media != null) {
                        status.setMediaIds(media.getMediaId());//use this instead of tweet.setMedia(video);
                    }
                }

                twitter.updateStatus(status);
            }catch (TwitterException e){
                Log.d("EEE",e.toString());
            }catch (FileNotFoundException e){

            }
        }
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
}
