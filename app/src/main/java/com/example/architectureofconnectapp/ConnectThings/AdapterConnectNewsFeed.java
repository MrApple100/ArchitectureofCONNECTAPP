package com.example.architectureofconnectapp.ConnectThings;

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

import com.example.architectureofconnectapp.IPostfromNet;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.R;
import com.example.architectureofconnectapp.VK.VKInterection;

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
        holder.Namesnetgroup.setText(connectPost.getNameNet()+"/"+connectPost.getNameGroup());
        if(settingView.getText()){
            holder.Text.setVisibility(View.VISIBLE);
            holder.Text.setText(connectPost.getText());
        }else{
            holder.Text.setVisibility(View.GONE);
        }
        if(connectPost.getPicture().size()!=0) {
            holder.Picture.setVisibility(View.VISIBLE);
            holder.Picture.setAdapter(new AdapterPhotoPlaces(MainActivity.getInstance(),connectPost.getPicture()));
        }else{
            holder.Picture.setVisibility(View.GONE);
        }
        if(settingView.getLike()){
            holder.like.setText(connectPost.getLike()+"");
            holder.like.setTag(connectPost.getId());
            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new VKInterection().like(connectPost.getNetPostJsonInfo(),(long)holder.like.getTag());
                    System.out.println(holder.like.getTag());
                }
            });
        }
        if(settingView.getRepost() ) {
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
        final TextView Text,views,Namesnetgroup;
       // final MediaStore.Audio Audio;
        final VideoView Video;
        final RecyclerView Picture;
        final Button like,repost,comment;

        ConnectPostViewHolder (View view){
            super(view);
            Namesnetgroup = (TextView) view.findViewById(R.id.namenet_nameqroup);
            Text = (TextView) view.findViewById(R.id.Text);
            Video = (VideoView) view.findViewById(R.id.Video);
            Picture = (RecyclerView) view.findViewById(R.id.Picture);
            like = (Button) view.findViewById(R.id.like);
            views = (TextView) view.findViewById(R.id.views);
            repost = (Button) view.findViewById(R.id.repost);
            comment = (Button) view.findViewById(R.id.comment);

        }
    }
}
