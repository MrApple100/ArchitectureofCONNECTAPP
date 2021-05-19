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
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architectureofconnectapp.APIforServer.Network;
import com.example.architectureofconnectapp.ConnectThings.AdapterPhotoPlaces;
import com.example.architectureofconnectapp.ConnectThings.ConnectPageCreatePost;

import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.Users;
import com.example.architectureofconnectapp.PicturesfromGallery.SetterPictures;
import com.example.architectureofconnectapp.R;
import com.example.architectureofconnectapp.Twitter.TwitterProcessRequest;
import com.example.architectureofconnectapp.VK.VKProcessRequest;

import org.json.JSONException;

import java.util.ArrayList;

public class FragmentNewConnectPost extends Fragment {
    private int Request_Code=1;
    static TextView Cancel;
    static TextView Access;
    static TextView AddPhoto;
    static RecyclerView PhotoPlaces;
    ArrayList<Bitmap> bitmaps=new ArrayList<>();
    static EditText TextPlace;

    static TextView VKname;
    static Switch VKswitch;

    static TextView Twittername;
    static Switch Twitterswitch;

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
        Cancel = (TextView) view.findViewById(R.id.Cancel);
        Access = (TextView) view.findViewById(R.id.Access);
        AddPhoto = (TextView) view.findViewById(R.id.addPhoto);
        PhotoPlaces = (RecyclerView) view.findViewById(R.id.PhotosPlace);
        AdapterPhotoPlaces adapter=new AdapterPhotoPlaces(MainActivity.getInstance(),bitmaps);
        PhotoPlaces.setAdapter(adapter);

        TextPlace = (EditText) view.findViewById(R.id.TextPlace);

        VKname = (TextView) view.findViewById(R.id.VKname);
        String name = null;
        String lastname =null;
            name = Users.getInstance().getUsersofNet().get((long)"VK".hashCode()).getFirst_name();
            lastname = Users.getInstance().getUsersofNet().get((long)"VK".hashCode()).getLast_name();
        VKname.setText(name+" "+lastname);
        VKswitch = (Switch) view.findViewById(R.id.VKswitch);

        Twittername = (TextView) view.findViewById(R.id.Twittername);
            name = Users.getInstance().getUsersofNet().get((long)"Twitter".hashCode()).getFirst_name();
            lastname = Users.getInstance().getUsersofNet().get((long)"Twitter".hashCode()).getLast_name();
        Twittername.setText(name);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmaps.clear();
                ((AdapterPhotoPlaces) PhotoPlaces.getAdapter()).setBitmapsList(null);
                PhotoPlaces.getAdapter().notifyDataSetChanged();
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
                if(VKswitch.isChecked())
                    connectPageCreatePost.SentPost(new VKProcessRequest());
                if(Twitterswitch.isChecked())
                    connectPageCreatePost.SentPost(new TwitterProcessRequest());
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                System.out.println("hi");
                if (direction == ItemTouchHelper.DOWN || direction == ItemTouchHelper.UP) {
                    ((AdapterPhotoPlaces) PhotoPlaces.getAdapter()).getBitmapsList().remove(viewHolder.getAdapterPosition());
                    bitmaps=((AdapterPhotoPlaces) PhotoPlaces.getAdapter()).getBitmapsList();
                    PhotoPlaces.getAdapter().notifyDataSetChanged();
                }
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(PhotoPlaces);
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
