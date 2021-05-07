package com.example.architectureofconnectapp.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureofconnectapp.ConnectThings.AdapterPhotoPlaces;
import com.example.architectureofconnectapp.ConnectThings.ConnectPageCreatePost;

import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.PicturesfromGallery.SetterPictures;
import com.example.architectureofconnectapp.R;
import com.example.architectureofconnectapp.VK.VKProcessRequest;

import java.util.ArrayList;

public class FragmentNewConnectPost extends Fragment {
    private int Request_Code=1;
    static Button Cancel;
    static Button Access;
    static Button AddPhoto;
    static RecyclerView PhotoPlaces;
    ArrayList<Bitmap> bitmaps=new ArrayList<>();
    static EditText TextPlace;
    private static FragmentNewConnectPost instance;

    public static FragmentNewConnectPost getInstance() {
        if (instance == null) {
            instance = new FragmentNewConnectPost();
        }
        return instance;
    }

    public FragmentNewConnectPost() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newconnectpost,container,false);
        Cancel = (Button) view.findViewById(R.id.Cancel);
        Access = (Button) view.findViewById(R.id.Access);
        AddPhoto = (Button) view.findViewById(R.id.addPhoto);
        PhotoPlaces = (RecyclerView) view.findViewById(R.id.PhotosPlace);
        AdapterPhotoPlaces adapter=new AdapterPhotoPlaces(MainActivity.getInstance(),bitmaps);
        PhotoPlaces.setAdapter(adapter);

        TextPlace = (EditText) view.findViewById(R.id.TextPlace);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPlaces.removeAllViews();
                TextPlace.setText("");
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.MainScene,FragmentConnectNewsfeed.getInstance());
                ft.commit();
                fm.executePendingTransactions();
            }
        });
        AddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestforPictures();

            }
        });
        Access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectPageCreatePost connectPageCreatePost=new ConnectPageCreatePost(TextPlace.getText().toString(), ((AdapterPhotoPlaces) PhotoPlaces.getAdapter()).getBitmapsList());
                connectPageCreatePost.SentPost(new VKProcessRequest());
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void RequestforPictures(){
        AnotherThread load = new AnotherThread();
        load.start();
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

    public int getRequest_Code() {
        return Request_Code;
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        System.out.println("LLPPPPPPPPP");
        Bitmap bitmap=null;

        if(requestCode==Request_Code){
            if(resultCode==MainActivity.RESULT_OK){
                Uri selectedimage=data.getData();

                try{
                    bitmap = MediaStore.Images.Media.getBitmap(MainActivity.getInstance().getContentResolver(),selectedimage);
                }catch (Exception e){
                    e.getStackTrace();
                }
                bitmaps.add(bitmap);
                ((AdapterPhotoPlaces) PhotoPlaces.getAdapter()).setBitmapsList(bitmaps);
                PhotoPlaces.getAdapter().notifyDataSetChanged();
            }

        }
    }
}
