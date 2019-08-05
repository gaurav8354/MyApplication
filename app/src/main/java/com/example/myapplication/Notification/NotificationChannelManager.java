package com.example.myapplication.Notification;


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationChannelManager extends Application {
    public static final String CHANNEL_1="2";
    @Override
    public void onCreate() {
        super.onCreate();
        createChannel();

    }

    private void createChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_1,"Channel2", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("This is channel 2");
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
