package com.flyeek.dev.demo.util;

import android.content.Context;

/**
 * Helper Class for push notifications.
 * <p>
 * Created by flyeek on 5/30/15.
 */
public class PushUtil {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

}
