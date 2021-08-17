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
import com.example.architectureofconnectapp.Cash.CreateTables.TableSocialNetwork;
import com.example.architectureofconnectapp.Cash.CreateTables.TableUser;
import com.example.architectureofconnectapp.Cash.Daos.DaoProfileConnectPost;
import com.example.architectureofconnectapp.Cash.Daos.DaoSocialNetwork;
import com.example.architectureofconnectapp.Cash.Daos.DaoUser;
import com.example.architectureofconnectapp.IPostfromNet;
import com.example.architectureofconnectapp.MainActivity;
import com.example.architectureofconnectapp.Model.CashConnectPost;
import com.example.architectureofconnectapp.Model.SocialNetwork;
import com.example.architectureofconnectapp.Model.User;
import com.example.architectureofconnectapp.Model.Users;
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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class AdapterConnectNewsFeed extends PagedListAdapter<ConnectPost, AdapterConnectNewsFeed.ConnectPostViewHolder> {

    //BD for cash Networks
    TableSocialNetwork dataBase = (TableSocialNetwork) TableSocialNetwork.getInstance(MainActivity.getInstance(), "data1").allowMainThreadQueries().build();
    DaoSocialNetwork SocialNetworkDao = dataBase.SocialNetworkDao();

    TableProfileConnectPost dataConnectProfilefeed = (TableProfileConnectPost) TableProfileConnectPost.getInstance(MainActivity.getInstance(), "CashConnectProfilefeeds").allowMainThreadQueries().build();
    DaoProfileConnectPost daoProfileConnectPost = dataConnectProfilefeed.ProfileConnectPostDao();

    TableUser dataUser = (TableUser) TableUser.getInstance(MainActivity.getInstance(), "dataUser").allowMainThreadQueries().build();
    DaoUser UserDao = dataUser.UserDao();

    HashMap<Long,ArrayList<Bitmap>> tempsaverofbitmaps=new HashMap<>();



    public AdapterConnectNewsFeed(@NonNull DiffUtil.ItemCallback<ConnectPost> diffCallback) {
        super(diffCallback);



        //чтобы на вывод в консоль было выделено больше памяти
        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024);//100mb
        } catch (Exception e) {

        }


        ArrayList<SocialNetwork> socialNetworks =(ArrayList<SocialNetwork>) SocialNetworkDao.getAll();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                for (SocialNetwork s : socialNetworks) {
                    ArrayList<CashConnectPost> cashconnectPosts =(ArrayList<CashConnectPost>) daoProfileConnectPost.getAll(s.getId());//here maybe error
                    for (CashConnectPost cashConnectPost :cashconnectPosts) {
                        Log.d("TIME",(i++)+"");
                        Log.d("TIME","time1");
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<byte[]>>() {
                        }.getType();
                        try {
                            Log.d("TIME", cashConnectPost.getPhoto().length() + "");
                            ArrayList<byte[]> bytes = gson.fromJson(cashConnectPost.getPhoto(), type); //here maybe error
                            ArrayList<Bitmap> bitmaps = new ArrayList<>();
                            for (byte[] b : bytes) {
                                bitmaps.add(bytestobitmap(b));
                            }
                            tempsaverofbitmaps.put(cashConnectPost.getId(),bitmaps);
                        }catch (OutOfMemoryError error){

                        }

                        Log.d("TIME","time2");
                    }
                }
            }
        }).start();

        


    }
    @NonNull
    @Override
    public AdapterConnectNewsFeed.ConnectPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onepost,parent,false);

        return new AdapterConnectNewsFeed.ConnectPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder( AdapterConnectNewsFeed.ConnectPostViewHolder holder, int position) {
        ConnectPost baseconnectPost = getItem(position);
        IPostfromNet connectPost = getItem(position).getPostElements();
        //holder.Text.setVisibility(View.GONE);
        //holder.Picture.setVisibility(View.GONE);
        Log.d("TIME",baseconnectPost.getCashConnectPost()+"");
        if(baseconnectPost.getCashConnectPost()==null) {
            SettingView settingView = getItem(position).getSettingView();
            holder.Namesnetgroup.setText(connectPost.getNameNet() + "/" + connectPost.getNameGroup());
            System.out.println(settingView.getText());

            if (settingView.getText()) {
                holder.Text.setVisibility(View.VISIBLE);
                holder.Text.setText(connectPost.getText());
            } else {
                holder.Text.setVisibility(View.GONE);
            }



            //формирование кэш поста
            CashConnectPost cashConnectPost = new CashConnectPost( connectPost.getId());
            cashConnectPost.setNetwork(connectPost.getNameNet().hashCode());

            User user1 = UserDao.getByid(connectPost.getNameNet().hashCode());

            if (connectPost.getNameGroup().equals(user1.getFirst_name() + " " + user1.getLast_name())) {
                cashConnectPost.setType("userfeed".hashCode());

            }else {
                cashConnectPost.setType("newsfeed".hashCode());
            }

            cashConnectPost.setText(connectPost.getText());
            cashConnectPost.setPhoto("null");
            cashConnectPost.setNameNet(connectPost.getNameNet());
            cashConnectPost.setNetPostJsonInfo(connectPost.getNetPostJsonInfo());
            cashConnectPost.setDatatime(connectPost.getDatatime());
            cashConnectPost.setComments(connectPost.getComments());
            cashConnectPost.setLike(connectPost.getLike());
            cashConnectPost.setRepost(connectPost.getRepost());


            //хэндлер который получает массив фотограффий, преобразует их в байты и отправляет в кэш пост целиком
            Handler handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    holder.Picture.setAdapter(new AdapterPhotoPlaces(MainActivity.getInstance(), (ArrayList<Bitmap>) msg.obj));
                    ((AdapterPhotoPlaces) holder.Picture.getAdapter()).notifyItemChanged(position);

                    ArrayList<Bitmap> bitmaps = (ArrayList<Bitmap>) msg.obj;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<byte[]> bytes = new ArrayList<>();
                            for (Bitmap bitmap : bitmaps) {
                                bytes.add(bitmaptobytes(bitmap));
                            }
                            Gson gson = new Gson();
                            cashConnectPost.setPhoto(gson.toJson(bytes));
                            if(daoProfileConnectPost.getByid(cashConnectPost.getId())==null) {
                                daoProfileConnectPost.insert(cashConnectPost);
                                tempsaverofbitmaps.put(connectPost.getId(),bitmaps);
                            }
                        }
                    }).start();

                }
            };



            //загрузка фотографий на дисплей при первом появлении
          // if (daoProfileConnectPost.getByid( connectPost.getId()) == null) {

                if (connectPost.getPicture(handler).size() != 0) {
                    holder.Picture.setVisibility(View.VISIBLE);
                    holder.Picture.setAdapter(new AdapterPhotoPlaces(MainActivity.getInstance(), new ArrayList<Bitmap>(Arrays.asList(BitmapFactory.decodeResource(MainActivity.getInstance().getResources(), R.drawable.empty)))));
                } else {
                    holder.Picture.setVisibility(View.GONE);
                }

            //загрузка фото(если оно есть) при повторном появлении
         /* } else {

                //загрузка фотографии на дисплей из кэш если она есть

                    if (!daoProfileConnectPost.getByid(connectPost.getId()).getPhoto().equals("null")) {

                        Handler handlerhere=new Handler(){
                            @Override
                            public void handleMessage(@NonNull Message msg) {
                                super.handleMessage(msg);
                                ArrayList<Bitmap> bitmaps=(ArrayList<Bitmap>) msg.obj;
                                //if (bitmaps.size() == 0) {
                               //     holder.Picture.setVisibility(View.GONE);
                               // } else {

                                 //
                                  //  holder.Picture.setVisibility(View.VISIBLE);
                                    holder.Picture.setAdapter(new AdapterPhotoPlaces(MainActivity.getInstance(), bitmaps));
                                ((AdapterPhotoPlaces) holder.Picture.getAdapter()).notifyItemChanged(position);
                                //}
                            }
                        };

                        if (connectPost.getPicture(null).size() != 0) {
                            holder.Picture.setVisibility(View.VISIBLE);
                            holder.Picture.setAdapter(new AdapterPhotoPlaces(MainActivity.getInstance(), new ArrayList<Bitmap>(Arrays.asList(BitmapFactory.decodeResource(MainActivity.getInstance().getResources(), R.drawable.empty)))));
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if(tempsaverofbitmaps.get(connectPost.getId())==null) {
                                        Log.d("TIME","false");
                                        CashConnectPost cashConnectPost1 = daoProfileConnectPost.getByid(connectPost.getId());

                                        Gson gson = new Gson();
                                        Type type = new TypeToken<ArrayList<byte[]>>() {
                                        }.getType();

                                        ArrayList<byte[]> bytes = gson.fromJson(cashConnectPost1.getPhoto(), type);
                                        ArrayList<Bitmap> bitmaps = new ArrayList<>();
                                        for (byte[] b : bytes) {
                                            bitmaps.add(bytestobitmap(b));
                                        }
                                        tempsaverofbitmaps.put(connectPost.getId(),bitmaps);
                                        Message message = new Message();
                                        message.obj = bitmaps;
                                        handlerhere.sendMessage(message);
                                    }else{
                                        Log.d("TIME","true");
                                        Message message = new Message();
                                        message.obj = tempsaverofbitmaps.get(connectPost.getId());
                                        handlerhere.sendMessage(message);
                                    }
                                }
                            }).start();
                        } else {
                            holder.Picture.setVisibility(View.GONE);
                        }



                    }

            }


          */





            if (settingView.getLike()) {
                holder.like.setText(connectPost.getLike() + " likes");
                holder.like.setTag(connectPost.getId());
                holder.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new VKInterection().like(connectPost.getNetPostJsonInfo(), (long) holder.like.getTag());
                        System.out.println(holder.like.getTag());
                    }
                });
            } else {
                holder.like.setText("no likes");
                holder.like.setTag(connectPost.getId());
            }
            if (settingView.getRepost()) {
                holder.repost.setText(connectPost.getRepost() + " reposts");
            } else {
                holder.repost.setText("no reposts");
            }
            if (settingView.getComment()) {
                holder.comment.setText(connectPost.getComments() + " comments");
            } else {
                holder.comment.setText("no comments");
            }
            if (settingView.getViews()) {
                holder.views.setVisibility(View.VISIBLE);
                holder.views.setText(connectPost.getViews() + "");
            }
        }else{
            CashConnectPost cashConnectPost = baseconnectPost.getCashConnectPost();
            if(cashConnectPost.getText()!=null){
                holder.Text.setVisibility(View.VISIBLE);
                holder.Text.setText(cashConnectPost.getText());
            }
            else{
                holder.Text.setVisibility(View.GONE);
            }
            if(cashConnectPost.getPhoto()!=null){
                ArrayList<Bitmap> bitmaps = new ArrayList<>();
                if(tempsaverofbitmaps.get(cashConnectPost.getId())==null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<byte[]>>() {
                    }.getType();
                    ArrayList<byte[]> bytes = gson.fromJson(cashConnectPost.getPhoto(), type);

                    for (byte[] b : bytes) {
                        bitmaps.add(bytestobitmap(b));
                    }
                    tempsaverofbitmaps.put(cashConnectPost.getId(), bitmaps);
                }else{
                    bitmaps=tempsaverofbitmaps.get(cashConnectPost.getId());
                }
                if(bitmaps.size()!=0) {
                    holder.Picture.setVisibility(View.VISIBLE);
                    holder.Picture.setAdapter(new AdapterPhotoPlaces(MainActivity.getInstance(), bitmaps));
                }else{
                    holder.Picture.setVisibility(View.GONE);
                }
            }else{
                holder.Picture.setVisibility(View.GONE);
            }


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
