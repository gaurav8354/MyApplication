package com.example.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myapplication.Database.SqlHelper;
import com.example.myapplication.Notification.NotificationChannelManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class FinalDataRecycleView extends AppCompatActivity {
    Intent i;
    TextView tv_dis, tv_grow, tv_water, tv_id, tv_name;
    boolean check=false;
    ImageView imageView,iv_star_blank;
    String name,pid,disc,imageurl;
    int gnum,wnum;
    Bitmap bitmap1;

    NotificationManagerCompat notificationCompat;

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d("12345","on intent");
        if(!intent.getStringExtra("id").equals(pid))
        {
            notificationCompat=NotificationManagerCompat.from(this);
            // Log.d("1234","oncreate called ");
            getSupportActionBar().hide();
            changeStatusBarColor("#FFFFFF");
            status_icon_color();

            i = intent;
            idsetter();
            favDataCheck();
            setData();

        }

        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_data_recycle_view);
        Log.d("12345","on create");

        notificationCompat=NotificationManagerCompat.from(this);
        // Log.d("1234","oncreate called ");
        getSupportActionBar().hide();
        changeStatusBarColor("#FFFFFF");
        status_icon_color();

        i = getIntent();
        idsetter();
        favDataCheck();
        setData();


    }

    @Override
    protected void onResume() {
        super.onResume();
        markImportantSata();
    }

    private void favDataCheck() {
        SqlHelper sqlHelper=new SqlHelper(getApplicationContext());
        if(sqlHelper.favExist(i.getStringExtra("id")))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                iv_star_blank.setImageDrawable(getDrawable(R.drawable.star_marked));
            }
        }

    }

    private void markImportantSata() {
        iv_star_blank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iv_star_blank.getDrawable().getConstantState()==getResources().getDrawable(R.drawable.star_blank).getConstantState())
                {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        iv_star_blank.setImageDrawable(getDrawable(R.drawable.star_marked));
//                        Toast toast=new Toast(getApplicationContext());
                        Toast toast = Toast.makeText(getApplicationContext(),"Added to favourite",1000);

                        name=i.getStringExtra("name");
                        pid=i.getStringExtra("id");
                        disc=i.getStringExtra("dis");
                        imageurl=i.getStringExtra("image");
                        gnum=i.getIntExtra("g",0);
                        wnum=i.getIntExtra("w",0);
                       if( insertDataToFav(name,pid,disc,imageurl,gnum,wnum)){
                           toast.setText("Added to favourite");
                           LayoutInflater layoutInflater=getLayoutInflater();
                           View toastview= (View) layoutInflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.custom_layout));
                           TextView tv=  toastview.findViewById(R.id.tv_toast);
                           tv.setText("Added to favourite");
                           ImageView ivEmoji=toastview.findViewById(R.id.iv_toast_emoji);
                           ImageView ivStar=toastview.findViewById(R.id.iv_toast);
                           ivEmoji.setImageDrawable(getDrawable(R.drawable.smilepng));
                           ivStar.setImageDrawable(getDrawable(R.drawable.star_marked));
                           toast.setView(toastview);
                           toast.show();
                           showNotification(i.getStringExtra("name"),i.getStringExtra("dis").substring(0,50),i.getStringExtra("image"));
                       }
                        else
                        {
                            Toast.makeText(FinalDataRecycleView.this, "Something wrong with database", Toast.LENGTH_SHORT).show();
                        }
//
                    }
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        iv_star_blank.setImageDrawable(getDrawable(R.drawable.star_blank));
//                        Toast.makeText(FinalDataRecycleView.this, "Remove from favourite", Toast.LENGTH_SHORT).show();
                        Toast toast = Toast.makeText(getApplicationContext(),"Removed1 from favourite",1000);
                        LayoutInflater layoutInflater=getLayoutInflater();
                        View toastview= (View) layoutInflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.custom_layout));
                        TextView tv=  toastview.findViewById(R.id.tv_toast);
                        tv.setText("Removed from favourite");
                        SqlHelper sqlHelper=new SqlHelper(getApplicationContext());
                        String pid;
                        pid=i.getStringExtra("id");
                        sqlHelper.removeFromFav(pid);
                        ImageView ivEmoji=toastview.findViewById(R.id.iv_toast_emoji);
                        ImageView ivStar=toastview.findViewById(R.id.iv_toast);
                        ivEmoji.setImageDrawable(getDrawable(R.drawable.sad));
                        ivStar.setImageDrawable(getDrawable(R.drawable.star_blank));
//                        toast.setText("Added to favourite");
                        toast.setView(toastview);
                        toast.show();

                    }
                }
            }
        });
    }

    private boolean insertDataToFav(String name, String pid, String disc, String imageurl, int gnum, int wnum) {
        SqlHelper sqlHelper=new SqlHelper(this);
       return sqlHelper.insertFavData(pid,name,disc,imageurl,gnum,wnum);
    }

    private void idsetter() {
        imageView = findViewById(R.id.imageView_final);
        tv_dis = findViewById(R.id.tv_final_discription);
        tv_grow = findViewById(R.id.tv_growzonenumber);
        tv_name = findViewById(R.id.tv_final_name);
        tv_water = findViewById(R.id.tv_wateringInterval);
        iv_star_blank=findViewById(R.id.iv_star_blank);
        getSupportActionBar().setTitle(i.getStringExtra("name"));

    }
    private void setData() {
        //tv_dis.setText(i.getStringExtra("dis"));
        String str = i.getStringExtra("dis");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_dis.setText(Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv_dis.setText(Html.fromHtml(str));
        }
        tv_grow.setText("GrowZoneNumber :"+ " " + i.getIntExtra("g", 0));
        tv_water.setText("wateringInterval" + " : " + i.getIntExtra("w", 0));
        tv_name.setText(i.getStringExtra("name"));
       // Glide.with(this).load(i.getStringExtra("image")).into(imageView);
        Glide.with(this)
                .asBitmap()
                .load(i.getStringExtra("image"))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                        bitmap1=resource;
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    private void changeStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
    void status_icon_color(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (true) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                // We want to change tint color to white again.
                // You can also record the flags in advance so that you can turn UI back completely if
                // you have set other flags before, such as translucent or full screen.
                decor.setSystemUiVisibility(0);
            }
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void showNotification(String discription, String msg, String img) {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
           // Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.pug);
//            Intent resultIntent =new Intent(this,NotificationMessage.class);
//            resultIntent.putExtra("title",msg);
//            resultIntent.putExtra("d",discription);
//            resultIntent.putExtra("image",R.drawable.pug);
//            TaskStackBuilder stackBuilder=TaskStackBuilder.create(this);
//            stackBuilder.addNextIntentWithParentStack(resultIntent);
//            PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);


//            NotificationManager notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//            FutureTarget futureTarget = Glide.with(this)
//                    .asBitmap()
//                    .load(img)
//                    .submit();
//
//            try {
//              //  bitmap1 =  futureTarget.;
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        Bitmap theBitmap=getBitmapFromURL(img);
        Intent resultIntent = getIntent();
        resultIntent.putExtra("name",name);
        resultIntent.putExtra("dis",disc);
        resultIntent.putExtra("image",imageurl);
        resultIntent.putExtra("id",pid);
        resultIntent.putExtra("w",wnum);
        resultIntent.putExtra("g",gnum);
//        TaskStackBuilder stackBuilder=TaskStackBuilder.create(this);
//        stackBuilder.addNextIntentWithParentStack(resultIntent);
        Intent notifyIntent = getIntent();
// Set the Activity to start in a new, empty task


// Create the PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

//        PendingIntent pendingIntent=stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationChannelManager.CHANNEL_1)
                    .setAutoCancel(true).setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap1).setSummaryText(""))
                    .setContentTitle(msg).setSmallIcon(R.drawable.ic_touch_app_black_24dp).setContentIntent(pendingIntent)
                    .setContentText(discription).setColor(Color.BLACK)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    notificationCompat.notify(2,builder.build());
                    Log.d("1234","called notification");

        }

    //private Bitmap getBitmap(String img) {
//        public static Bitmap getBitmapFromURL(String src) {
//            try {
//                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//                StrictMode.setThreadPolicy(policy);
//                URL url = new URL(src);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//                InputStream input = connection.getInputStream();
//                Bitmap myBitmap = BitmapFactory.decodeStream(input);
//                return myBitmap;
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//        } // Author: silentnuke

    @Override
    public void onBackPressed() {
        check=true;
        super.onBackPressed();
    }
}


