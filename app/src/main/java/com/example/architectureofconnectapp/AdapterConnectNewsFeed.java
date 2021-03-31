package com.example.architectureofconnectapp;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterConnectNewsFeed extends PagedListAdapter<ConnectPost, AdapterConnectNewsFeed.ConnectPostViewHolder> {


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
        if(settingView.getText()){
            holder.Text.setVisibility(View.VISIBLE);
            holder.Text.setText(connectPost.getText());
        }
        if(connectPost.Picture.size()!=0) {
            holder.Picture.setVisibility(View.VISIBLE);
            System.out.println(connectPost.getPicture().get(0));
            holder.Picture.setImageBitmap(connectPost.getPicture().get(0));
        }
        if(settingView.getLike()){

            holder.like.setText(connectPost.getLike()+"");
        }
        if(settingView.getRepost()) {
            holder.repost.setText(connectPost.getRepost() + "");
        }
        if(settingView.getComment()){
            holder.comment.setText(connectPost.getComments()+"");
        }
        if(settingView.getViews()) {
            holder.views.setVisibility(View.VISIBLE);
            holder.views.setText(connectPost.getViews() + "");
        }
    }
    public static class ConnectPostViewHolder  extends RecyclerView.ViewHolder {
        final TextView Text,views;
       // final MediaStore.Audio Audio;
        final VideoView Video;
        final ImageView Picture;
        final Button like,repost,comment;

        ConnectPostViewHolder (View view){
            super(view);
            Text= (TextView) view.findViewById(R.id.Text);
            Video = (VideoView) view.findViewById(R.id.Video);
            Picture = (ImageView) view.findViewById(R.id.Picture);
            like = (Button) view.findViewById(R.id.like);
            views = (TextView) view.findViewById(R.id.views);
            repost = (Button) view.findViewById(R.id.repost);
            comment = (Button) view.findViewById(R.id.comment);

        }
    }
}
