package com.henrryd.appfoody2.other;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.henrryd.appfoody2.other.user;
import com.henrryd.appfoody2.R;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.henrryd.appfoody2.other.custom_Picture;

import java.lang.reflect.Type;

public class MyApplication extends Application {

    public static user User = null;
    public static final String CHANNEL_ID = "CHANNEL_1";

    @Override
    public void onCreate() {
        super.onCreate();

        DataLocalManager.init(getApplicationContext());
        custom_Picture.init(getApplicationContext());

        String tmpuser = DataLocalManager.get_user();
        if (tmpuser != null)
            if (tmpuser != null){
                Gson gson = new Gson();
                Type objtype = new TypeToken<user>(){}.getType();
                User = gson.fromJson(tmpuser, objtype);
            }


        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.menu_slideshow);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

}
