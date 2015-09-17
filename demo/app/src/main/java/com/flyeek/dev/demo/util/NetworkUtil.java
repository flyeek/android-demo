package com.flyeek.dev.demo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by flyeek on 9/16/15.
 */
public class NetworkUtil {

    /**
     * Judge if network is available.
     *
     * @param context
     * @return True if network if available, false otherwise.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.isAvailable()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Judge if Mobile network is available.
     *
     * @param context
     * @return True if mobile network is available, false otherwise.
     */
    public static boolean isMobileDataEnable(Context context) throws Exception {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileDataEnable = false;

        if (connectivityManager != null) {
            isMobileDataEnable = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        }

        return isMobileDataEnable;
    }

    /**
     * Judge if WIFI network is available.
     *
     * @param context
     * @return True if WIFI network is available, false otherwise.
     */
    public static boolean isWifiDataEnable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiDataEnable = false;

        if (connectivityManager != null) {
            isWifiDataEnable = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        }

        return isWifiDataEnable;
    }

    /**
     * Get IP address for the network.
     *
     * @param context
     * @return ip address of the network, or null if no ip available.
     */
    public static String getIpAddress(Context context) {
        String ipAddress = null;

        Enumeration<NetworkInterface> mEnumeration = null;
        try {
            mEnumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ex) {
            Log.e("Error", ex.toString());
        }
        if (mEnumeration != null) {
            while (mEnumeration.hasMoreElements()) {
                NetworkInterface intf = mEnumeration.nextElement();
                for (Enumeration<InetAddress> enumIPAddr = intf.getInetAddresses(); enumIPAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIPAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ipAddress = inetAddress.getHostAddress();
                        break;
                    }
                }
            }
        }
        return ipAddress;
    }

    private static String parseIp(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }
}
