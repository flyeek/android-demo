package com.flyeek.dev.demo.googleplay;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class GAInstallReferrerReceiver extends BroadcastReceiver {

    private static String TAG = "INSTALL_RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Received install event");
        Toast.makeText(context, "Received install referrer ",
                Toast.LENGTH_SHORT).show();

        Bundle extras = intent.getExtras();
        if (extras != null) {
            Toast.makeText(context, "Received install referrer " + extras.getString("referrer"),
                    Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Received install referrer " + extras.getString("referrer"));
        }
    }

}