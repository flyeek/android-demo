package com.flyeek.dev.demo.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Helper class about package and activity.
 * Created by flyeek on 9/8/15.
 */
public class PackageUtil {

    public static final int APP_TYPE_SYSTEM = 1;
    public static final int APP_TYPE_THIRD_PARTY = 1<<1;
    public static final int APP_TYPE_ALL = APP_TYPE_SYSTEM | APP_TYPE_THIRD_PARTY;

    public static List<String> getInstalledPackages(Context context, int type) {
        List<String> installedApps = new ArrayList<>();

        type = type & APP_TYPE_ALL;
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo packageInfo = packageInfos.get(i);
            final int packageType = (packageInfo.applicationInfo.flags & ApplicationInfo
                    .FLAG_SYSTEM) == 1 ? APP_TYPE_SYSTEM : APP_TYPE_THIRD_PARTY;
            if ((type & packageType) != 0) {
                installedApps.add(packageInfo.packageName);
            }
        }

        return installedApps;
    }


    /**
     * Only work below(include) API-21, after which runtime user-confirm will be needed.
     * @param context
     * @return
     */
    private List<String> getRunningPackages(Context context) {
        List<String> runningPackages = new ArrayList<>();

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            final List<ActivityManager.RunningTaskInfo> runningTaskInfos = am.getRunningTasks(20);
            if (runningTaskInfos != null) {
                for (int i = 0 ; i < runningTaskInfos.size() ; i++) {
                    runningPackages.add(runningTaskInfos.get(i).topActivity.getPackageName());
                }
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            final List<ActivityManager.RunningAppProcessInfo> runningProcessInfos = am.getRunningAppProcesses();
            if (runningProcessInfos != null) {
                for (ActivityManager.RunningAppProcessInfo pi : runningProcessInfos) {
                    Collections.addAll(runningPackages, pi.pkgList);
                }
            }
        }

        return runningPackages;
    }


    public static String getAppName(Context context, String packageName) {
        String appName = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            appName =  packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
        } catch (PackageManager.NameNotFoundException e) {
            // Nothing to do.
        }

        return appName;
    }


    public static String getVersionName(Context context, String packageName) {
        String versionName = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // Nothing to do.
        }

        return versionName;
    }


    public static int getVersionCode(Context context, String packageName) {
        int versionCode = 0;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // Nothing to do.
        }

        return versionCode;
    }


    public static boolean isAppInstalled(Context context, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }

        return packageInfo != null;
    }
}
