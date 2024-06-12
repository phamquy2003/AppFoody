package com.henrryd.appfoody2.BroadcastReceiverFoody;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;

import com.henrryd.appfoody2.R;

public class LowBatteryReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "low_battery_channel";
    private static final int NOTIFICATION_ID = 456;

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = (level / (float) scale) * 100;

        if (batteryPct < 15) {
            showLowBatteryNotification(context);
            new AlertDialog.Builder(context)
                    .setTitle("Pin yếu!!!")
                    .setMessage("Pin hiện đang còn dưới 15%!!!\nHãy kết nối thiết bị với nguồn điện!!!")
                    .setPositiveButton("Đóng", ((dialog, which) -> dialog.cancel())).show();
        }
    }

    private void showLowBatteryNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Low Battery Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID)
                .setContentTitle("Pin yếu")
                .setContentText("Pin của bạn đang yếu, hãy sạc ngay!")
                .setSmallIcon(R.drawable.ic_low_battery);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}