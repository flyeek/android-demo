package com.flyeek.dev.demo;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.flyeek.dev.demo.util.FrescoUtil;

/**
 * Created by flyeek on 6/8/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
        FrescoUtil.initialize(getApplicationContext());
    }
}
