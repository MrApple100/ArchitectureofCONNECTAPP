package com.example.architectureofconnectapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.architectureofconnectapp.ConnectThings.ConnectPost;

public class DiffUtilCallback extends DiffUtil.ItemCallback{

    @Override
    public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
        ConnectPost oldconnectPost = (ConnectPost) oldItem;
        ConnectPost newconnectPost = (ConnectPost) newItem;
        return oldconnectPost.getId() == newconnectPost.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
        ConnectPost oldconnectPost = (ConnectPost) oldItem;
        ConnectPost newconnectPost = (ConnectPost) newItem;
        return oldconnectPost.getText().equals(newconnectPost.getText());
    }


}
