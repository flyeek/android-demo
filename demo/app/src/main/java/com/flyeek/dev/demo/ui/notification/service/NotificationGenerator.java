package com.flyeek.dev.demo.ui.notification.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.flyeek.dev.demo.R;

public class NotificationGenerator extends BroadcastReceiver {
    public NotificationGenerator() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("com.flyeek.notify")) {
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Title")
                    .setContentText("Context")
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setOngoing(false)
                    .build();

            notificationManager.notify(1, notification);
        }
    }
}
