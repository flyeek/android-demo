package com.flyeek.dev.demo.util;

import android.content.Context;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateStatus;

/**
 * Helper Class for update.
 * <p/>
 * Created by flyeek on 5/30/15.
 */
public class UpdateUtil {

    /**
     * 当用户进入应用首页后，如果处于wifi环境则自动检测更新（默认只在wifi环境下检测，是为了用户流量考虑。
     * 这个行为可以更改），如果有更新，弹出对话框提示有新版本，用户点选更新开始下载更新。
     * <p>
     * 在应用程序入口Activity里的OnCreate() 方法中调用
     */
    public static final int TYPE_AUTO = 1;

    /**
     * 主要使用场景：许多应用的设置界面中都会有检查更新等类似功能，需要用户主动触发而检测更新。它的默认行为基本和自动更新基本一致。
     * 它和自动更新的主要区别是：在这种手动更新的情况下，无论网络状况是否Wifi，无论用户是否忽略过该版本的更新，都可以像下面的示例一样在按钮的回调中发起更新检查
     */
    public static final int TYPE_MANUAL = 2;

    /**
     * 主要使用场景：当用户进入应用首页后如果处于wifi环境检测更新，如果有更新，后台下载新版本，
     * 如果下载成功，则进行通知栏展示，用户点击通知栏开始安装。
     * <p>
     * 静默下载中途如果wifi断开，则会停止下载。
     * <p>
     * 在应用程序入口Activity里的OnCreate() 方法中调用
     */
    public static final int TYPE_SILENT = 3;

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void checkUpdate(int type, boolean deltaUpdate, boolean onlyWifi, boolean
            richNotification, boolean updateUIDialog) {
        UmengUpdateAgent.setDeltaUpdate(deltaUpdate);
        UmengUpdateAgent.setRichNotification(richNotification);
        UmengUpdateAgent.setUpdateUIStyle(updateUIDialog ? UpdateStatus.STYLE_DIALOG :
                UpdateStatus.STYLE_NOTIFICATION);

        switch (type) {
            case TYPE_AUTO:
                UmengUpdateAgent.setUpdateOnlyWifi(onlyWifi);
                UmengUpdateAgent.update(mContext);
                break;
            case TYPE_MANUAL:
                UmengUpdateAgent.forceUpdate(mContext);
                break;
            case TYPE_SILENT:

                break;
            default:
                break;
        }


    }


}
