package com.example.architectureofconnectapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.architectureofconnectapp.ConnectThings.ConnectPost;

public class DiffUtilCallback extends DiffUtil.ItemCallback{
    //нахождение похожих объектов
    @Override
    public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
        ConnectPost oldconnectPost = (ConnectPost) oldItem;
        ConnectPost newconnectPost = (ConnectPost) newItem;
        return oldconnectPost.getId() == newconnectPost.getId();
    }
    //сравнение контентов объектов
    @Override
    public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
        ConnectPost oldconnectPost = (ConnectPost) oldItem;
        ConnectPost newconnectPost = (ConnectPost) newItem;
        if(oldconnectPost.getSettingView().getText()!=null && newconnectPost.getSettingView().getText()!=null)
            return oldconnectPost.getId()==newconnectPost.getId();
        else
            return false;
    }


}
