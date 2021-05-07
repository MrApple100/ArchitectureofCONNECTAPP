package com.example.architectureofconnectapp.PicturesfromGallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SetterPictures extends Activity {
    private int Request_Code=1;
    AnotherThread load;
    ArrayList<Bitmap> bitmaps;

    public void RequestforPictures(){
        load = new AnotherThread();
        load.start();
    }
    public ArrayList<Bitmap> getPictures(){
        return bitmaps;
    }
    private class AnotherThread extends Thread{
        @Override
        public void run() {
            super.run();
            Intent intent=new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,Request_Code);
        }
    }
    /*
    private class LoadImage extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... args) {
            for (int i = 0; i < 100; i += 10) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Request_Code);
            }
            return null;
        }

        protected void onPostExecute(Void image) {

        }
    }

     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap=null;
        if(requestCode==Request_Code){
            if(resultCode==RESULT_OK){
                Uri selectedimage=data.getData();
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedimage);
                }catch (Exception e){
                    e.getStackTrace();
                }
                bitmaps.add(bitmap);
            }

        }
    }
}
