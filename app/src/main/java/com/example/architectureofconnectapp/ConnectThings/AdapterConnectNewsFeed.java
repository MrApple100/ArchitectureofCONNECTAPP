package com.example.architectureofconnectapp.ConnectThings;

import android.database.CursorWindow;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureofconnectapp.Cash.CreateTables.TableProfileConnectPost;
import com.example.architectureofconnectapp.Cash.Daos.DaoProfileConnectPost;
import com.example.architectureofconnectapp.IPostfromNet;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.CashConnectPost;
import com.example.architectureofconnectapp.R;
import com.example.architectureofconnectapp.VK.VKInterection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdapterConnectNewsFeed extends PagedListAdapter<ConnectPost, AdapterConnectNewsFeed.ConnectPostViewHolder> {


    TableProfileConnectPost dataConnectProfilefeed = (TableProfileConnectPost) TableProfileConnectPost.getInstance(MainActivity.getInstance(), "ConnectProfilefeed").allowMainThreadQueries().build();
    DaoProfileConnectPost daoProfileConnectPost = dataConnectProfilefeed.ProfileConnectPostDao();

    public AdapterConnectNewsFeed(@NonNull DiffUtil.ItemCallback<ConnectPost> diffCallback) {
        super(diffCallback);
    }
    @NonNull
    @Override
    public AdapterConnectNewsFeed.ConnectPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onepost,parent,false);

        return new AdapterConnectNewsFeed.ConnectPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder( AdapterConnectNewsFeed.ConnectPostViewHolder holder, int position) {
        IPostfromNet connectPost= getItem(position).getPostElements();
        SettingView settingView=getItem(position).getSettingView();
        holder.Namesnetgroup.setText(connectPost.getNameNet()+"/"+connectPost.getNameGroup());
        System.out.println(settingView.getText());

            if (settingView.getText()) {
                holder.Text.setVisibility(View.VISIBLE);
                holder.Text.setText(connectPost.getText());
            } else {
                holder.Text.setVisibility(View.GONE);
            }
        
        CashConnectPost cashConnectPost = new CashConnectPost((int)connectPost.getId());
        cashConnectPost.setNetwork(connectPost.getNameNet().hashCode());
        cashConnectPost.setText(connectPost.getText());
        cashConnectPost.setPhoto("null");

        Handler handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                holder.Picture.setAdapter(new AdapterPhotoPlaces(MainActivity.getInstance(),(ArrayList<Bitmap>)msg.obj));
                ((AdapterPhotoPlaces) holder.Picture.getAdapter()).notifyDataSetChanged();

                ArrayList<Bitmap> bitmaps =(ArrayList<Bitmap>) msg.obj;
                List<byte[]> bytes=new ArrayList<>();
                for(Bitmap bitmap:bitmaps){
                    bytes.add(bitmaptobytes(bitmap));
                }
                Gson gson=new Gson();
                cashConnectPost.setPhoto(gson.toJson(bytes));
                Log.d("OKLMN",cashConnectPost.getId()+" ");
                Log.d("OKLMN",cashConnectPost.getPhoto()+" ");
                daoProfileConnectPost.insert(cashConnectPost);
                Log.d("OKLMN",daoProfileConnectPost.getByid((int)connectPost.getId()).getId()+"");
                Log.d("OKLMN","upfate");
            }
        };

try {
    Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
    field.setAccessible(true);
    field.set(null,100*1024*1024);//100mb
}catch (Exception e){

}


        if(daoProfileConnectPost.getByid((int)connectPost.getId())==null) {
            Log.d("OKLMN","null2");
            if (connectPost.getPicture(handler).size() != 0) {
                holder.Picture.setVisibility(View.VISIBLE);
                holder.Picture.setAdapter(new AdapterPhotoPlaces(MainActivity.getInstance(), new ArrayList<Bitmap>(Arrays.asList(BitmapFactory.decodeResource(MainActivity.getInstance().getResources(), R.drawable.empty)))));
            } else {
                holder.Picture.setVisibility(View.GONE);
            }
        }else{
            if(!daoProfileConnectPost.getByid((int)connectPost.getId()).getPhoto().equals("null")) {
                holder.Picture.setVisibility(View.VISIBLE);
                CashConnectPost cashConnectPost1 = daoProfileConnectPost.getByid((int) connectPost.getId());
                Log.d("OKLMN", "cashConnectPost1.getText()");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<byte[]>>() {
                }.getType();
                ArrayList<byte[]> bytes = gson.fromJson(cashConnectPost1.getPhoto(), type);
                ArrayList<Bitmap> bitmaps = new ArrayList<>();
                for (byte[] b : bytes) {
                    bitmaps.add(bytestobitmap(b));
                }
                holder.Picture.setAdapter(new AdapterPhotoPlaces(MainActivity.getInstance(), bitmaps));
            }else{
                holder.Picture.setVisibility(View.GONE);
            }
        }
        if(settingView.getLike()){
            holder.like.setText(connectPost.getLike()+" likes");
            holder.like.setTag(connectPost.getId());
            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new VKInterection().like(connectPost.getNetPostJsonInfo(),(long)holder.like.getTag());
                    System.out.println(holder.like.getTag());
                }
            });
        }else{
            holder.like.setText("no likes");
            holder.like.setTag(connectPost.getId());
        }
        if(settingView.getRepost() ) {
            holder.repost.setText(connectPost.getRepost() + " reposts");
        }else{
            holder.repost.setText("no reposts");
        }
        if(settingView.getComment()){
            holder.comment.setText(connectPost.getComments()+" comments");
        }else{
            holder.comment.setText("no comments");
        }
        if(settingView.getViews()) {
            holder.views.setVisibility(View.VISIBLE);
            holder.views.setText(connectPost.getViews() + "");
        }
    }
    public static class ConnectPostViewHolder  extends RecyclerView.ViewHolder {
        final TextView Text,views,Namesnetgroup;
       // final MediaStore.Audio Audio;
        final VideoView Video;
        final RecyclerView Picture;
        final TextView like,repost,comment;

        ConnectPostViewHolder (View view){
            super(view);
            Namesnetgroup = (TextView) view.findViewById(R.id.namenet_nameqroup);
            Text = (TextView) view.findViewById(R.id.Text);
            Video = (VideoView) view.findViewById(R.id.Video);
            Picture = (RecyclerView) view.findViewById(R.id.Picture);
            like = (TextView) view.findViewById(R.id.like);
            views = (TextView) view.findViewById(R.id.views);
            repost = (TextView) view.findViewById(R.id.repost);
            comment = (TextView) view.findViewById(R.id.comment);

        }
    }

    private byte[] bitmaptobytes(Bitmap bitmap){
        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos); // YOU can also save it in JPEG
        byte[] bitmapdata = bos.toByteArray();
        return  bitmapdata;
    }
    private Bitmap bytestobitmap(byte[] bytes){
        //Convert byte array to bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
        return bitmap;
    }
}
