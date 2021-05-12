package com.example.architectureofconnectapp.ConnectThings;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureofconnectapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterPhotoPlaces extends RecyclerView.Adapter<AdapterPhotoPlaces.PhotoPlacesHolder> {
    ArrayList<Bitmap> bitmaps;
    LayoutInflater layoutInflater;

    public AdapterPhotoPlaces(Context context, ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public ArrayList<Bitmap> getBitmapsList() {
        return bitmaps;
    }

    public void setBitmapsList(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }


    @Override
    public PhotoPlacesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoPlacesHolder(layoutInflater.inflate(R.layout.imageinphotoplace, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterPhotoPlaces.PhotoPlacesHolder holder, int position) {
        holder.imageView.setImageBitmap(bitmaps.get(position));


    }

    @Override
    public int getItemCount() {
        if(bitmaps==null){
            return 0;
        }else
            return bitmaps.size();
    }

    public static class PhotoPlacesHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        PhotoPlacesHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageinphotoplaces);
        }
    }
}
