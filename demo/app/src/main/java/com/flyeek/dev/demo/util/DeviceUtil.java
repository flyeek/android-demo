package com.flyeek.dev.demo.util;


import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author flyeek
 * @version created at 15/8/24.
 */
public class DeviceUtil {

    /**
     * Return true if the device brand is equal to the given brand name, or false
     * otherwise.
     * @param brand brand name of device.
     */
    public static boolean isBrand(String brand) {
        return Build.BRAND.equalsIgnoreCase(brand);
    }

    /**
     * Return user agent header of webview.
     */
    private static String getUserAgentHeader(Context context) {
        WebView webview;
        webview = new WebView(context);
        webview.layout(0, 0, 0, 0);
        WebSettings settings = webview.getSettings();
        return settings.getUserAgentString();
    }

    public static String getBaseFilePath(Context context) {
        boolean isSDCanRead = getExternalStorageState();
        String baseLocation = "";
        if (isSDCanRead) {
            baseLocation = getSDCardPath();
        } else {
            baseLocation = context.getFilesDir().getAbsolutePath();
        }
        return baseLocation;
    }

    public static boolean getExternalStorageState() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return false;
        } else {
            return false;
        }
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().toString();
    }

}
