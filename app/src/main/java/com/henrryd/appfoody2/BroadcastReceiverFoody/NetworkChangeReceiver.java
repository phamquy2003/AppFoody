package com.henrryd.appfoody2.BroadcastReceiverFoody;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;

import com.henrryd.appfoody2.R;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "internet_channel";
    private static final int NOTIFICATION_ID = 123;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = isConnectedToInternet(context);
        if (isConnected) {
            showNotification(context, "Đã kết nối Internet");
            Toast.makeText(context, "Thiết bị của bạn đã được kết nối Internet", Toast.LENGTH_SHORT).show();

        } else {
            showNotification(context, "Mất kết nối Internet");
            new AlertDialog.Builder(context)
                    .setTitle("Thông báo")
                    .setMessage("Thiết bị hiện đang ngoại tuyển\nHãy kết nối Internet với thiết bị của bạn")
                    .setPositiveButton("Đóng", ((dialog, which) -> dialog.cancel())).show();
            Toast.makeText(context, "Thiết bị của bạn hiện đang ngoại tuyến", Toast.LENGTH_SHORT).show();

        }
    }

    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void showNotification(Context context, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Internet Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Internet Connectivity")
                .setContentText(message);

        boolean isConnected = isConnectedToInternet(context);

        if (isConnected) {
            builder.setSmallIcon(R.drawable.ic_wifi);
        } else {
            builder.setSmallIcon(R.drawable.ic_no_network);
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
