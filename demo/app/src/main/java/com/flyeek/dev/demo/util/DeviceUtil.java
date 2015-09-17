package com.flyeek.dev.demo.util;


import android.content.Context;
import android.os.Build;
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

}
